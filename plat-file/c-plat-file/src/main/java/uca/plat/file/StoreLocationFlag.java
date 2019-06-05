package uca.plat.file;

import java.util.Random;

public enum StoreLocationFlag {

    /**
     * 文件
     */
    FILE("f", 10),
    /**
     * 图片
     */
    IMAGE("i", 10),
    /**
     * 富文件用到的文件资源
     */
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
