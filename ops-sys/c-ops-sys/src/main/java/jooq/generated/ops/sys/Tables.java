/*
 * This file is generated by jOOQ.
 */
package jooq.generated.ops.sys;


import jooq.generated.ops.sys.tables.Menu;
import jooq.generated.ops.sys.tables.Permission;
import jooq.generated.ops.sys.tables.Role;
import jooq.generated.ops.sys.tables.UserRole;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in 
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>menu</code>.
     */
    public static final Menu MENU = jooq.generated.ops.sys.tables.Menu.MENU;

    /**
     * The table <code>permission</code>.
     */
    public static final Permission PERMISSION = jooq.generated.ops.sys.tables.Permission.PERMISSION;

    /**
     * The table <code>role</code>.
     */
    public static final Role ROLE = jooq.generated.ops.sys.tables.Role.ROLE;

    /**
     * The table <code>user_role</code>.
     */
    public static final UserRole USER_ROLE = jooq.generated.ops.sys.tables.UserRole.USER_ROLE;
}
