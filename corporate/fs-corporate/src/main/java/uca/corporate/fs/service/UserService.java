package uca.corporate.fs.service;

import org.springframework.stereotype.Service;
import uca.base.user.StdUser;
import uca.corporate.domain.Corporate;
import uca.corporate.domain.User;
import uca.corporate.fs.repository.CorporateRepository;
import uca.corporate.fs.repository.UserRepository;

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

    public UserService(UserRepository userRepository, CorporateRepository corporateRepository) {
        this.userRepository = userRepository;
        this.corporateRepository = corporateRepository;
    }

    public StdUser fetchStdUserInfo(String userId) {
        User user = userRepository.forceFindById(userId);
        Corporate corporate = corporateRepository.forceFindById(user.getMemberOf());
        return null;
    }

}
