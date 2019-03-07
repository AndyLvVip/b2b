package uca.auth.client.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import uca.auth.client.config.Config;
import uca.auth.client.exception.AuthClientException;
import uca.auth.client.exception.InternalServerException;
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(this.config.getClient().getId(), this.config.getClient().getSecret());
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("scope", scope.getName());
        map.add("grant_type", "password");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<OAuth2TokenVo> response = restTemplate.exchange(this.config.getClient().getTokenUrl()
                , HttpMethod.POST
                , entity
                , OAuth2TokenVo.class
        );
        if(!response.getStatusCode().is2xxSuccessful())
            throw new InternalServerException("exception response status code: " + response.getStatusCode() + ", " + response.getBody());

        OAuth2TokenVo oathToken = response.getBody();

        stringRedisTemplate.opsForValue().set(oathToken.getAccess_token(), oathToken.getRefresh_token(), scope.getTimeout(), TimeUnit.SECONDS);

        oathToken.setRefresh_token(null);
        return oathToken;
    }

    public OAuth2TokenVo refreshToken(Config.Client.Scope scope, String access_token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(this.config.getClient().getId(), this.config.getClient().getSecret());
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String refresh_token = stringRedisTemplate.opsForValue().get(access_token);
        if(StringUtils.isEmpty(refresh_token))
            throw new AuthClientException("invalid access_token");

        map.add("refresh_token", refresh_token);
        map.add("grant_type", "refresh_token");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<OAuth2TokenVo> response = restTemplate.exchange(this.config.getClient().getTokenUrl()
                , HttpMethod.POST
                , entity
                , OAuth2TokenVo.class
        );
        if(!response.getStatusCode().is2xxSuccessful())
            throw new InternalServerException("exception response status code: " + response.getStatusCode() + ", " + response.getBody());

        OAuth2TokenVo oathToken = response.getBody();

        stringRedisTemplate.opsForValue().set(oathToken.getAccess_token(), oathToken.getRefresh_token(), scope.getTimeout(), TimeUnit.SECONDS);

        oathToken.setRefresh_token(null);
        return oathToken;
    }
}
