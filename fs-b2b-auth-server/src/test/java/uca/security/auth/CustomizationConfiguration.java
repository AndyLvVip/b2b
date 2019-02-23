package uca.security.auth;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

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
}
