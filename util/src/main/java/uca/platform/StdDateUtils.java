package uca.platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by @author andy
 * On @date 19-1-25 下午10:49
 */
public class StdDateUtils {
    private static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static String now2yyyyMMdd() {
        return LocalDateTime.now().format(yyyyMMdd);
    }
}
