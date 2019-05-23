/*
 * This file is generated by jOOQ.
 */
package jooq.generated.ops.sys;


import jooq.generated.ops.sys.tables.Menu;
import jooq.generated.ops.sys.tables.Permission;
import jooq.generated.ops.sys.tables.Role;
import jooq.generated.ops.sys.tables.UserRole;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import javax.annotation.Generated;
import java.util.ArrayList;
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
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 981967687;

    /**
     * The reference instance of <code></code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>menu</code>.
     */
    public final Menu MENU = jooq.generated.ops.sys.tables.Menu.MENU;

    /**
     * The table <code>permission</code>.
     */
    public final Permission PERMISSION = jooq.generated.ops.sys.tables.Permission.PERMISSION;

    /**
     * The table <code>role</code>.
     */
    public final Role ROLE = jooq.generated.ops.sys.tables.Role.ROLE;

    /**
     * The table <code>user_role</code>.
     */
    public final UserRole USER_ROLE = jooq.generated.ops.sys.tables.UserRole.USER_ROLE;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Menu.MENU,
            Permission.PERMISSION,
            Role.ROLE,
            UserRole.USER_ROLE);
    }
}
