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
import org.springframework.boot.test.mock.mockito.SpyBean;
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
import uca.auth.client.bs.service.SecurityCodeService;
import uca.auth.client.bs.vo.SecurityCodeReqVo;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uca.auth.client.CustomizationConfiguration.restDocument;

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

    @SpyBean
    SecurityCodeService securityCodeService;

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

        when(authClient.getId()).thenReturn("id");
        when(authClient.getSecret()).thenReturn("secret");
        Config.Client.Scope web = new Config.Client.Scope();
        web.setName("scope");
        when(authClient.getWeb()).thenReturn(web);
    }

    private ResultActions _webLogin(SecurityCodeReqVo vo) throws Exception {
        return this.mockMvc.perform(post("/web/login")
                .with(httpBasic("dummy", "password"))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(stdObjectMapper.toJson(vo))
        ).andExpect(status().isOk());
    }

    @Test
    public void genSecurityCode() throws Exception {
        ValueOperations<String, String> valueOperations = (ValueOperations<String, String>) Mockito.mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        doNothing().when(valueOperations).set(anyString(), anyString());
        this.mockMvc.perform(post("/securityCode/gen"))
                .andExpect(status().isOk())
                .andDo(restDocument(
                        responseFields(
                                fieldWithPath("key").description("验证码key")
                                , fieldWithPath("image").description("验证码图片，以Base64文本返回")
                        )
                ));
    }

    @Test
    public void webLogin() throws Exception {
        SecurityCodeReqVo reqVo = new SecurityCodeReqVo();
        reqVo.setKey(StdStringUtils.uuid());
        reqVo.setValue("12ab");
        ValueOperations<String, String> valueOperations = (ValueOperations<String, String>) Mockito.mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        doNothing().when(valueOperations).set(anyString(), anyString());
        doNothing().when(securityCodeService).validateSecurityCode(any(SecurityCodeReqVo.class));
        _webLogin(reqVo)
                .andDo(restDocument(requestHeaders(
                        headerWithName("Authorization").description("Basic身份认证")
                        )
                        , requestFields(
                                fieldWithPath("key").description("验证码的key")
                                , fieldWithPath("value").description("验证码")
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
        ValueOperations<String, String> valueOperations = (ValueOperations<String, String>) Mockito.mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        doNothing().when(valueOperations).set(anyString(), anyString());
        _mobileLogin()
                .andDo(restDocument(requestHeaders(
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
        SecurityCodeReqVo reqVo = new SecurityCodeReqVo();
        reqVo.setKey(StdStringUtils.uuid());
        reqVo.setValue("12ab");
        ValueOperations<String, String> valueOperations = (ValueOperations<String, String>) Mockito.mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(anyString())).thenReturn(StdStringUtils.uuid());
        doNothing().when(valueOperations).set(anyString(), anyString());
        doNothing().when(securityCodeService).validateSecurityCode(any(SecurityCodeReqVo.class));

        String result = _webLogin(reqVo)
                .andReturn().getResponse().getContentAsString();
        OAuth2TokenVo token = stdObjectMapper.fromJson(result, OAuth2TokenVo.class);
        this.mockMvc.perform(post("/web/refreshToken")
                .header("Authorization", token.getAccess_token())
        )
        .andExpect(status().isOk())
                .andDo(restDocument(requestHeaders(
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
        ValueOperations<String, String> valueOperations = (ValueOperations<String, String>) Mockito.mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(anyString())).thenReturn(StdStringUtils.uuid());
        doNothing().when(valueOperations).set(anyString(), anyString());
        String result = _mobileLogin()
                .andReturn().getResponse().getContentAsString();
        OAuth2TokenVo token = stdObjectMapper.fromJson(result, OAuth2TokenVo.class);
        this.mockMvc.perform(post("/mobile/refreshToken")
                .header("Authorization", token.getAccess_token())
        )
                .andExpect(status().isOk())
                .andDo(restDocument(requestHeaders(
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
