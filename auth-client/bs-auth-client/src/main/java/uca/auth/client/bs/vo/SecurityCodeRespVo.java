package uca.auth.client.bs.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/4 14:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityCodeRespVo {

    private String key;

    private String image;
}
