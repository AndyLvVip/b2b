package uca.ops.sys.bs.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uca.base.user.StdSimpleUser;
import uca.ops.sys.bs.CustomizationConfiguration;
import uca.ops.sys.bs.service.PermissionService;
import uca.ops.sys.domain.Permission;
import uca.platform.StdStringUtils;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/22 17:52
 */
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(CustomizationConfiguration.class)
@RunWith(SpringRunner.class)
@Transactional
public class PermissionControllerTest {

    @SpyBean
    PermissionService permissionService;

    @SpyBean
    OAuth2RestTemplate restTemplate;

    @SpyBean
    UserInfoTokenServices userInfoTokenServices;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void fetchOwnPermissions() throws Exception {
        Permission permission = new Permission();
        permission.setMenuId(2L);
        permission.setPermission(1L + (1 << 1) + (1 << 2) + (1 << 3));
        String accessToken = StdStringUtils.uuid();
        StdSimpleUser andy = CustomizationConfiguration.andy();
        doReturn(Arrays.asList(permission)).when(permissionService).fetchOwnPermissions(andy.getId());
        Mockito.doReturn(CustomizationConfiguration.responseEntity(andy)).when(restTemplate).getForEntity(anyString(), any(Class.class));
        userInfoTokenServices.setRestTemplate(restTemplate);

        this.mockMvc.perform(get("/permission/own")
                .header("Authorization", "Bearer " + accessToken)
        )
                .andExpect(status().isOk())
                .andDo(CustomizationConfiguration.restDocument(
                        responseFields(
                                fieldWithPath("[].menuId").description("菜单id")
                                , fieldWithPath("[].permission").description("对该菜单拥有的权限总和")
                        )
                ))
        ;

    }
}
