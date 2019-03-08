package uca.security.auth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty
    private String username;
    @Column
    private String name;
    @Column
    @NotEmpty
    private String password;
    @Column
    private String email;
    @Column
    @NotEmpty
    private String phone;
    @Column
    private LocalDateTime createdOn;

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
