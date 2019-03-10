package uca.security.auth.repository;

import org.springframework.data.repository.CrudRepository;
import uca.security.auth.domain.User;

public interface UserRepository extends CrudRepository<User, String> {

    User findByUsernameOrPhoneOrEmail(String username, String phone, String email);
}
