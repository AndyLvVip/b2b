package uca.base.fs.constant;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/21 15:37
 */
public enum CorporateType {
    /**
     * 未知类型
     */
    UNKNOWN(1),
    /**
     * 个人类型
     */
    PERSON(2),
    /**
     * 企业类型
     */
    COMPANY(3),
    ;

    public final int val;

    CorporateType(int value) {
            this.val = value;
        }
}
