package uca.ops.user.bs.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uca.ops.user.bs.CustomizationConfiguration;
import uca.ops.user.bs.feign.AuthServerFeignClient;
import uca.ops.user.bs.feign.OpsSysFeignClient;
import uca.ops.user.bs.vo.UserRegisterVo;

import java.util.Arrays;

import static uca.ops.user.bs.CustomizationConfiguration.andy;

/**
 * Created by Andy Lv on 2019/5/19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Import(CustomizationConfiguration.class)
@Transactional
public class UserServiceTest {

    @SpyBean
    OpsSysFeignClient platformSysFeignClient;

    @SpyBean
    AuthServerFeignClient authServerFeignClient;

    @Autowired
    UserService userService;


    @Test
    public void register() {
        UserRegisterVo user = new UserRegisterVo();
        user.setStdSimpleUser(andy().getStdSimpleUser());
        user.setRoleIds(Arrays.asList("register"));
        userService.register(user);
    }
}
