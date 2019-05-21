package uca.corporate.fs.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uca.base.user.StdPermission;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/17 8:50
 */
@FeignClient(name = "fs-platform-sys", primary = false)
public interface PlatformSysFeignClient {

    @GetMapping("/permission/user")
    List<StdPermission> fetchUserAllPermission();

    @PostMapping("/public/linkUserRole/{userId}/{roleId}")
    void linkUserRole(@PathVariable("userId") String userId, @PathVariable("roleId") String roleId);

}
