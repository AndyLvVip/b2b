/*
 * This file is generated by jOOQ.
 */
package jooq.generated.ops.sys;


import jooq.generated.ops.sys.tables.Menu;
import jooq.generated.ops.sys.tables.Permission;
import jooq.generated.ops.sys.tables.Role;
import jooq.generated.ops.sys.tables.UserRole;
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

    public static final Index MENU_PRIMARY = Indexes0.MENU_PRIMARY;
    public static final Index PERMISSION_PRIMARY = Indexes0.PERMISSION_PRIMARY;
    public static final Index ROLE_PRIMARY = Indexes0.ROLE_PRIMARY;
    public static final Index USER_ROLE_PRIMARY = Indexes0.USER_ROLE_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index MENU_PRIMARY = Internal.createIndex("PRIMARY", Menu.MENU, new OrderField[] { Menu.MENU.ID }, true);
        public static Index PERMISSION_PRIMARY = Internal.createIndex("PRIMARY", Permission.PERMISSION, new OrderField[] { Permission.PERMISSION.ID }, true);
        public static Index ROLE_PRIMARY = Internal.createIndex("PRIMARY", Role.ROLE, new OrderField[] { Role.ROLE.ID }, true);
        public static Index USER_ROLE_PRIMARY = Internal.createIndex("PRIMARY", UserRole.USER_ROLE, new OrderField[] { UserRole.USER_ROLE.ID }, true);
    }
}