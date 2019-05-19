package uca.corporate.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uca.base.user.StdSimpleUser;
import uca.corporate.fs.feign.AuthServerFeignClient;
import uca.corporate.fs.feign.PlatformSysFeignClient;
import uca.corporate.fs.service.UserService;

/**
 * Created by Andy Lv on 2019/5/19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    PlatformSysFeignClient platformSysFeignClient;

    @Autowired
    AuthServerFeignClient authServerFeignClient;

    @Autowired
    UserService userService;


    @Test
    public void register() {
//        doNothing().when(platformSysFeignClient).linkUserRole(anyString(), anyString());
//        doNothing().when(authServerFeignClient).register(any(StdSimpleUser.class));
        StdSimpleUser stdSimpleUser = new StdSimpleUser();
        stdSimpleUser.setName("User Service Test");
        stdSimpleUser.setUsername("userServiceTest");
        stdSimpleUser.setPhone("13800138000");
        stdSimpleUser.setPassword("password");
        userService.register(stdSimpleUser);
    }
}
