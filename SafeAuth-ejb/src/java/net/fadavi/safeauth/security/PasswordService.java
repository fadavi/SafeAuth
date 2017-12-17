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
package net.fadavi.safeauth.security;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import net.fadavi.safeauth.conf.Configuration;

/**
 *
 * @author Mohamad Fadavi <fadavi@fadavi.net>
 */
@Singleton // maybe changes in production phase
@Stateless
public class PasswordService {
    private final String ALGORITHM = Configuration.get("password-hash-algorithm");
    private final Charset UNICODE = Charset.forName("UTF-8");
    private final SecureRandom RANDOM = new SecureRandom();
    private final int SALT_SIZE = Configuration.getInt("password-hash-salt-size");
    private final int ITERATION_COUNT = Configuration.getInt("password-pbe-iteration-count");
    private final int KEY_LENGTH = Configuration.getInt("password-pbe-key-length");
    
    public char[] fromByteArray(byte[] bytes) {
        return UNICODE.decode(ByteBuffer.wrap(bytes)).array();
    }

    public byte[] generateSalt() {
        byte[] salt = new byte[SALT_SIZE];
        RANDOM.nextBytes(salt);
        return salt;
    }

    public byte[] hash(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec ks = new PBEKeySpec(password, salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        return skf.generateSecret(ks).getEncoded();
    }

    public Boolean check(byte[] correctPasswordHash, char[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Arrays.equals(correctPasswordHash, hash(password, salt));
    }
}
