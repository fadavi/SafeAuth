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
public class RegisterTokenResponse {
    @XmlElement private int result;
    @XmlElement private String message;
    @XmlElement private String registerToken;
    
    public RegisterTokenResponse() {}
    
    public RegisterTokenResponse(String registerToken) {
        this.result = JsonResponse.SUCCESS.getResult();
        this.message = JsonResponse.SUCCESS.getMessage();
        this.registerToken = registerToken;
    }
}
