package uca.security.auth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import uca.base.user.StdSimpleUser;
import uca.platform.StdStringUtils;
import uca.platform.json.StdObjectMapper;
import uca.security.auth.domain.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uca.security.auth.CustomizationConfiguration.restDocument;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(CustomizationConfiguration.class)
@RunWith(SpringRunner.class)
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StdObjectMapper stdObjectMapper;

    @MockBean
    private ClientDetailsService clientDetailsService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 7;

    private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 10;


    @Before
    public void setUp() {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId("client-1");
        clientDetails.setClientSecret(passwordEncoder.encode("client-1-secret"));
        clientDetails.setAuthorizedGrantTypes(Arrays.asList("password", "refresh_token"));
        clientDetails.setScope(Arrays.asList("webclient", "mobileclient"));
        clientDetails.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
        clientDetails.setRefreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
        when(clientDetailsService.loadClientByClientId("client-1")).thenReturn(clientDetails);

        StdSimpleUser vo = dummy();
        User user = User.newInstance(vo);
        user.setId(StdStringUtils.uuid());
        user.setCreatedOn(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode("password"));
        when(userDetailsService.loadUserByUsername("dummy")).thenReturn(user);
    }

    private StdSimpleUser dummy() {
        StdSimpleUser user = new StdSimpleUser();
        user.setUsername("dummy");
        user.setName("Daisy GB");
        user.setPhone("13800138000");
        user.setEmail("dummy@email.com");
        return user;
    }

    @Test
    public void userRegister() throws Exception {
        StdSimpleUser dummy = dummy();
        dummy.setPassword("password");
        dummy.setId(StdStringUtils.uuid());
        this.mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(stdObjectMapper.toJson(dummy)))
                .andExpect(status().isNoContent())
                .andDo(restDocument(requestFields(
                        fieldWithPath("id").description("用户id"),
                        fieldWithPath("username").description("登录用户名"),
                        fieldWithPath("name").optional().description("用户姓名"),
                        fieldWithPath("password").description("登录密码"),
                        fieldWithPath("phone").description("手机号码"),
                        fieldWithPath("email").optional().description("邮箱")
                )))
        ;
    }

    @Test
    public void userLogin() throws Exception {
        passwordLogin("webclient")
                .andDo(restDocument(requestHeaders(
                        headerWithName("Authorization").description("Basic 鉴权")
                        )
                        , requestParameters(
                                parameterWithName("username").description("用户登录名")
                                , parameterWithName("password").description("登录密码")
                                , parameterWithName("grant_type").description("授权类型")
                                , parameterWithName("scope").description("授权范围")
                        ),
                        responseFields(
                                fieldWithPath("access_token").description("Access token")
                                , fieldWithPath("token_type").description("token类型")
                                , fieldWithPath("refresh_token").description("刷新token")
                                , fieldWithPath("expires_in").description("过期时间")
                                , fieldWithPath("scope").description("登录范围")
                        )
                ))
        ;

    }

    private ResultActions passwordLogin(String scope) throws Exception {
        return this.mockMvc.perform(post("/oauth/token")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("client-1:client-1-secret".getBytes()))
                .param("username", "dummy")
                .param("password", "password")
                .param("grant_type", "password")
                .param("scope", scope)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                ;
    }

    private ResultActions refreshTokenLogin(String refreshToken) throws Exception {
        return this.mockMvc.perform(post("/oauth/token")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("client-1:client-1-secret".getBytes()))
                .param("refresh_token", refreshToken)
                .param("grant_type", "refresh_token")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );
    }




    @Test
    public void invalidUserLogin() throws Exception {
        this.mockMvc.perform(post("/oauth/token")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("client-1:client-1-secret".getBytes()))
                .param("username", "dummy")
                .param("password", "invalidPassword")
                .param("grant_type", "password")
                .param("scope", "webclient")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isBadRequest())
                .andDo(print())
        ;

    }

    private String loginAndGetAccessToken(String scope) throws Exception {
        String resultString = passwordLogin(scope)
                .andReturn().getResponse().getContentAsString();
        return accessToken(resultString);
    }

    private String accessToken(String responseBody) {
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(responseBody).get("access_token").toString();
    }

    private String refreshToken(String responseBody) {
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(responseBody).get("refresh_token").toString();
    }

    @Test
    public void userCredential() throws Exception {
        String accessToken = loginAndGetAccessToken("webclient");

        this.mockMvc.perform(get("/user")
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andDo(restDocument(requestHeaders(
                        headerWithName("Authorization").description("Bearer token")
                        )
                        , responseFields(
                                fieldWithPath("user.id").description("用户Id")
                                , fieldWithPath("user.username").description("用户登录名")
                                , fieldWithPath("user.name").description("用户姓名")
                                , fieldWithPath("user.phone").description("用户手机号码")
                                , fieldWithPath("user.email").description("用户邮箱")
                                , fieldWithPath("user.createdOn").ignored()
                                , fieldWithPath("user.enabled").ignored()
                                , fieldWithPath("user.authorities.[].authority").ignored()
                                , fieldWithPath("user.credentialsNonExpired").ignored()
                                , fieldWithPath("user.accountNonExpired").ignored()
                                , fieldWithPath("user.accountNonLocked").ignored()
                        )
                ));
    }

    @Test
    public void oauthLogout() throws Exception {
        String responseBody = passwordLogin("webclient").andReturn().getResponse().getContentAsString();
        String accessToken = accessToken(responseBody);
        String refreshToken = refreshToken(responseBody);

        //登录完是可以成功获取用户信息的
        this.mockMvc.perform(get("/user")
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk());

        //可以刷新access_token
        responseBody = refreshTokenLogin(refreshToken)
                .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString()
        ;
        accessToken = accessToken(responseBody);
        refreshToken = refreshToken(responseBody);

        //logout
        this.mockMvc.perform(post("/oauth/logout")
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isNoContent())
                .andDo(restDocument(requestHeaders(
                        headerWithName("Authorization").description("Bearer token to access protected resource")
                )))
        ;

        //不能获取用户信息
        this.mockMvc.perform(get("/user")
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnauthorized());

        //不能刷新用户的access token
        refreshTokenLogin(refreshToken)
                .andExpect(status().isBadRequest());

    }

    @Test
    public void refreshAccessToken2GetUserCredential() throws Exception {
        String responseBody = passwordLogin("webclient").andReturn().getResponse().getContentAsString();
        String accessToken = accessToken(responseBody);
        String refreshToken = refreshToken(responseBody);

        TimeUnit.SECONDS.sleep(ACCESS_TOKEN_VALIDITY_SECONDS + 1);

        // invalid access_token test
        this.mockMvc.perform(get("/user")
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnauthorized());

        // obtain new access_token with valid refresh_token
        responseBody = refreshTokenLogin(refreshToken)
        .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String accessToken2 = accessToken(responseBody);
        String refreshToken2 = refreshToken(responseBody);
        assertEquals(refreshToken, refreshToken2);
        assertNotEquals(accessToken, accessToken2);

        // new valid access_token test
        this.mockMvc.perform(get("/user")
                .header("Authorization", "Bearer " + accessToken2)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk());


        TimeUnit.SECONDS.sleep(REFRESH_TOKEN_VALIDITY_SECONDS - (ACCESS_TOKEN_VALIDITY_SECONDS + 1) + 1);

        // refresh_token timeout test
        refreshTokenLogin(refreshToken2)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void multiClientLogin4SameUser() throws Exception {
        String webAccessToken = loginAndGetAccessToken("webclient");

        String mobileAccessToken = loginAndGetAccessToken("mobileclient");

        assertNotEquals(webAccessToken, mobileAccessToken);
    }


    @Test
    public void sameClientLogin4SameUser() throws Exception {
        String webAccessToken = loginAndGetAccessToken("webclient");
        String webAccessToken2 = loginAndGetAccessToken("webclient");

        assertEquals(webAccessToken, webAccessToken2);

        String mobileAccessToken = loginAndGetAccessToken("mobileclient");
        String mobileAccessToken2 = loginAndGetAccessToken("mobileclient");
        assertEquals(mobileAccessToken, mobileAccessToken2);

        assertNotEquals(webAccessToken, mobileAccessToken);
    }
}
