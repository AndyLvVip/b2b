package uca.platform;

import java.util.UUID;

public class StdStringUtils {

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
