package uca.base.constant;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/15 9:03
 */
public class Constants {

    public static final String SYSTEM = "system";

    public static final String USER = "user";

    public static class PermissionUnit {
        public static final int VIEW = 1;

        public static final int CREATE = 1 << 1;

        public static final int EDIT = 1 << 2;

        public static final int DELETE = 1 << 3;
    }

}
