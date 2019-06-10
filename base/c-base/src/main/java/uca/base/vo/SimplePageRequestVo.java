package uca.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/5 20:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimplePageRequestVo {
    private int page;

    private int size;
}
