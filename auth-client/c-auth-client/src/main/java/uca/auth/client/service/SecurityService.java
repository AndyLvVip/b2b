package uca.auth.client.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import uca.auth.client.config.Config;
import uca.auth.client.exception.InternalServerException;
import uca.auth.client.exception.InvalidAccessTokenException;
import uca.auth.client.exception.InvalidUsernamePasswordException;
import uca.auth.client.vo.OAuth2TokenVo;

import java.util.concurrent.TimeUnit;

@Service
public class SecurityService {

    private RestTemplate restTemplate;
    private Config config;
    private StringRedisTemplate stringRedisTemplate;


    public SecurityService(RestTemplate restTemplate
            , Config config
            , StringRedisTemplate stringRedisTemplate
    ) {

        this.restTemplate = restTemplate;
        this.config = config;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public OAuth2TokenVo login(Config.Client.Scope scope, String username, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("scope", scope.getName());
        map.add("grant_type", "password");
        return authenticated(scope, map, null);
    }

    public OAuth2TokenVo refreshToken(Config.Client.Scope scope, String accessToken) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String refreshToken = stringRedisTemplate.opsForValue().get(accessToken);
        if(StringUtils.isEmpty(refreshToken)) {
            throw new InvalidAccessTokenException("invalid accessToken: " + accessToken);
        }

        map.add("refresh_token", refreshToken);
        map.add("grant_type", "refresh_token");
        OAuth2TokenVo oAuth2TokenVo = authenticated(scope, map, accessToken);
        this.stringRedisTemplate.expire(accessToken, 0, TimeUnit.SECONDS); //accessToken can be used to refresh only once.
        return oAuth2TokenVo;
    }

    public OAuth2TokenVo authenticated(Config.Client.Scope scope, MultiValueMap<String, String> params, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(this.config.getClient().getId(), this.config.getClient().getSecret());
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<OAuth2TokenVo> response = restTemplate.exchange(this.config.getClient().getTokenUrl()
                , HttpMethod.POST
                , entity
                , OAuth2TokenVo.class
        );
        if(response.getStatusCode().value() == HttpStatus.BAD_REQUEST.value()) {
            if(StringUtils.isNotEmpty(accessToken)) {
                throw new InvalidAccessTokenException("invalid accessToken: " + accessToken);
            } else {
                throw new InvalidUsernamePasswordException("invalid username password");
            }
        } else if(!response.getStatusCode().is2xxSuccessful()) {
            throw new InternalServerException("exception response status code: " + response.getStatusCode() + ", " + response.getBody());
        }

        OAuth2TokenVo oathToken = response.getBody();

        stringRedisTemplate.opsForValue().set(oathToken.getAccessToken(), oathToken.getRefreshToken(), scope.getTimeout(), TimeUnit.SECONDS);

        oathToken.setRefreshToken(null);
        return oathToken;
    }

}
