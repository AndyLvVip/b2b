package uca.ops.sys.bs.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uca.base.fs.constant.RoleTemplate;
import uca.ops.sys.CustomizationConfiguration;
import uca.ops.sys.bs.service.UserRoleService;
import uca.platform.StdStringUtils;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/15 10:31
 */
@SpringBootTest
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



    @Test
    public void linkUserRole() throws Exception {
        String userId = StdStringUtils.uuid();
        this.mockMvc.perform(post("/public/linkUserRole/{userId}/{roleId}", userId, RoleTemplate.REGISTER.val))
                .andExpect(status().isNoContent())
                .andDo(CustomizationConfiguration.restDocument(
                        pathParameters(
                                parameterWithName("userId").description("用户id")
                                , parameterWithName("roleId").description("角色id")
                        )
                ))
        ;
    }

}
