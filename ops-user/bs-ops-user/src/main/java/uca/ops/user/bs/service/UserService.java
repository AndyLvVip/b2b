package uca.ops.user.bs.service;

import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uca.base.bs.user.StdUser;
import uca.base.user.StdPermission;
import uca.base.user.StdSimpleUser;
import uca.ops.user.bs.feign.AuthServerFeignClient;
import uca.ops.user.bs.feign.OpsSysFeignClient;
import uca.ops.user.bs.repository.UserRepository;
import uca.ops.user.bs.vo.UserRegisterVo;
import uca.ops.user.domain.User;

import java.util.Collections;
import java.util.List;

import static uca.base.constant.Constants.SYSTEM;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/16 15:34
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    private final OpsSysFeignClient opsSysFeignClient;

    private final AuthServerFeignClient authServerFeignClient;

    public UserService(UserRepository userRepository,
                       OpsSysFeignClient opsSysFeignClient,
                       AuthServerFeignClient authServerFeignClient
                       ) {
        this.userRepository = userRepository;
        this.opsSysFeignClient = opsSysFeignClient;
        this.authServerFeignClient = authServerFeignClient;
    }

    public StdUser fetchStdUserInfo(String userId) {
        User user = userRepository.forceFindById(userId);
        List<StdPermission> stdPermissions = opsSysFeignClient.fetchUserAllPermission();
        StdUser stdUser = new StdUser();
        stdUser.setStdSimpleUser(user.toStdSimpleUser());
        stdUser.setStdPermissionList(stdPermissions);
        return stdUser;
    }

    public User fetchUserDetail(String userId) {
        User user = userRepository.forceFindById(userId);
        return user;
    }

    private User create(StdSimpleUser simpleUser) {
        User user = User.newInstance(simpleUser);
        userRepository.insert(user, SYSTEM);
        return user;
    }

    @GlobalTransactional
    @Transactional
    public void register(UserRegisterVo vo) {
        User user = create(vo.getStdSimpleUser());
        opsSysFeignClient.linkUserRole(user.getId(), vo.getRoleIds());
        vo.getStdSimpleUser().setId(user.getId());
        authServerFeignClient.register(vo.getStdSimpleUser());
    }

    public List<User> fetchList(List<String> userIds) {
        if(CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return userRepository.fetchList(userIds);
    }
}
