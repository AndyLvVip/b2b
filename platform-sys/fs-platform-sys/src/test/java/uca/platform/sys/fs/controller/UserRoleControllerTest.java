package uca.platform.sys.fs.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uca.base.fs.constant.RoleTemplate;
import uca.base.user.StdSimpleUser;
import uca.platform.StdStringUtils;
import uca.platform.sys.CustomizationConfiguration;
import uca.platform.sys.domain.Permission;
import uca.platform.sys.fs.service.UserRoleService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uca.base.constant.Constants.USER;
import static uca.platform.sys.CustomizationConfiguration.restDocument;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/15 10:31
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(CustomizationConfiguration.class)
@RunWith(SpringRunner.class)
@Transactional
public class UserRoleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    UserRoleService userRoleService;

    @SpyBean
    OAuth2RestTemplate restTemplate;

    @SpyBean
    UserInfoTokenServices userInfoTokenServices;

    @Test
    public void linkUserRole() throws Exception {
        String userId = StdStringUtils.uuid();
        this.mockMvc.perform(post("/public/linkUserRole/{userId}/{roleId}", userId, RoleTemplate.REGISTER.val))
                .andExpect(status().isNoContent())
                .andDo(restDocument(
                        pathParameters(
                                parameterWithName("userId").description("用户id")
                                , parameterWithName("roleId").description("角色id")
                        )
                ))
        ;
    }

    @Test
    public void fetchAllPermissionList() throws Exception {
        Permission permission = new Permission();
        permission.setMenuId(2L);
        permission.setPermission(1L + (1 << 1) + (1 << 2) + (1 << 3));
        String userId = StdStringUtils.uuid();
        doReturn(Arrays.asList(permission)).when(userRoleService).fetchAllPermissionList(userId);
        String accessToken = StdStringUtils.uuid();
        Map<String, StdSimpleUser> body = new HashMap<>();
        StdSimpleUser stdUser = new StdSimpleUser();
        stdUser.setId(userId);
        stdUser.setName("Andy Lv");
        stdUser.setUsername("andy");

        body.put(USER, stdUser);
        ResponseEntity<Map<String, StdSimpleUser>> entity = new ResponseEntity(body, HttpStatus.OK);

        doReturn(entity).when(restTemplate).getForEntity(anyString(), any(Class.class));
        userInfoTokenServices.setRestTemplate(restTemplate);

        this.mockMvc.perform(get("/permission/user/")
                .header("Authorization", "Bearer " + accessToken)
        )
                .andExpect(status().isOk())
                .andDo(restDocument(
                        responseFields(
                                fieldWithPath("[].menuId").description("菜单id")
                                , fieldWithPath("[].permission").description("对该菜单拥有的权限总和")
                        )
                ))
        ;

    }
}
