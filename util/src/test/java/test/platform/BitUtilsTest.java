package test.platform;

import org.junit.Test;
import uca.platform.BitUtils;

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
            System.out.println(BitUtils.bitValue(i));
        }
    }

    @Test
    public void bitAnd() {
        System.out.println(3 & 1);
        System.out.println(7 & 4);
        System.out.println(2 & 1);
        System.out.println(4 & 1);
    }
}
