package uca.auth.client.bs.util;

import uca.auth.client.bs.vo.SecurityCodeVo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/4 14:44
 */
public class ImageUtils {

    private static final int WIDTH = 100;

    private static final int HEIGHT = 40;

    public static final int FONT_SIZE = 30;

    private static final char[] CHARS = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n'
            , 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
            , 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N'
            , 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
            , '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };


    public static SecurityCodeVo createSecurityCodeImage() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        Random rand = new Random();

        graphics.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
        int size = 4;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i++) {
            String c = CHARS[rand.nextInt(CHARS.length)] + "";
            graphics.setColor(genRandomColor());
            graphics.drawString(c, i * WIDTH / size, HEIGHT * 2 / 3);

            sb.append(c);
        }

        int lines = 5;
        for (int i = 0; i < lines; i++) {
            graphics.setColor(genRandomColor());
            graphics.drawLine(rand.nextInt(WIDTH), rand.nextInt(HEIGHT)
                    , rand.nextInt(WIDTH), rand.nextInt(HEIGHT)
            );
        }
        return new SecurityCodeVo(sb.toString(), image);
    }

    public static Color genRandomColor() {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

}
