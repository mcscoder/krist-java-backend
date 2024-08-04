package com.krist.entity.order;

import com.krist.entity.common.BaseEntity;
import com.krist.entity.user.Shipping;
import com.krist.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class UserOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double discount;

    @Column(nullable = false)
    private Double deliveryCharge;

    @Column(nullable = false)
    private Boolean paid;

    // 1. Shipping
    @ManyToOne
    @JoinColumn(name = "shipping_id")
    private Shipping shipping;

    // 2. User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 3. Delivery status
    @ManyToOne
    @JoinColumn(name = "delivery_status_id")
    private DeliveryStatus deliveryStatus;

    // 4. Payment method
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;
}
