/*
 * This file is generated by jOOQ.
*/
package net.fadavi.safeauth.data.gen.tables.records;


import javax.annotation.Generated;

import net.fadavi.safeauth.data.gen.tables.Account;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountRecord extends UpdatableRecordImpl<AccountRecord> implements Record4<Integer, String, String, String> {

    private static final long serialVersionUID = 1418583135;

    /**
     * Setter for <code>account.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>account.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>account.username</code>.
     */
    public void setUsername(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>account.username</code>.
     */
    public String getUsername() {
        return (String) get(1);
    }

    /**
     * Setter for <code>account.password_hash</code>.
     */
    public void setPasswordHash(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>account.password_hash</code>.
     */
    public String getPasswordHash() {
        return (String) get(2);
    }

    /**
     * Setter for <code>account.password_salt</code>.
     */
    public void setPasswordSalt(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>account.password_salt</code>.
     */
    public String getPasswordSalt() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Account.ACCOUNT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Account.ACCOUNT.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Account.ACCOUNT.PASSWORD_HASH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Account.ACCOUNT.PASSWORD_SALT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getPasswordHash();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getPasswordSalt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getPasswordHash();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getPasswordSalt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountRecord value2(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountRecord value3(String value) {
        setPasswordHash(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountRecord value4(String value) {
        setPasswordSalt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountRecord values(Integer value1, String value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AccountRecord
     */
    public AccountRecord() {
        super(Account.ACCOUNT);
    }

    /**
     * Create a detached, initialised AccountRecord
     */
    public AccountRecord(Integer id, String username, String passwordHash, String passwordSalt) {
        super(Account.ACCOUNT);

        set(0, id);
        set(1, username);
        set(2, passwordHash);
        set(3, passwordSalt);
    }
}