/*
 * This file is generated by jOOQ.
*/
package net.fadavi.safeauth.data.gen;


import javax.annotation.Generated;

import net.fadavi.safeauth.data.gen.tables.Account;
import net.fadavi.safeauth.data.gen.tables.records.AccountRecord;

import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code></code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AccountRecord> PK_ACCOUNT = UniqueKeys0.PK_ACCOUNT;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<AccountRecord> PK_ACCOUNT = createUniqueKey(Account.ACCOUNT, "pk_account", Account.ACCOUNT.ID);
    }
}
