package uca.auth.client.vo;

import lombok.Data;

@Data
public class OAuth2TokenVo {

    private String access_token;

    private String refresh_token;

    private String token_type;

    private Integer expires_in;

    private String scope;

}
