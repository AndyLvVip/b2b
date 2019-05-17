package uca.security.auth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uca.base.user.StdSimpleUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Column
    @Id
    private String id;
    @Column
    private String username;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private LocalDateTime createdOn;

    public static User newInstance(StdSimpleUser vo) {
        User user = new User();
        user.setId(vo.getId());
        user.setPhone(vo.getPhone());
        user.setEmail(vo.getEmail());
        user.setName(vo.getName());
        user.setUsername(vo.getUsername());
        user.setPassword(vo.getPassword());
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(() -> "user");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
