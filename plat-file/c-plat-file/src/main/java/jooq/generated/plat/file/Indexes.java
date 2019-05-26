/*
 * This file is generated by jOOQ.
 */
package jooq.generated.plat.file;


import jooq.generated.plat.file.tables.FileItemInfo;
import jooq.generated.plat.file.tables.FileSetInfo;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;

import javax.annotation.Generated;


/**
 * A class modelling indexes of tables of the <code></code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index FILE_ITEM_INFO_IDX_FILE_SET_INFO_ID = Indexes0.FILE_ITEM_INFO_IDX_FILE_SET_INFO_ID;
    public static final Index FILE_ITEM_INFO_PRIMARY = Indexes0.FILE_ITEM_INFO_PRIMARY;
    public static final Index FILE_SET_INFO_PRIMARY = Indexes0.FILE_SET_INFO_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index FILE_ITEM_INFO_IDX_FILE_SET_INFO_ID = Internal.createIndex("idx_file_set_info_id", FileItemInfo.FILE_ITEM_INFO, new OrderField[] { FileItemInfo.FILE_ITEM_INFO.FILE_SET_INFO_ID }, false);
        public static Index FILE_ITEM_INFO_PRIMARY = Internal.createIndex("PRIMARY", FileItemInfo.FILE_ITEM_INFO, new OrderField[] { FileItemInfo.FILE_ITEM_INFO.ID }, true);
        public static Index FILE_SET_INFO_PRIMARY = Internal.createIndex("PRIMARY", FileSetInfo.FILE_SET_INFO, new OrderField[] { FileSetInfo.FILE_SET_INFO.ID }, true);
    }
}
