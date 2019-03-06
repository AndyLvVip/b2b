package uca.auth.client.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import uca.auth.client.config.AppConfiguration;
import uca.auth.client.exception.InternalServerException;
import uca.auth.client.vo.OAuth2TokenVo;

@Service
public class SecurityService {

    private RestTemplate restTemplate;
    private AppConfiguration.AuthClient authClient;
    private RedisTemplate<String, String> redisTemplate;


    public SecurityService(RestTemplate restTemplate
            , AppConfiguration.AuthClient authClient
                           , RedisTemplate<String, String> redisTemplate
    ) {
        this.restTemplate = restTemplate;
        this.authClient = authClient;
        this.redisTemplate = redisTemplate;
    }

    public OAuth2TokenVo login(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(authClient.getId(), authClient.getSecret());
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("scope", authClient.getScope());
        map.add("grant_type", "password");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<OAuth2TokenVo> response = restTemplate.exchange("http://gwserver/authserver/oauth/token"
                , HttpMethod.POST
                , entity
                , OAuth2TokenVo.class
        );
        if(!response.getStatusCode().is2xxSuccessful())
            throw new InternalServerException("exception response status code: " + response.getStatusCode() + ", " + response.getBody());

        OAuth2TokenVo oathToken = response.getBody();

        redisTemplate.opsForValue().set(authClient.getId() + "." + oathToken.getRefresh_token(), oathToken.getAccess_token());

        oathToken.setRefresh_token(null);
        return oathToken;
    }
}
