/*
 * This file is generated by jOOQ.
 */
package jooq.generated.plat.file.tables;


import jooq.generated.plat.file.DefaultSchema;
import jooq.generated.plat.file.Indexes;
import jooq.generated.plat.file.Keys;
import jooq.generated.plat.file.tables.records.FileItemInfoRecord;
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
public class FileItemInfo extends TableImpl<FileItemInfoRecord> {

    private static final long serialVersionUID = -619340244;

    /**
     * The reference instance of <code>file_item_info</code>
     */
    public static final FileItemInfo FILE_ITEM_INFO = new FileItemInfo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FileItemInfoRecord> getRecordType() {
        return FileItemInfoRecord.class;
    }

    /**
     * The column <code>file_item_info.id</code>.
     */
    public final TableField<FileItemInfoRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.CHAR(36).nullable(false), this, "");

    /**
     * The column <code>file_item_info.file_set_info_id</code>.
     */
    public final TableField<FileItemInfoRecord, String> FILE_SET_INFO_ID = createField("file_set_info_id", org.jooq.impl.SQLDataType.CHAR(36).nullable(false), this, "");

    /**
     * The column <code>file_item_info.file_path</code>.
     */
    public final TableField<FileItemInfoRecord, String> FILE_PATH = createField("file_path", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>file_item_info.file_name</code>.
     */
    public final TableField<FileItemInfoRecord, String> FILE_NAME = createField("file_name", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>file_item_info.size</code>.
     */
    public final TableField<FileItemInfoRecord, Long> SIZE = createField("size", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>file_item_info.sequence</code>.
     */
    public final TableField<FileItemInfoRecord, Integer> SEQUENCE = createField("sequence", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>file_item_info.created_by</code>.
     */
    public final TableField<FileItemInfoRecord, String> CREATED_BY = createField("created_by", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false), this, "");

    /**
     * The column <code>file_item_info.created_on</code>.
     */
    public final TableField<FileItemInfoRecord, LocalDateTime> CREATED_ON = createField("created_on", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>file_item_info.updated_by</code>.
     */
    public final TableField<FileItemInfoRecord, String> UPDATED_BY = createField("updated_by", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false), this, "");

    /**
     * The column <code>file_item_info.updated_on</code>.
     */
    public final TableField<FileItemInfoRecord, LocalDateTime> UPDATED_ON = createField("updated_on", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>file_item_info.version</code>.
     */
    public final TableField<FileItemInfoRecord, Integer> VERSION = createField("version", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>file_item_info</code> table reference
     */
    public FileItemInfo() {
        this(DSL.name("file_item_info"), null);
    }

    /**
     * Create an aliased <code>file_item_info</code> table reference
     */
    public FileItemInfo(String alias) {
        this(DSL.name(alias), FILE_ITEM_INFO);
    }

    /**
     * Create an aliased <code>file_item_info</code> table reference
     */
    public FileItemInfo(Name alias) {
        this(alias, FILE_ITEM_INFO);
    }

    private FileItemInfo(Name alias, Table<FileItemInfoRecord> aliased) {
        this(alias, aliased, null);
    }

    private FileItemInfo(Name alias, Table<FileItemInfoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> FileItemInfo(Table<O> child, ForeignKey<O, FileItemInfoRecord> key) {
        super(child, key, FILE_ITEM_INFO);
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
        return Arrays.<Index>asList(Indexes.FILE_ITEM_INFO_IDX_FILE_SET_INFO_ID, Indexes.FILE_ITEM_INFO_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<FileItemInfoRecord> getPrimaryKey() {
        return Keys.KEY_FILE_ITEM_INFO_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<FileItemInfoRecord>> getKeys() {
        return Arrays.<UniqueKey<FileItemInfoRecord>>asList(Keys.KEY_FILE_ITEM_INFO_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TableField<FileItemInfoRecord, Integer> getRecordVersion() {
        return VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileItemInfo as(String alias) {
        return new FileItemInfo(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileItemInfo as(Name alias) {
        return new FileItemInfo(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public FileItemInfo rename(String name) {
        return new FileItemInfo(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FileItemInfo rename(Name name) {
        return new FileItemInfo(name, null);
    }
}
