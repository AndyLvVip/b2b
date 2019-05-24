package uca.ops.user.bs.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uca.ops.user.domain.User;

import static uca.ops.user.bs.CustomizationConfiguration.andy;

/**
 * Created by Andy Lv on 2019/5/19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void insert() {
        User user = User.newInstance(andy().getStdSimpleUser());
        userRepository.insert(user, "Unit Test");
    }
}
