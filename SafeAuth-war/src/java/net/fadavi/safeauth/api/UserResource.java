/* 
 * Copyright (C) 2017 Mohamad Fadavi <fadavi@fadavi.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fadavi.safeauth.api;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import net.fadavi.safeauth.data.DB;
import static net.fadavi.safeauth.data.gen.Tables.ACCOUNT;
import net.fadavi.safeauth.data.gen.tables.records.AccountRecord;
import net.fadavi.safeauth.req.AuthenticateRequest;
import net.fadavi.safeauth.req.RegisterRequest;
import net.fadavi.safeauth.resp.AuthTokenResponse;
import net.fadavi.safeauth.resp.JsonResponse;
import net.fadavi.safeauth.resp.RegisterTokenResponse;
import net.fadavi.safeauth.security.Base64Service;
import net.fadavi.safeauth.security.PasswordService;
import net.fadavi.safeauth.security.RSAService;
import net.fadavi.safeauth.security.ValidatorService;

/**
 * 
 * @author Mohamad Fadavi <fadavi@fadavi.net>
 */
@Path("user")
@SessionScoped
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class UserResource implements Serializable {
    @Inject private DB db;
    @Inject private RSAService rsa;
    @Inject private PasswordService passwordService;
    @Inject private Base64Service base64;
    @Inject private ValidatorService validator;

    private PrivateKey authKey;
    private PrivateKey regKey;

    @GET
    @Path("requestAuthToken")
    public AuthTokenResponse requestAuthToken() {
        KeyPair authKp = rsa.generateKeys();

        authKey = authKp.getPrivate();
        byte[] authPem = rsa.toPem(authKp.getPublic());
        String authPemStr = base64.encodeToString(authPem);
        return new AuthTokenResponse(authPemStr);
    }

    @GET
    @Path("requestRegisterToken")
    public RegisterTokenResponse requestRegisterToken() {
        KeyPair regKp = rsa.generateKeys();

        regKey = regKp.getPrivate();
        byte[] regPem = rsa.toPem(regKp.getPublic());
        String regPemStr = base64.encodeToString(regPem);
        return new RegisterTokenResponse(regPemStr);
    }

    @POST
    @Path("authenticate")
    public JsonResponse authenticate(AuthenticateRequest authReq) {
        if (authKey == null) {
            return JsonResponse.NO_AUTHENTICATE_TOKEN;
        }

        byte[] encryptedUsername = base64.decode(authReq.getUsername());
        byte[] encryptedPassword = base64.decode(authReq.getPassword());

        String username;
        try {
            username = rsa.decryptString(encryptedUsername, authKey);
        } catch (Exception __) {
            authKey = null;
            return JsonResponse.USERNAME_DECRYPTION_ERROR;
        }

        AccountRecord account = db.open()
                .selectFrom(ACCOUNT)
                .where(ACCOUNT.USERNAME.eq(username))
                .limit(1)
                .fetchAny();

        if(account == null) {
            return JsonResponse.INCORRECT_PASSWORD_ANDOR_USERNAME;
        }
        
        byte[] salt = base64.decode(account.getPasswordSalt());
        byte[] correctPasswordHash = base64.decode(account.getPasswordHash());
        byte[] passwordBytes;

        try {
            passwordBytes = rsa.decrypt(encryptedPassword, authKey);
        } catch (Exception __) {
            return JsonResponse.INTERNAL_SERVER_ERROR;
        } finally {
            authKey = null;
        }

        char[] password = passwordService.fromByteArray(passwordBytes);
        Arrays.fill(passwordBytes, (byte) 0);

        boolean succeed;
        try {
            succeed = passwordService.check(correctPasswordHash, password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            return JsonResponse.INTERNAL_SERVER_ERROR;
        } finally {
            Arrays.fill(password, '\0');
        }

        return succeed ? JsonResponse.SUCCESS : JsonResponse.INCORRECT_PASSWORD_ANDOR_USERNAME;
    }

    @POST
    @Path("register")
    public JsonResponse register(RegisterRequest regReq) {
        if (regKey == null) {
            return JsonResponse.NO_REGISTER_TOKEN;
        }
        
        byte[] encryptedUsername = base64.decode(regReq.getUsername());
        byte[] encryptedPassword = base64.decode(regReq.getPassword());

        String username;
        byte[] passwordBytes;

        try {
            username = rsa.decryptString(encryptedUsername, regKey);
        } catch (Exception __) {
            regKey = null;
            return JsonResponse.USERNAME_DECRYPTION_ERROR;
        }
        
        if (!validator.validateUsername(username)) {
            return JsonResponse.INVALID_USERNAME;
        }
        
        boolean usernameTaken = db.open().selectOne()
                .from(ACCOUNT)
                .where(ACCOUNT.USERNAME.eq(username))
                .fetchOptional()
                .isPresent();
        
        if (usernameTaken) {
            return JsonResponse.USERNAME_ALREADY_TAKEN;
        }        
        
        try {
            passwordBytes = rsa.decrypt(encryptedPassword, regKey);
        } catch (Exception __) {
            return JsonResponse.USERNAME_DECRYPTION_ERROR;
        } finally {
            regKey = null;
        }

        char[] passwordChars = passwordService.fromByteArray(passwordBytes);
        Arrays.fill(passwordBytes, (byte) 0);
        
        if (!validator.validatePassword(passwordChars)) {
            Arrays.fill(passwordChars, '\0');
            return JsonResponse.INVALID_PASSWORD;
        }

        byte[] salt = passwordService.generateSalt();

        byte[] passwordHash;
        try {
            passwordHash = passwordService.hash(passwordChars, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException __) {
            return JsonResponse.INTERNAL_SERVER_ERROR;
        } finally {
            Arrays.fill(passwordChars, '\0');
        }

        String saltStr = base64.encodeToString(salt);
        String passwordHashStr = base64.encodeToString(passwordHash);

        db.open().insertInto(ACCOUNT)
                .columns(ACCOUNT.USERNAME, ACCOUNT.PASSWORD_HASH, ACCOUNT.PASSWORD_SALT)
                .values(username, passwordHashStr, saltStr)
                .execute();

        return JsonResponse.SUCCESS;
    }

    @GET
    @Path("ping")
    public String ping() {
        try {
            KeyPair kp = rsa.generateKeys();

            Cipher c1 = Cipher.getInstance("RSA");
            c1.init(Cipher.ENCRYPT_MODE, kp.getPrivate());
            byte[] enc = c1.doFinal("Pong!".getBytes());

            Cipher c2 = Cipher.getInstance("RSA");
            c2.init(Cipher.DECRYPT_MODE, kp.getPublic());
            byte[] dec = c2.doFinal(enc);

            return "{\"message\": \"" + new String(dec) + "\"}";
        } catch (Exception ignore) {
            return ignore.getMessage();
        }
    }

    @POST
    @Path("ping")
    public String ping(AuthenticateRequest ar) {
        return "{\"message\": \"Hello " + ar.getUsername() + "\"}";
    }

    @GET
    @Path("/{ignore: .*}")
    public JsonResponse invalidMethodGet() {
        return JsonResponse.INVALID_METHOD;
    }
    
    @POST
    @Path("/{ignore: .*}")
    public JsonResponse invalidMethodPost() {
        return JsonResponse.INVALID_METHOD;
    }
}
