package uca.security.auth.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserReqVo {
    @NotEmpty
    private String username;
    private String name;
    @NotEmpty
    private String password;
    private String email;
    @NotEmpty
    private String phone;
}
