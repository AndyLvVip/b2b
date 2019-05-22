package uca.platform.sys.fs.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import uca.platform.StdStringUtils;
import uca.platform.sys.CustomizationConfiguration;
import uca.platform.sys.domain.Menu;
import uca.platform.sys.domain.Permission;
import uca.platform.sys.fs.repository.MenuRepository;
import uca.platform.sys.fs.service.PermissionService;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uca.platform.sys.CustomizationConfiguration.*;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/22 18:33
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(CustomizationConfiguration.class)
@RunWith(SpringRunner.class)
@Transactional
public class MenuControllerTest {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    PermissionService permissionService;

    @SpyBean
    MenuRepository menuRepository;

    @SpyBean
    UserInfoTokenServices userInfoTokenServices;

    @SpyBean
    OAuth2RestTemplate restTemplate;

    @Before
    public void setUp() {
        userInfoTokenServices.setRestTemplate(restTemplate);
    }


    @Test
    public void fetchOwnMenu() throws Exception {
        Permission permission = new Permission();
        permission.setMenuId(2L);
        permission.setPermission(1L);
        String accessToken = StdStringUtils.uuid();
        StdSimpleUser andy = andy();
        doReturn(Arrays.asList(permission)).when(permissionService).fetchOwnPermissions(andy.getId());
        doReturn(responseEntity(andy)).when(restTemplate).getForEntity(anyString(), any(Class.class));

        Menu top = new Menu();
        top.setParentId(null);
        top.setId(1L);
        top.setIcon("vip-center-icon");
        top.setName("会员中心");
        top.setSequence(new BigDecimal("1"));
        top.setUrl("#");

        Menu sub1 = new Menu();
        sub1.setParentId(1L);
        sub1.setId(2L);
        sub1.setIcon("base-info-icon");
        sub1.setName("基本信息");
        sub1.setSequence(new BigDecimal("1"));
        sub1.setUrl("/baseInfo/detail");

        doReturn(Arrays.asList(top, sub1)).when(menuRepository).findAll();
        this.mockMvc.perform(get("/menu/own")
                .header("Authorization", "Bearer " + accessToken)
        )
                .andExpect(status().isOk())
                .andDo(restDocument(
                        responseFields(
                                fieldWithPath("[].id").description("菜单id")
                                , fieldWithPath("[].icon").description("菜单图标")
                                , fieldWithPath("[].name").description("菜单名")
                                , fieldWithPath("[].sequence").description("菜单排序")
                                , fieldWithPath("[].url").description("菜单url")

                                , fieldWithPath("[].subMenus.[].id").description("菜单id")
                                , fieldWithPath("[].subMenus.[].parentId").description("上一级菜单id")
                                , fieldWithPath("[].subMenus.[].icon").description("菜单图标")
                                , fieldWithPath("[].subMenus.[].name").description("菜单名")
                                , fieldWithPath("[].subMenus.[].sequence").description("菜单排序")
                                , fieldWithPath("[].subMenus.[].url").description("菜单url")
                        )
                ))
        ;
    }
}
