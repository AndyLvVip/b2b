package uca.ops.user.bs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import uca.base.user.StdSimpleUser;
import uca.ops.user.bs.service.UserService;

import java.util.Map;

import static uca.base.constant.Constants.USER;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/21 15:55
 */
public class StdPrincipalExtractor implements PrincipalExtractor {

    private final ObjectMapper objectMapper;

    private final UserService userService;

    public StdPrincipalExtractor(ObjectMapper objectMapper, UserService userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Override
    public Object extractPrincipal(Map<String, Object> map) {
        StdSimpleUser stdSimpleUser = objectMapper.convertValue(map.get(USER), StdSimpleUser.class);
        return userService.fetchStdUserInfo(stdSimpleUser.getId());
    }
}
