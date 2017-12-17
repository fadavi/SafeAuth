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

import java.util.Base64;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

/**
 *
 * @author Mohamad Fadavi <fadavi@fadavi.net>
 */
@Singleton // maybe changes in production phase
@Stateless
public class Base64Service {
    private final Base64.Decoder DECODER = Base64.getDecoder();
    private final Base64.Encoder ENCODER = Base64.getEncoder();
    
    public byte[] encode(byte[] bytes) {
        return ENCODER.encode(bytes);
    }
    
    public byte[] encode(String str) {
        return encode(str.getBytes());
    }
    
    public String encodeToString(byte[] bytes) {
        return ENCODER.encodeToString(bytes);
    }
    
    public String encodeToString(String str) {
        return encodeToString(str.getBytes());
    }
    
    public byte[] decode(byte[] bytes) {
        return DECODER.decode(bytes);
    }
    
    public byte[] decode(String str) {
        return DECODER.decode(str);
    }
    
    public String decodeToString(byte[] bytes) {
        return new String(DECODER.decode(bytes));
    }
    
    public String decodeToString(String str) {
        return decodeToString(str.getBytes());
    }
}
