package uca.plat.file;

import java.util.Random;

public enum StoreLocationFlag {

    FILE("f", 10),
    IMAGE("i", 10),
    RICH_TEXT_FILE("r", 10),
    ;

    private final String flag;
    private final int count;
    private final Random random;

    StoreLocationFlag(String flag, int count) {
        this.flag = flag;
        this.count = count;
        this.random = new Random();
    }

    public String randomDir() {
        return this.flag + String.format("%02d", this.random.nextInt(this.count) + 1);
    }
}
