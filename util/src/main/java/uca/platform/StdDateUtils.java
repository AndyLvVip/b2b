package uca.platform;

import java.time.LocalDate;
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

    private static final DateTimeFormatter yyMMddHHmmssSSS = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");

    public static String now2yyMMddHHmmssSSS() {
        return LocalDateTime.now().format(yyMMddHHmmssSSS);
    }

    public static final DateTimeFormatter yyyy_MM_dd_HH_mm_ss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format2yyyy_MM_dd_HH_mm_ss(LocalDateTime dateTime) {
        return dateTime.format(yyyy_MM_dd_HH_mm_ss);
    }

    public static String now2yyyy_MM_dd_HH_mm_ss() {
        return format2yyyy_MM_dd_HH_mm_ss(LocalDateTime.now());
    }

    public static final DateTimeFormatter yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String format2yyyy_MM_dd(LocalDate date) {
        return date.format(yyyy_MM_dd);
    }

    public static LocalDate parse2yyyy_MM_dd(String date) {
        return LocalDate.parse(date, yyyy_MM_dd);
    }

    public static String now2yyyy_MM_dd() {
        return format2yyyy_MM_dd(LocalDate.now());
    }
}
