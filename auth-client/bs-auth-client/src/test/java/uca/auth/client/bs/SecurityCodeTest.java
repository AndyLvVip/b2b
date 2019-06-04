package uca.auth.client.bs;

import org.junit.Test;
import uca.auth.client.bs.util.ImageUtils;
import uca.auth.client.bs.vo.SecurityCodeVo;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/4 16:03
 */
public class SecurityCodeTest {

    @Test
    public void base64Image() {
        SecurityCodeVo securityCode = ImageUtils.createSecurityCodeImage();
        System.out.println(securityCode.asBase64Image());
        System.out.println(securityCode.getCode());
    }
}
