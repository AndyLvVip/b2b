package uca.ops.sys.bs.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uca.ops.sys.vo.UserVo;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/5 17:31
 */
@FeignClient(name = "bs-ops-user", primary = false)
public interface OpsUserFeignClient {

    @GetMapping("/user/fetchList")
    List<UserVo> fetchList(@RequestParam("userIds") List<String> userIds);
}
