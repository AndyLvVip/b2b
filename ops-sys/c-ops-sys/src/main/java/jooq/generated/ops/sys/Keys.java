/*
 * This file is generated by jOOQ.
 */
package jooq.generated.ops.sys;


import jooq.generated.ops.sys.tables.Menu;
import jooq.generated.ops.sys.tables.Permission;
import jooq.generated.ops.sys.tables.Role;
import jooq.generated.ops.sys.tables.UserRole;
import jooq.generated.ops.sys.tables.records.MenuRecord;
import jooq.generated.ops.sys.tables.records.PermissionRecord;
import jooq.generated.ops.sys.tables.records.RoleRecord;
import jooq.generated.ops.sys.tables.records.UserRoleRecord;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;

import javax.annotation.Generated;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code></code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
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

    public static final UniqueKey<MenuRecord> KEY_MENU_PRIMARY = UniqueKeys0.KEY_MENU_PRIMARY;
    public static final UniqueKey<PermissionRecord> KEY_PERMISSION_PRIMARY = UniqueKeys0.KEY_PERMISSION_PRIMARY;
    public static final UniqueKey<RoleRecord> KEY_ROLE_PRIMARY = UniqueKeys0.KEY_ROLE_PRIMARY;
    public static final UniqueKey<UserRoleRecord> KEY_USER_ROLE_PRIMARY = UniqueKeys0.KEY_USER_ROLE_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 {
        public static final UniqueKey<MenuRecord> KEY_MENU_PRIMARY = Internal.createUniqueKey(Menu.MENU, "KEY_menu_PRIMARY", Menu.MENU.ID);
        public static final UniqueKey<PermissionRecord> KEY_PERMISSION_PRIMARY = Internal.createUniqueKey(Permission.PERMISSION, "KEY_permission_PRIMARY", Permission.PERMISSION.ID);
        public static final UniqueKey<RoleRecord> KEY_ROLE_PRIMARY = Internal.createUniqueKey(Role.ROLE, "KEY_role_PRIMARY", Role.ROLE.ID);
        public static final UniqueKey<UserRoleRecord> KEY_USER_ROLE_PRIMARY = Internal.createUniqueKey(UserRole.USER_ROLE, "KEY_user_role_PRIMARY", UserRole.USER_ROLE.ID);
    }
}