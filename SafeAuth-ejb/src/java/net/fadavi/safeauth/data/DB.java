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
package net.fadavi.safeauth.data;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import net.fadavi.safeauth.conf.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

/**
 *
 * @author Mohamad Fadavi <fadavi@fadavi.net>
 */
@Stateless
@Singleton
public class DB {
    private final String DIALECT = Configuration.get("database-dialect");
    private final String SQLITE_FILE_NAME = Configuration.get("sqlite-file-name");
    private final Path SQLITE_FILE_PATH = Paths.get(System.getProperty("user.home"), SQLITE_FILE_NAME);
    
    public DSLContext open() {
        if (DIALECT.equals("SQLite")) {
            return DSL.using(getSQLiteDataSource(), SQLDialect.SQLITE);
        }
        
        throw new RuntimeException("No suitable dialect found");
    }

    private DataSource getSQLiteDataSource() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + SQLITE_FILE_PATH);

        SQLiteConfig conf = ds.getConfig();
        conf.setEncoding(SQLiteConfig.Encoding.UTF8);
        conf.enforceForeignKeys(true);
        conf.setCacheSize(-16000);

        return ds;
    }

    private DataSource getMySQLDataSource() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
