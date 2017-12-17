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
package net.fadavi.safeauth.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Mohamad Fadavi <fadavi@fadavi.net>
 */
public class Configuration {
    private static final Properties configuration = new Properties();
    
    static {
        try(InputStream is = Configuration.class.getResourceAsStream("configuration.properties")) {
            configuration.load(is);
        } catch (IOException ioex) {
            throw new RuntimeException("Loading configurations failed");
        }
    }
    
    public static String get(String key) {
        return configuration.getProperty(key);
    }
    
    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
