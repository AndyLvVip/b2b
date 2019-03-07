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
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import uca.auth.client.config.Config;
import uca.auth.client.vo.OAuth2TokenVo;

import java.util.Base64;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by andy.lv
 * on: 2019/1/22 12:36
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(CustomizationConfiguration.class)
public class AuthClientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RestTemplate restTemplate;

    @MockBean
    RedisTemplate<String, String> redisTemplate;

    @MockBean
    Config.Client authClient;

    @Before
    public void setUp() {
        OAuth2TokenVo token = new OAuth2TokenVo();
        token.setAccess_token(UUID.randomUUID().toString());
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

        ValueOperations valueOperations = Mockito.mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        doNothing().when(valueOperations).set(anyString(), anyString());

        when(authClient.getId()).thenReturn("id");
        when(authClient.getSecret()).thenReturn("secret");
        Config.Client.Scope web = new Config.Client.Scope();
        web.setName("scope");
        when(authClient.getWeb()).thenReturn(web);
    }

    @Test
    public void login() throws Exception {
        this.mockMvc.perform(post("/login")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("dummy:password".getBytes()))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
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
}
