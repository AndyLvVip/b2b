package uca.ops.user.bs.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uca.base.user.StdPermission;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/17 8:50
 */
@FeignClient(name = "bs-ops-sys", primary = false)
public interface OpsSysFeignClient {

    @GetMapping("/permission/own")
    List<StdPermission> fetchUserAllPermission();

    @PostMapping("/public/linkUserRole/{userId}")
    void linkUserRole(@PathVariable("userId") String userId, @RequestBody List<String> roleIds);

}
