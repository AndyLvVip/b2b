package uca.corporate.fs.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import uca.base.user.StdSimpleUser;
import uca.corporate.fs.CustomizationConfiguration;
import uca.corporate.fs.feign.AuthServerFeignClient;
import uca.corporate.fs.feign.PlatformSysFeignClient;

/**
 * Created by Andy Lv on 2019/5/19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Import(CustomizationConfiguration.class)
public class UserServiceTest {

    @SpyBean
    PlatformSysFeignClient platformSysFeignClient;

    @SpyBean
    AuthServerFeignClient authServerFeignClient;

    @Autowired
    UserService userService;


    @Test
    public void register() {
        StdSimpleUser stdSimpleUser = new StdSimpleUser();
        stdSimpleUser.setName("User Service Test");
        stdSimpleUser.setUsername("exception");
        stdSimpleUser.setPhone("13800138000");
        stdSimpleUser.setPassword("password");
        userService.register(stdSimpleUser);
    }
}
