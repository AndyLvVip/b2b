package uca.auth.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;
import uca.auth.client.config.Config;
import uca.auth.client.vo.OAuth2TokenVo;
import uca.platform.StdStringUtils;
import uca.platform.json.StdObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by andy.lv
 * on: 2019/1/22 12:36
 */
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AuthClientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StdObjectMapper stdObjectMapper;

    @MockBean
    RestTemplate restTemplate;

    @MockBean
    StringRedisTemplate stringRedisTemplate;

    @MockBean
    Config.Client authClient;

    @Before
    public void setUp() {
        OAuth2TokenVo token = new OAuth2TokenVo();
        token.setAccess_token(StdStringUtils.uuid());
        token.setExpires_in(30 * 60);
        token.setScope("webclient");
        token.setToken_type("bearer");
        ResponseEntity<OAuth2TokenVo> response = ResponseEntity.ok(token);
        when(restTemplate.exchange(
                anyString()
                , any(HttpMethod.class)
                , any(HttpEntity.class)
                , ArgumentMatchers.<Class<OAuth2TokenVo>>any()
        )
        ).thenReturn(response);

        ValueOperations<String, String> valueOperations = (ValueOperations<String, String>) Mockito.mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(anyString())).thenReturn(StdStringUtils.uuid());
        doNothing().when(valueOperations).set(anyString(), anyString());

        when(authClient.getId()).thenReturn("id");
        when(authClient.getSecret()).thenReturn("secret");
        Config.Client.Scope web = new Config.Client.Scope();
        web.setName("scope");
        when(authClient.getWeb()).thenReturn(web);
    }

    private ResultActions _webLogin() throws Exception {
        return this.mockMvc.perform(post("/web/login")
                .with(httpBasic("dummy", "password"))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk());
    }

    @Test
    public void webLogin() throws Exception {
        _webLogin()
                .andDo(CustomizationConfiguration.restDocument(requestHeaders(
                        headerWithName("Authorization").description("Basic身份认证")
                        )
                        , responseFields(
                                fieldWithPath("access_token").description("访问使用token")
                                , fieldWithPath("token_type").description("Access token的类型")
                                , fieldWithPath("expires_in").description("过期时间，单位为秒")
                                , fieldWithPath("scope").description("Access token可访问的范围")
                        )
                ));
    }

    private ResultActions _mobileLogin() throws Exception {
        return this.mockMvc.perform(post("/mobile/login")
                .with(httpBasic("dummy", "password"))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
                ;
    }

    @Test
    public void mobileLogin() throws Exception {
        _mobileLogin()
                .andDo(CustomizationConfiguration.restDocument(requestHeaders(
                        headerWithName("Authorization").description("Basic身份认证")
                        )
                        , responseFields(
                                fieldWithPath("access_token").description("访问使用token")
                                , fieldWithPath("token_type").description("Access token的类型")
                                , fieldWithPath("expires_in").description("过期时间，单位为秒")
                                , fieldWithPath("scope").description("Access token可访问的范围")
                        )
                ))
        ;
    }

    @Test
    public void webRefreshToken() throws Exception {
        String result = _webLogin()
                .andReturn().getResponse().getContentAsString();
        OAuth2TokenVo token = stdObjectMapper.fromJson(result, OAuth2TokenVo.class);
        this.mockMvc.perform(post("/web/refreshToken")
                .header("Authorization", token.getAccess_token())
        )
        .andExpect(status().isOk())
                .andDo(CustomizationConfiguration.restDocument(requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                        )
                        , responseFields(
                                fieldWithPath("access_token").description("访问使用token")
                                , fieldWithPath("token_type").description("Access token的类型")
                                , fieldWithPath("expires_in").description("过期时间，单位为秒")
                                , fieldWithPath("scope").description("Access token可访问的范围")
                        )
                ))
        ;

    }

    @Test
    public void mobileRefreshToken() throws Exception {
        String result = _mobileLogin()
                .andReturn().getResponse().getContentAsString();
        OAuth2TokenVo token = stdObjectMapper.fromJson(result, OAuth2TokenVo.class);
        this.mockMvc.perform(post("/mobile/refreshToken")
                .header("Authorization", token.getAccess_token())
        )
                .andExpect(status().isOk())
                .andDo(CustomizationConfiguration.restDocument(requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                        )
                        , responseFields(
                                fieldWithPath("access_token").description("访问使用token")
                                , fieldWithPath("token_type").description("Access token的类型")
                                , fieldWithPath("expires_in").description("过期时间，单位为秒")
                                , fieldWithPath("scope").description("Access token可访问的范围")
                        )
                ))
        ;

    }
}
