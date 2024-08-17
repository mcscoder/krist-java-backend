package com.krist.entity.user;

import java.util.Date;

import com.krist.entity.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class PasswordResetOtp extends BaseEntity {
    public static final Long EXPIRATION = 300000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer otp;

    @Column(nullable = false)
    private Date expiryDate;

    // 1. User
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PasswordResetOtp(Integer otp, Date expiryDate, User user) {
        this.otp = otp;
        this.expiryDate = expiryDate;
        this.user = user;
    }
}
