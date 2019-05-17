/*
 * This file is generated by jOOQ.
 */
package jooq.generated.corporate.tables;


import jooq.generated.corporate.DefaultSchema;
import jooq.generated.corporate.Indexes;
import jooq.generated.corporate.Keys;
import jooq.generated.corporate.tables.records.UserRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User extends TableImpl<UserRecord> {

    private static final long serialVersionUID = 23886777;

    /**
     * The reference instance of <code>user</code>
     */
    public static final User USER = new User();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRecord> getRecordType() {
        return UserRecord.class;
    }

    /**
     * The column <code>user.id</code>.
     */
    public final TableField<UserRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.CHAR(36).nullable(false), this, "");

    /**
     * The column <code>user.username</code>.
     */
    public final TableField<UserRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false), this, "");

    /**
     * The column <code>user.member_of</code>.
     */
    public final TableField<UserRecord, String> MEMBER_OF = createField("member_of", org.jooq.impl.SQLDataType.CHAR(36).nullable(false), this, "");

    /**
     * The column <code>user.name</code>.
     */
    public final TableField<UserRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(30), this, "");

    /**
     * The column <code>user.phone</code>.
     */
    public final TableField<UserRecord, String> PHONE = createField("phone", org.jooq.impl.SQLDataType.CHAR(11), this, "");

    /**
     * The column <code>user.email</code>.
     */
    public final TableField<UserRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>user.qq</code>.
     */
    public final TableField<UserRecord, String> QQ = createField("qq", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>user.enabled</code>.
     */
    public final TableField<UserRecord, Boolean> ENABLED = createField("enabled", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>user.created_on</code>.
     */
    public final TableField<UserRecord, LocalDateTime> CREATED_ON = createField("created_on", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>user.updated_on</code>.
     */
    public final TableField<UserRecord, LocalDateTime> UPDATED_ON = createField("updated_on", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>user.created_by</code>.
     */
    public final TableField<UserRecord, String> CREATED_BY = createField("created_by", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false), this, "");

    /**
     * The column <code>user.updated_by</code>.
     */
    public final TableField<UserRecord, String> UPDATED_BY = createField("updated_by", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false), this, "");

    /**
     * The column <code>user.version</code>.
     */
    public final TableField<UserRecord, Integer> VERSION = createField("version", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>user</code> table reference
     */
    public User() {
        this(DSL.name("user"), null);
    }

    /**
     * Create an aliased <code>user</code> table reference
     */
    public User(String alias) {
        this(DSL.name(alias), USER);
    }

    /**
     * Create an aliased <code>user</code> table reference
     */
    public User(Name alias) {
        this(alias, USER);
    }

    private User(Name alias, Table<UserRecord> aliased) {
        this(alias, aliased, null);
    }

    private User(Name alias, Table<UserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> User(Table<O> child, ForeignKey<O, UserRecord> key) {
        super(child, key, USER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserRecord> getPrimaryKey() {
        return Keys.KEY_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserRecord>> getKeys() {
        return Arrays.<UniqueKey<UserRecord>>asList(Keys.KEY_USER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TableField<UserRecord, Integer> getRecordVersion() {
        return VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User as(String alias) {
        return new User(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User as(Name alias) {
        return new User(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(String name) {
        return new User(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Name name) {
        return new User(name, null);
    }
}
