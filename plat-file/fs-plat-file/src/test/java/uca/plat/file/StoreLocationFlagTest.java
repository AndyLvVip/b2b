package uca.plat.file;

import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class StoreLocationFlagTest {

    @Test
    public void randomDir() {
        Set<String> result = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            result.add(StoreLocationFlag.FILE.randomDir());
        }
        System.out.println(result);
        assertTrue(result.size() <= 10);
    }

    @Test
    public void randomNum() {
        Set<Integer> num = new HashSet<>();
        for(int i = 0; i < 100; i++) {
            num.add(new Random().nextInt(10));
        }
        System.out.println(num);
        assertTrue(num.size() <= 10);
    }
}
