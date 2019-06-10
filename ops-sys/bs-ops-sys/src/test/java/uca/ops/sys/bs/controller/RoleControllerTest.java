package uca.ops.sys.bs.controller;

import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import uca.base.vo.SimplePageRequestVo;
import uca.ops.sys.bs.CustomizationConfiguration;
import uca.ops.sys.bs.repository.*;
import uca.ops.sys.domain.*;
import uca.ops.sys.vo.RoleMenuVo;
import uca.ops.sys.vo.UserRoleReqVo;
import uca.ops.sys.vo.UserVo;
import uca.platform.StdStringUtils;
import uca.platform.json.StdObjectMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uca.ops.sys.bs.CustomizationConfiguration.*;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/5 18:17
 */
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(CustomizationConfiguration.class)
@RunWith(SpringRunner.class)
@Transactional
public class RoleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    RoleRepository roleRepository;

    @SpyBean
    CustomizationConfiguration.MockOpsUserFeignClient opsUserFeignClient;

    @SpyBean
    UserRoleRepository userRoleRepository;

    @Autowired
    StdObjectMapper stdObjectMapper;

    @SpyBean
    UserInfoTokenServices userInfoTokenServices;

    @SpyBean
    OAuth2RestTemplate restTemplate;

    @SpyBean
    PermissionRepository permissionRepository;

    @SpyBean
    PermissionUnitRepository permissionUnitRepository;

    @SpyBean
    MenuRepository menuRepository;

    @Before
    public void setUp() {
        userInfoTokenServices.setRestTemplate(restTemplate);
    }

    @Test
    public void searchRoles() throws Exception {
        SimplePageRequestVo pageable = new SimplePageRequestVo(0, 10);
        Role role = new Role();
        role.setId(StdStringUtils.uuid());
        role.setName("管理员");
        role.setVersion(1);
        role.setRemark("这是权限最高的角色");

        UserVo user = new UserVo();
        user.setId(StdStringUtils.uuid());
        user.setName("张三");

        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(user.getId());

        String accessToken = StdStringUtils.uuid();

        Page<Role> roles = new PageImpl<>(Arrays.asList(role), PageRequest.of(pageable.getPage(), pageable.getSize()), 1);

        doReturn(roles).when(roleRepository).search(any(UserRoleReqVo.class), any(Pageable.class));
        doReturn(Arrays.asList(user)).when(opsUserFeignClient).fetchList(any(List.class));
        doReturn(Arrays.asList(userRole)).when(userRoleRepository).fetchList(any(List.class));
        doReturn(CustomizationConfiguration.responseEntity(andy())).when(restTemplate).getForEntity(anyString(), any(Class.class));

        UserRoleReqVo vo = new UserRoleReqVo();
        vo.setRoleName("管理员");

        LinkedMultiValueMap<String, String> params = stdObjectMapper.convertValue(vo, new TypeReference<LinkedMultiValueMap<String, String>>() {});
        params.putAll(stdObjectMapper.convertValue(pageable, new TypeReference<LinkedMultiValueMap<String, String>>() {}));


        this.mockMvc.perform(get("/role")
                .header("Authorization", "Bearer " + accessToken)
                .params(params)
        )
                .andExpect(status().isOk())
                .andDo(restDocument(
                        requestHeaders(
                                headerWithName("Authorization").description("access token")
                        )
                        , requestParameters(
                                parameterWithName("roleName").description("角色名称")
                                , parameterWithName("page").description("页码，从0开始")
                                , parameterWithName("size").description("每页的记录数")
                        )
                        , responseFields(
                                fieldWithPath("content.[].role.id").description("角色id")
                                , fieldWithPath("content.[].role.name").description("角色名称")
                                , fieldWithPath("content.[].role.remark").description("角色备注")
                                , fieldWithPath("content.[].role.version").description("角色版本号")
                                , fieldWithPath("content.[].users.[].id").description("用户id")
                                , fieldWithPath("content.[].users.[].name").description("用户姓名")
                                , fieldWithPath("pageable.sort.sorted").ignored()
                                , fieldWithPath("pageable.sort.unsorted").ignored()
                                , fieldWithPath("pageable.sort.empty").ignored()
                                , fieldWithPath("pageable.offset").ignored()
                                , fieldWithPath("pageable.pageNumber").ignored()
                                , fieldWithPath("pageable.pageSize").ignored()
                                , fieldWithPath("pageable.paged").ignored()
                                , fieldWithPath("pageable.unpaged").ignored()
                                , fieldWithPath("totalElements").description("总的记录数")
                                , fieldWithPath("totalPages").description("总的页数")
                                , fieldWithPath("last").description("是否最后一页")
                                , fieldWithPath("number").description("页码，从0开始")
                                , fieldWithPath("size").description("每页记录数")
                                , fieldWithPath("sort.sorted").ignored()
                                , fieldWithPath("sort.unsorted").ignored()
                                , fieldWithPath("sort.empty").ignored()
                                , fieldWithPath("numberOfElements").description("当前页的记录数")
                                , fieldWithPath("first").description("是否为第一页")
                                , fieldWithPath("empty").description("是否为空")
                        )

                ))
        ;
    }

    @Test
    public void createRole() throws Exception {

        Role role = new Role();
        role.setName("测试角色");
        role.setRemark("测试使用的角色");

        doReturn(CustomizationConfiguration.responseEntity(andy())).when(restTemplate).getForEntity(anyString(), any(Class.class));
        this.mockMvc.perform(post("/role")
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(stdObjectMapper.toJson(role))
        )
                .andExpect(status().isNoContent())
        .andDo(restDocument(
                requestHeaders(
                        headerWithName("Authorization").description("access token")
                )
                , requestFields(
                        fieldWithPath("name").description("角色名称")
                        , fieldWithPath("remark").description("备注")
                )
        ))
        ;

    }

    @Test
    public void editRole() throws Exception {
        Role role = new Role();
        role.setId(StdStringUtils.uuid());
        role.setName("变更后的测试角色");
        role.setRemark("变更后的测试角色备注");
        role.setVersion(1);

        doReturn(CustomizationConfiguration.responseEntity(andy())).when(restTemplate).getForEntity(anyString(), any(Class.class));
        doReturn(role).when(roleRepository).forceFindById(role.getId());
        doNothing().when(roleRepository).update(any(Role.class), anyString());

        this.mockMvc.perform(put("/role/{id}", role.getId())
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(stdObjectMapper.toJson(role))
        ).andExpect(status().isNoContent())
                .andDo(
                        restDocument(
                                requestHeaders(
                                        headerWithName("Authorization").description("access token")
                                )
                                , requestFields(
                                        fieldWithPath("id").description("角色id")
                                        , fieldWithPath("name").description("角色名称")
                                        , fieldWithPath("remark").description("备注")
                                        , fieldWithPath("version").description("版本号")
                                )
                        )
                )
        ;
    }

    @Test
    public void deleteRole() throws Exception {
        Role role = new Role();
        role.setId(StdStringUtils.uuid());
        role.setVersion(1);

        doReturn(responseEntity(andy())).when(restTemplate).getForEntity(anyString(), any(Class.class));
        doReturn(role).when(roleRepository).forceFindById(role.getId());
        doNothing().when(roleRepository).delete(any(Role.class));

        this.mockMvc.perform(delete("/role/{id}", role.getId())
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(stdObjectMapper.toJson(role))
        )
                .andExpect(status().isNoContent())
                .andDo(
                        restDocument(
                                requestHeaders(
                                        headerWithName("Authorization").description("access token")
                                )
                                , requestFields(
                                        fieldWithPath("id").description("角色id")
                                        , fieldWithPath("version").description("版本号")
                                )
                        )
                );

    }

    @Test
    public void fetchPermissionForRole() throws Exception {
        Role role = new Role();
        role.setId(StdStringUtils.uuid());
        role.setRemark("角色备注");
        role.setName("测试角色");
        role.setVersion(1);

        doReturn(role).when(roleRepository).forceFindById(role.getId());

        Permission permission = new Permission();
        permission.setRoleId(role.getId());
        permission.setMenuId(2L);
        permission.setPermission(1);

        doReturn(Arrays.asList(permission)).when(permissionRepository).fetchRolePermissions(role.getId());

        PermissionUnit pu = new PermissionUnit();
        pu.setLabel("查看");
        pu.setMenuId(permission.getMenuId());
        pu.setUnit(1);

        PermissionUnit pu2 = new PermissionUnit();
        pu2.setLabel("创建");
        pu2.setMenuId(permission.getMenuId());
        pu2.setUnit(1 << 1);

        doReturn(Arrays.asList(pu, pu2)).when(permissionUnitRepository).fetchAllWithSequence();

        Menu menu = new Menu();
        menu.setId(1L);
        menu.setParentId(null);
        menu.setName("一级菜单");

        Menu mn2 = new Menu();
        mn2.setName("二级菜单");
        mn2.setParentId(menu.getId());
        mn2.setId(permission.getMenuId());

        doReturn(Arrays.asList(menu, mn2)).when(menuRepository).findAll();

        doReturn(responseEntity(andy())).when(restTemplate).getForEntity(anyString(), any(Class.class));

        this.mockMvc.perform(get("/role/{id}/permission", role.getId())
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
        )
                .andExpect(status().isOk())
                .andDo(restDocument(
                        requestHeaders(
                                headerWithName("Authorization").description("access token")
                        )
                        , pathParameters(
                                parameterWithName("id").description("角色id")
                        )
                        , responseFields(
                                fieldWithPath("role.id").description("角色id")
                                , fieldWithPath("role.remark").description("角色备注")
                                , fieldWithPath("role.name").description("角色名")
                                , fieldWithPath("role.version").description("角色版本号")
                                , fieldWithPath("menus.[].id").description("菜单id")
                                , fieldWithPath("menus.[].name").description("菜单名")
                                , fieldWithPath("menus.[].permissionUnits.[]").ignored()
                                , fieldWithPath("menus.[].subMenus.[].id").description("菜单id")
                                , fieldWithPath("menus.[].subMenus.[].parentId").description("父菜单id")
                                , fieldWithPath("menus.[].subMenus.[].name").description("菜单名")
                                , fieldWithPath("menus.[].subMenus.[].permissionUnits.[].menuId").description("权限单元所属菜单id")
                                , fieldWithPath("menus.[].subMenus.[].permissionUnits.[].label").description("权限单元标注")
                                , fieldWithPath("menus.[].subMenus.[].permissionUnits.[].unit").description("权限单元")
                                , fieldWithPath("menus.[].subMenus.[].permissionUnits.[].granted").description("权限单元是否已授权")
                        )
                ));
    }

    @Test
    public void editPermissionForRole() throws Exception {
        doReturn(responseEntity(andy())).when(restTemplate).getForEntity(anyString(), any(Class.class));

        Role role = new Role();
        role.setVersion(1);
        role.setId(StdStringUtils.uuid());

        doReturn(Collections.emptyList()).when(permissionRepository).fetchRolePermissions(role.getId());

        Menu mn2 = new Menu();
        mn2.setId(2L);

        PermissionUnit pu = new PermissionUnit();
        pu.setMenuId(mn2.getId());
        pu.setUnit(1);
        pu.setId(StdStringUtils.uuid());

        PermissionUnit pu2 = new PermissionUnit();
        pu2.setMenuId(mn2.getId());
        pu2.setUnit(1 << 1);
        pu2.setId(StdStringUtils.uuid());
        mn2.setPermissionUnits(Arrays.asList(pu, pu2));

        doReturn(Arrays.asList(pu, pu2)).when(permissionUnitRepository).fetchAllWithSequence();

        doReturn(role).when(roleRepository).forceFindById(role.getId());
        doNothing().when(roleRepository).update(any(Role.class), anyString());


        RoleMenuVo params = new RoleMenuVo();
        params.setRole(role);
        params.setMenus(Arrays.asList(mn2));

        this.mockMvc.perform(put("/role/{id}/permission", role.getId())
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(stdObjectMapper.toJson(params))
        )
                .andExpect(status().isNoContent())
                .andDo(
                        restDocument(
                                requestHeaders(
                                        headerWithName("Authorization").description("access token")
                                )
                                , pathParameters(
                                        parameterWithName("id").description("角色id")
                                )
                                , requestFields(
                                        fieldWithPath("role.id").description("角色id")
                                        , fieldWithPath("role.version").description("角色版本号")
                                        , fieldWithPath("menus.[].id").description("菜单id")
                                        , fieldWithPath("menus.[].permissionUnits.[].id").description("权限单元id")
                                        , fieldWithPath("menus.[].permissionUnits.[].unit").ignored()
                                        , fieldWithPath("menus.[].permissionUnits.[].menuId").ignored()
                                )
                        )
                );
    }
}
