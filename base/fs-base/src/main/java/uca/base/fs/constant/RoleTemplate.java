package uca.base.fs.constant;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/17 14:49
 */
public enum RoleTemplate {

    /**
     * 已注册
     */
    REGISTER("register"),

    ;

    public final String val;

    RoleTemplate(String value) {
        this.val = value;
    }
}
