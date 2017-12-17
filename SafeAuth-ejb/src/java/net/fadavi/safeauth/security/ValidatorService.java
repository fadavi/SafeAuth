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

import java.util.regex.Pattern;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import net.fadavi.safeauth.conf.Configuration;

/**
 *
 * @author Mohamad Fadavi <fadavi@fadavi.net>
 */
@Singleton // maybe changes in production phase
@Stateless
public class ValidatorService {
    private final int USERNAME_MIN_LEN = Configuration.getInt("username-min-len");
    private final int USERNAME_MAX_LEN = Configuration.getInt("username-max-len");
    private final int PASSWORD_MIN_LEN = Configuration.getInt("password-min-len");
    private final int PASSWORD_MAX_LEN = Configuration.getInt("password-max-len");
    
    private final String USERNAME_REGEX = String.format(
            "^[a-z][a-z0-9_\\-\\.]{%d,%d}$",
            USERNAME_MIN_LEN - 1,
            USERNAME_MAX_LEN - 1
    );
    
    private final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);
    
    public boolean validatePassword(char[] password) {
        int len = password.length;

        if (len > PASSWORD_MAX_LEN || len < PASSWORD_MIN_LEN) {
            return false;
        }

        boolean digitSeen = false;
        boolean alphaSeen = false;

        for (char c : password) {
            if (Character.isDigit(c)) {
                digitSeen = true;
            } else if (Character.isAlphabetic(c)) {
                alphaSeen = true;
            }

            if (digitSeen && alphaSeen) {
                return true;
            }
        }

        return false;
    }
    
    public boolean validateUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }
}
