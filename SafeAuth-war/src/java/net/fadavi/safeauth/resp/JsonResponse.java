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
package net.fadavi.safeauth.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mohamad Fadavi <fadavi@fadavi.net>
 */
@XmlRootElement
public class JsonResponse {
    public static final JsonResponse
            SUCCESS = create(0x001, 
                    "Success"),
            
            INCORRECT_PASSWORD_ANDOR_USERNAME = create(0x002,
                    "Incorrect password and/or username."),
            
            INVALID_USERNAME = create(0x003,
                    "Invalid username; username must be 5-32 characters, alphanumeric starting with a letter and may contains: -._"),
            
            INVALID_PASSWORD = create(0x004,
                    "Invalid password; password must be 6-32 characters and contains at least one digit and one non-digit character."),
            
            USERNAME_ALREADY_TAKEN = create(0x005,
                    "Username already taken."),
            
            NO_AUTHENTICATE_TOKEN = create(0x006,
                    "First, request for a authenticate token."),
            
            NO_REGISTER_TOKEN = create(0x007,
                    "First, request for a register token."),
            
            USERNAME_DECRYPTION_ERROR = create(0x008,
                    "Username decryption error occured."),
            
            PASSWORD_DECRYPTION_ERROR = create(0x009,
                    "Password decryption error occured."),
            
            INTERNAL_SERVER_ERROR = create(0x00A,
                    "Internal server error occured."),
            
            INVALID_METHOD = create(0x00B,
                    "Invalid method request.");

    @XmlElement private int result;
    @XmlElement private String message;

    private static JsonResponse create(int result, String message) {
        return new JsonResponse(result, message);
    }

    public JsonResponse() {}

    private JsonResponse(int result, String message) {
        this.result = result;
        this.message = message;
    }

    public int getResult() { return result; }

    public String getMessage() { return message; }
}
