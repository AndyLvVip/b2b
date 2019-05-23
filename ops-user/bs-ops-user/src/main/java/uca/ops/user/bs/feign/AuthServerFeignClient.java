package uca.ops.user.bs.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uca.base.user.StdSimpleUser;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/17 15:26
 */
@FeignClient(name = "bs-auth-server", primary = false)
public interface AuthServerFeignClient {

    @PostMapping("/user/register")
    void register(@RequestBody StdSimpleUser stdSimpleUser);
}
