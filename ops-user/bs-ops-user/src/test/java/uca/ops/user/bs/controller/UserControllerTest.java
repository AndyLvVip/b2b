package uca.ops.user.bs.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uca.base.bs.user.StdUser;
import uca.base.user.StdPermission;
import uca.base.user.StdSimpleUser;
import uca.ops.user.bs.CustomizationConfiguration;
import uca.ops.user.bs.repository.UserRepository;
import uca.ops.user.bs.service.UserService;
import uca.ops.user.bs.vo.UserRegisterVo;
import uca.ops.user.domain.User;
import uca.platform.StdStringUtils;
import uca.platform.json.StdObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uca.ops.user.bs.CustomizationConfiguration.*;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/21 11:01
 */
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(CustomizationConfiguration.class)
@RunWith(SpringRunner.class)
@Transactional
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StdObjectMapper stdObjectMapper;

    @SpyBean
    UserService userService;

    @SpyBean
    OAuth2RestTemplate restTemplate;

    @SpyBean
    UserInfoTokenServices userInfoTokenServices;

    @SpyBean
    PrincipalExtractor principalExtractor;

    @SpyBean
    UserRepository userRepository;

    @Before
    public void setUp() {
        userInfoTokenServices.setRestTemplate(restTemplate);
    }

    @Test
    public void register() throws Exception {
        StdSimpleUser andy = andy().getStdSimpleUser();
        andy.setId(null);
        andy.setPassword("password");
        List<String> roleIds = Arrays.asList("register");

        UserRegisterVo user = new UserRegisterVo();
        user.setStdSimpleUser(andy);
        user.setRoleIds(roleIds);
        doNothing().when(userService).register(any(UserRegisterVo.class));

        this.mockMvc.perform(post("/public/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(stdObjectMapper.toJson(user))
        )
                .andExpect(status().isNoContent())
                .andDo(restDocument(
                        requestFields(
                                fieldWithPath("stdSimpleUser.username").description("登录名")
                                , fieldWithPath("stdSimpleUser.name").description("姓名")
                                , fieldWithPath("stdSimpleUser.phone").description("电话号码")
                                , fieldWithPath("stdSimpleUser.password").description("密码")
                                , fieldWithPath("stdSimpleUser.email").description("邮箱地址")
                                , fieldWithPath("roleIds.[]").description("角色id数组")
                        )
                ))
        ;

    }

    @Test
    public void userDetail4NoToken() throws Exception {
        StdUser andy = andy();
        doReturn(responseEntity(andy.getStdSimpleUser())).when(restTemplate).getForEntity(anyString(), any(Class.class));
        doReturn(andy).when(principalExtractor).extractPrincipal(any(Map.class));

        this.mockMvc.perform(get("/user/detail"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void userDetail4NoPermission() throws Exception {
        StdUser andy = andy();
        doReturn(responseEntity(andy.getStdSimpleUser())).when(restTemplate).getForEntity(anyString(), any(Class.class));
        doReturn(andy).when(principalExtractor).extractPrincipal(any(Map.class));

        this.mockMvc.perform(get("/user/detail")
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void userDetail4InvalidPermission() throws Exception {
        StdUser andy = andy();
        andy.getStdPermissionList().add(new StdPermission(2, 1 << 4));
        doReturn(responseEntity(andy.getStdSimpleUser())).when(restTemplate).getForEntity(anyString(), any(Class.class));
        doReturn(andy).when(principalExtractor).extractPrincipal(any(Map.class));

        this.mockMvc.perform(get("/user/detail")
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void userDetail() throws Exception {
        StdUser andy = andy();
        andy.getStdPermissionList().add(new StdPermission(2, 1));
        doReturn(responseEntity(andy.getStdSimpleUser())).when(restTemplate).getForEntity(anyString(), any(Class.class));
        doReturn(andy).when(principalExtractor).extractPrincipal(any(Map.class));
        User user = new User();
        user.setId(andy.getStdSimpleUser().getId());
        user.setUsername(andy.getStdSimpleUser().getUsername());
        user.setName(andy.getStdSimpleUser().getName());
        user.setPhone(andy.getStdSimpleUser().getPhone());
        user.setEmail(andy.getStdSimpleUser().getEmail());
        user.setEnabled(true);
        user.setQq("12345678");
        user.setCreatedOn(LocalDateTime.now());
        user.setCreatedBy("system");
        user.setVersion(1);

        doReturn(user).when(userService).fetchUserDetail(andy.getStdSimpleUser().getId());

        this.mockMvc.perform(get("/user/detail")
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
        )
                .andExpect(status().isOk())
                .andDo(restDocument(
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        )
                        , responseFields(
                                fieldWithPath("id").description("用户id")
                                , fieldWithPath("username").description("用户名")
                                , fieldWithPath("name").description("姓名")
                                , fieldWithPath("phone").description("手机号码")
                                , fieldWithPath("email").description("邮箱地址")
                                , fieldWithPath("enabled").description("是否可用")
                                , fieldWithPath("qq").description("QQ")
                                , fieldWithPath("version").description("用户记录的版本号")
                        )
                ));
    }

    @Test
    public void fetchUserList() throws Exception {
        StdUser andy = andy();
        doReturn(responseEntity(andy.getStdSimpleUser())).when(restTemplate).getForEntity(anyString(), any(Class.class));
        doReturn(andy).when(principalExtractor).extractPrincipal(any(Map.class));

        String uid = StdStringUtils.uuid();

        User user = new User();
        user.setId(uid);
        user.setName("张三");

        List<User> users = new ArrayList<>();
        users.add(user);

        doReturn(users).when(userRepository).fetchList(any(List.class));

        this.mockMvc.perform(get("/user/fetchList")
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
                .param("userIds", uid)

        ).andExpect(status().isOk())
                .andDo(restDocument(
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        )
                        , requestParameters(
                                parameterWithName("userIds").description("用户id数组")
                        )
                        , responseFields(
                                fieldWithPath("[].id").description("用户id")
                                , fieldWithPath("[].name").description("姓名")
                        )
                ))
        ;
    }
}
