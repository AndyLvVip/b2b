package test.platform;

import org.junit.Test;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/16 9:49
 */
public class BitUtilsTest {

    @Test
    public void bitValue() {
        for(int i = 0 ; i < 20; i ++) {
            System.out.println(1 << i);
        }
    }

    @Test
    public void bitAnd() {
        System.out.println(3 & 1);
        System.out.println(7 & 4);
        System.out.println(2 & 1);
        System.out.println(4 & 1);
    }

    @Test
    public void bitOr() {
        System.out.println(1 | (1 << 1));
    }
}
