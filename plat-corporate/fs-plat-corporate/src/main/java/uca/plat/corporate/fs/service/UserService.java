package uca.plat.corporate.fs.service;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uca.base.fs.constant.RoleTemplate;
import uca.base.fs.user.StdUser;
import uca.base.user.StdPermission;
import uca.base.user.StdSimpleUser;
import uca.corporate.domain.Corporate;
import uca.corporate.domain.User;
import uca.plat.corporate.fs.feign.AuthServerFeignClient;
import uca.plat.corporate.fs.feign.PlatformSysFeignClient;
import uca.plat.corporate.fs.repository.CorporateRepository;
import uca.plat.corporate.fs.repository.UserRepository;

import java.util.Arrays;
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

    private final CorporateRepository corporateRepository;

    private final PlatformSysFeignClient platformSysFeignClient;

    private final CorporateService corporateService;

    private final AuthServerFeignClient authServerFeignClient;

    public UserService(UserRepository userRepository,
                       CorporateRepository corporateRepository,
                       PlatformSysFeignClient platformSysFeignClient,
                       CorporateService corporateService,
                       AuthServerFeignClient authServerFeignClient
                       ) {
        this.userRepository = userRepository;
        this.corporateRepository = corporateRepository;
        this.platformSysFeignClient = platformSysFeignClient;
        this.corporateService = corporateService;
        this.authServerFeignClient = authServerFeignClient;
    }

    public StdUser fetchStdUserInfo(String userId) {
        User user = userRepository.forceFindById(userId);
        Corporate corporate = corporateRepository.forceFindById(user.getMemberOf());
        List<StdPermission> stdPermissions = platformSysFeignClient.fetchUserAllPermission();
        StdUser stdUser = new StdUser();
        stdUser.setStdSimpleUser(user.toStdSimpleUser());
        stdUser.setStdCorporate(corporate.toStdCorporate());
        stdUser.setStdPermissionList(stdPermissions);
        return stdUser;
    }

    public User fetchUserDetailWithCorporate(String userId) {
        User user = userRepository.forceFindById(userId);
        Corporate corporate = corporateRepository.forceFindById(user.getMemberOf());
        user.setCorporate(corporate);
        return user;
    }

    private User create(StdSimpleUser simpleUser, String memberOf) {
        User user = User.newInstance(simpleUser, memberOf);
        userRepository.insert(user, SYSTEM);
        return user;
    }

    @GlobalTransactional
    @Transactional
    public void register(StdSimpleUser simpleUser) {
        Corporate corporate = corporateService.create(simpleUser.getUsername());
        User user = create(simpleUser, corporate.getId());
        platformSysFeignClient.linkUserRole(user.getId(), Arrays.asList(RoleTemplate.REGISTER.val));
        simpleUser.setId(user.getId());
        authServerFeignClient.register(simpleUser);
    }

}
