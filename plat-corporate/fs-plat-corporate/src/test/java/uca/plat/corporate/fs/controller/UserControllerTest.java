package uca.plat.corporate.fs.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uca.base.constant.CorporateType;
import uca.base.fs.constant.RoleTemplate;
import uca.base.fs.user.StdUser;
import uca.base.user.StdPermission;
import uca.base.user.StdSimpleUser;
import uca.corporate.domain.Corporate;
import uca.corporate.domain.User;
import uca.plat.corporate.fs.CustomizationConfiguration;
import uca.plat.corporate.fs.service.UserService;
import uca.platform.StdStringUtils;
import uca.platform.json.StdObjectMapper;

import java.time.LocalDateTime;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uca.base.constant.Constants.SYSTEM;
import static uca.plat.corporate.fs.CustomizationConfiguration.*;

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

    @MockBean
    UserService userService;

    @SpyBean
    OAuth2RestTemplate restTemplate;

    @SpyBean
    UserInfoTokenServices userInfoTokenServices;

    @SpyBean
    PrincipalExtractor principalExtractor;

    @Before
    public void setUp() {
        userInfoTokenServices.setRestTemplate(restTemplate);
    }

    @Test
    public void register() throws Exception {
        StdSimpleUser stdSimpleUser = new StdSimpleUser();
        stdSimpleUser.setUsername("daisy");
        stdSimpleUser.setName("张三");
        stdSimpleUser.setPhone("13800138000");
        stdSimpleUser.setPassword("password");
        stdSimpleUser.setEmail("zhangsan@ucacc.com");

        doNothing().when(userService).register(any(StdSimpleUser.class));

        this.mockMvc.perform(post("/public/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(stdObjectMapper.toJson(stdSimpleUser))
        )
                .andExpect(status().isNoContent())
                .andDo(restDocument(
                        requestFields(
                                fieldWithPath("username").description("登录名")
                                , fieldWithPath("name").description("姓名")
                                , fieldWithPath("phone").description("电话号码")
                                , fieldWithPath("password").description("密码")
                                , fieldWithPath("email").description("邮箱地址")
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
        user.setMemberOf(andy.getStdCorporate().getId());
        user.setQq("12345678");
        user.setCreatedOn(LocalDateTime.now());
        user.setCreatedBy("system");
        user.setVersion(1);

        Corporate corporate = new Corporate();
        user.setCorporate(corporate);
        corporate.setId(andy.getStdCorporate().getId());
        corporate.setActive(true);
        corporate.setLastRoleId(RoleTemplate.REGISTER.val);
        corporate.setVerified(true);
        corporate.setType(andy.getStdCorporate().getType());
        corporate.setName(andy.getStdCorporate().getName());
        corporate.setAddressId(StdStringUtils.uuid());
        corporate.setLogoFileId(StdStringUtils.uuid());
        corporate.setPhone("0757-88886666");
        corporate.setFaxNum("87654321");
        corporate.setWebsite("https://www.ucacc.com");
        corporate.setCreatedOn(LocalDateTime.now());
        corporate.setCreatedBy(SYSTEM);
        corporate.setVersion(1);
        doReturn(user).when(userService).fetchUserDetailWithCorporate(andy.getStdSimpleUser().getId());

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
                                , fieldWithPath("memberOf").description("所有企业的id")
                                , fieldWithPath("qq").description("QQ")
                                , fieldWithPath("version").description("用户记录的版本号")
                                , fieldWithPath("corporate.id").description("企业id")
                                , fieldWithPath("corporate.active").description("是否可用")
                                , fieldWithPath("corporate.lastRoleId").description("当前的角色")
                                , fieldWithPath("corporate.verified").description("是否已验证")
                                , fieldWithPath("corporate.type").description(String.format("未知：%d; 个人：%d; 企业：%d", CorporateType.UNKNOWN.val, CorporateType.PERSON.val, CorporateType.COMPANY.val))
                                , fieldWithPath("corporate.name").description("企业名称")
                                , fieldWithPath("corporate.addressId").description("企业的地址id")
                                , fieldWithPath("corporate.logoFileId").description("企业logo文件id")
                                , fieldWithPath("corporate.phone").description("企业电话")
                                , fieldWithPath("corporate.faxNum").description("企业传真号")
                                , fieldWithPath("corporate.website").description("企业网址")
                                , fieldWithPath("corporate.version").description("企业记录版本号")
                        )
                ));
    }
}
