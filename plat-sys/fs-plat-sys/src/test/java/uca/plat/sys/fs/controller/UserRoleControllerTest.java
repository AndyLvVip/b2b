package uca.plat.sys.fs.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uca.base.fs.constant.RoleTemplate;
import uca.plat.sys.fs.CustomizationConfiguration;
import uca.plat.sys.fs.service.UserRoleService;
import uca.platform.StdStringUtils;
import uca.platform.json.StdObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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

    @Autowired
    StdObjectMapper stdObjectMapper;

    @Test
    public void linkUserRole() throws Exception {
        String userId = StdStringUtils.uuid();
        List<String> roleIds = Arrays.asList(RoleTemplate.REGISTER.val);
        this.mockMvc.perform(post("/public/linkUserRole/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(stdObjectMapper.toJson(roleIds))
        )
                .andExpect(status().isNoContent())
                .andDo(CustomizationConfiguration.restDocument(
                        pathParameters(
                                parameterWithName("userId").description("用户id")
                        )
                        , requestFields(
                                fieldWithPath("[]").description("角色id数组")
                        )
                ))
        ;

    }

}
