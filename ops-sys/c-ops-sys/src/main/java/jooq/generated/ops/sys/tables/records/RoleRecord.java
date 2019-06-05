/*
 * This file is generated by jOOQ.
 */
package jooq.generated.ops.sys.tables.records;


import jooq.generated.ops.sys.tables.Role;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.time.LocalDateTime;


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
public class RoleRecord extends UpdatableRecordImpl<RoleRecord> implements Record7<String, String, LocalDateTime, String, LocalDateTime, String, Integer> {

    private static final long serialVersionUID = -1716106388;

    /**
     * Setter for <code>role.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>role.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>role.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>role.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>role.created_on</code>.
     */
    public void setCreatedOn(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>role.created_on</code>.
     */
    public LocalDateTime getCreatedOn() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>role.created_by</code>.
     */
    public void setCreatedBy(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>role.created_by</code>.
     */
    public String getCreatedBy() {
        return (String) get(3);
    }

    /**
     * Setter for <code>role.updated_on</code>.
     */
    public void setUpdatedOn(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>role.updated_on</code>.
     */
    public LocalDateTime getUpdatedOn() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>role.updated_by</code>.
     */
    public void setUpdatedBy(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>role.updated_by</code>.
     */
    public String getUpdatedBy() {
        return (String) get(5);
    }

    /**
     * Setter for <code>role.version</code>.
     */
    public void setVersion(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>role.version</code>.
     */
    public Integer getVersion() {
        return (Integer) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<String, String, LocalDateTime, String, LocalDateTime, String, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<String, String, LocalDateTime, String, LocalDateTime, String, Integer> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Role.ROLE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Role.ROLE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field3() {
        return Role.ROLE.CREATED_ON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Role.ROLE.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field5() {
        return Role.ROLE.UPDATED_ON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Role.ROLE.UPDATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return Role.ROLE.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component3() {
        return getCreatedOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component5() {
        return getUpdatedOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getUpdatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value3() {
        return getCreatedOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value5() {
        return getUpdatedOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getUpdatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value3(LocalDateTime value) {
        setCreatedOn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value4(String value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value5(LocalDateTime value) {
        setUpdatedOn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value6(String value) {
        setUpdatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value7(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord values(String value1, String value2, LocalDateTime value3, String value4, LocalDateTime value5, String value6, Integer value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RoleRecord
     */
    public RoleRecord() {
        super(Role.ROLE);
    }

    /**
     * Create a detached, initialised RoleRecord
     */
    public RoleRecord(String id, String name, LocalDateTime createdOn, String createdBy, LocalDateTime updatedOn, String updatedBy, Integer version) {
        super(Role.ROLE);

        set(0, id);
        set(1, name);
        set(2, createdOn);
        set(3, createdBy);
        set(4, updatedOn);
        set(5, updatedBy);
        set(6, version);
    }
}
