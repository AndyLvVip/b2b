package uca.base.constant;

import org.junit.Test;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/16 9:11
 */
public class PrimeTest {

    @Test
    public void get20Primes() {
        int c = 0;
        long v = 1;
        for(int i = 2; i < Integer.MAX_VALUE; i++) {
            int r = (int) Math.sqrt(i);
            boolean prime = true;
            for(int j = 2; j <= r; j++) {
                if(i % j == 0) {
                    prime = false;
                }
            }
            if(prime) {
                c++;
                System.out.println(String.format("Prime: %d, Multiply value: %d", i, v *= i));
                if(20 == c) {
                    break;
                }
            }
        }
    }
}
