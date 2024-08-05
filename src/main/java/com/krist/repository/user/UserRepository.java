package com.krist.repository.user;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.krist.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
