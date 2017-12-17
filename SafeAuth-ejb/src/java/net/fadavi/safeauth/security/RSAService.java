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

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

/**
 *
 * @author Mohamad Fadavi <fadavi@fadavi.net>
 */
@Singleton // maybe changes in production phase
@Stateless
public class RSAService {

    public static final int KEY_SIZE = 512;

    private KeyPairGenerator kpGen;
    private KeyFactory keyFactory;

    public RSAService() {
        try {
            this.kpGen = KeyPairGenerator.getInstance("RSA");
            this.kpGen.initialize(KEY_SIZE);
            this.keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException ex) {
            throw new Error(ex); // impossible
        }
    }

    public KeyPair generateKeys() {
        return kpGen.generateKeyPair();
    }

    public byte[] toPem(PublicKey pubKey) {
        try {
            X509EncodedKeySpec keySpec = keyFactory.getKeySpec(pubKey, X509EncodedKeySpec.class);
            return keySpec.getEncoded();
        } catch (InvalidKeySpecException ex) {
            throw new Error(ex); // impossible
        }
    }

    public byte[] decrypt(byte[] encrypted, PrivateKey pvKey)
            throws InvalidKeyException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException,
            NoSuchPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pvKey);

        return cipher.doFinal(encrypted);
    }

    public String decryptString(byte[] encrypted, PrivateKey pvKey)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchAlgorithmException, IllegalBlockSizeException,
            IllegalBlockSizeException, BadPaddingException, BadPaddingException,
            NoSuchPaddingException, NoSuchPaddingException {
        return new String(decrypt(encrypted, pvKey));
    }
}
