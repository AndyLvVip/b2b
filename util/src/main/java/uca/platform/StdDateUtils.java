package uca.platform;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by @author andy
 * On @date 19-1-25 下午10:49
 */
public class StdDateUtils {
    private static final DateTimeFormatter STD_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String now2yyyyMMdd() {
        return LocalDateTime.now().format(STD_YYYYMMDD);
    }

    private static final DateTimeFormatter STD_YYMMDDHHMMSS_SSS = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");

    public static String now2yyMMddHHmmssSSS() {
        return LocalDateTime.now().format(STD_YYMMDDHHMMSS_SSS);
    }

    public static final DateTimeFormatter STD_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format2StdyyyyMMddHHmmssWithSeparator(LocalDateTime dateTime) {
        return dateTime.format(STD_YYYY_MM_DD_HH_MM_SS);
    }

    public static String now2StdyyyyMMddHHmmss() {
        return format2StdyyyyMMddHHmmssWithSeparator(LocalDateTime.now());
    }

    public static final DateTimeFormatter STD_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String format2StdyyyyMMddWithSeparator(LocalDate date) {
        return date.format(STD_YYYY_MM_DD);
    }

    public static LocalDate parse2StdyyyyMMddWithSeparator(String date) {
        return LocalDate.parse(date, STD_YYYY_MM_DD);
    }

    public static String now2StdyyyyMMddWithSeparator() {
        return format2StdyyyyMMddWithSeparator(LocalDate.now());
    }
}
