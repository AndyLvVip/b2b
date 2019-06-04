package uca.auth.client.bs.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uca.platform.exception.InternalServerException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/4 14:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityCodeVo {

    private String code;

    private BufferedImage image;


    public String asBase64Image() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", bos);
            return "data:image/png;base64," + Base64.getEncoder()
                    .encodeToString(bos.toByteArray())
            ;
        } catch (IOException e) {
            throw new InternalServerException(e);
        }
    }
}
