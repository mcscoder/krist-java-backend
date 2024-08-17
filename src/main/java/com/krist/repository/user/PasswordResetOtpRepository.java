package com.krist.repository.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.krist.entity.user.PasswordResetOtp;
import com.krist.entity.user.User;

public interface PasswordResetOtpRepository extends CrudRepository<PasswordResetOtp, Long> {

    // Docs reference:
    // https://docs.spring.io/spring-data/jpa/reference/repositories/core-concepts.html
    Optional<PasswordResetOtp> findByUser(User user);

    // Docs reference:
    // https://docs.spring.io/spring-data/jpa/reference/repositories/query-methods-details.html#repositories.query-methods.query-creation
    Optional<PasswordResetOtp> findByUserAndOtp(User user, Integer otp);

}
