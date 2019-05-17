package uca.base.user;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Andy Lv on 2019/5/16
 */
@Data
public class StdSimpleUser {

    private String id;

    @NotEmpty(message = "username can not be empty")
    private String username;

    @NotEmpty(message = "password can not be empty")
    private String password;

    private String name;

    @NotEmpty(message = "phone can not be empty")
    private String phone;

    private String email;

    public StdSimpleUser validateId() {
        if(StringUtils.isEmpty(getId())) {
            throw new IllegalArgumentException("id can not be empty");
        }
        return this;
    }
}
