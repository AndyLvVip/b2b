package uca.corporate.fs;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import uca.base.constant.CorporateType;
import uca.base.fs.user.StdCorporate;
import uca.base.fs.user.StdUser;
import uca.base.user.StdPermission;
import uca.base.user.StdSimpleUser;
import uca.corporate.fs.feign.AuthServerFeignClient;
import uca.corporate.fs.feign.PlatformSysFeignClient;
import uca.platform.StdStringUtils;

import java.util.*;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static uca.base.constant.Constants.USER;

/**
 * Created by andy.lv
 * on: 2019/1/19 16:18
 */
@TestConfiguration
public class CustomizationConfiguration implements RestDocsMockMvcConfigurationCustomizer {

    @Override
    public void customize(MockMvcRestDocumentationConfigurer configurer) {
        configurer.operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint());
    }

    public static RestDocumentationResultHandler restDocument(Snippet... snippets) {
        return MockMvcRestDocumentation.document("{method-name}", snippets);
    }

    @Bean
    @Primary
    TokenStore inMemoryTokenStore() {
        return new InMemoryTokenStore();
    }


    public static class MockPlatformSysFeignClient implements PlatformSysFeignClient {

        @Override
        public List<StdPermission> fetchUserAllPermission() {
            return Collections.EMPTY_LIST;
        }

        @Override
        public void linkUserRole(String userId, String roleId) {

        }
    }

    @Bean
    @Primary
    PlatformSysFeignClient platformSysFeignClient() {
        return new MockPlatformSysFeignClient();
    }

    public static class MockAuthServerFeignClient implements AuthServerFeignClient {

        @Override
        public void register(StdSimpleUser stdSimpleUser) {

        }
    }

    @Bean
    @Primary
    AuthServerFeignClient authServerFeignClient() {
        return new MockAuthServerFeignClient();
    }


    public static StdUser andy() {
        StdUser stdUser = new StdUser();
        StdSimpleUser stdSimpleUser = new StdSimpleUser();
        StdCorporate stdCorporate = new StdCorporate();
        List<StdPermission> permissions = new ArrayList<>();
        stdUser.setStdSimpleUser(stdSimpleUser);
        stdUser.setStdCorporate(stdCorporate);
        stdUser.setStdPermissionList(permissions);
        String userId = StdStringUtils.uuid();
        String corporateId = StdStringUtils.uuid();

        stdSimpleUser.setId(userId);
        stdSimpleUser.setUsername("andy");
        stdSimpleUser.setName("Andy Lv");
        stdSimpleUser.setPhone("13800138000");
        stdSimpleUser.setEmail("andy.lv@ucacc.com");

        stdCorporate.setId(corporateId);
        stdCorporate.setName("众陶联");
        stdCorporate.setType(CorporateType.COMPANY.val);

        return stdUser;
    }

    public static ResponseEntity<Map<String, StdSimpleUser>> responseEntity(StdSimpleUser user) {
        Map<String, StdSimpleUser> userMap = new HashMap<>();
        userMap.put(USER, user);
        return new ResponseEntity<>(userMap, HttpStatus.OK);
    }
}
