package com.krist.dto.user;

public record DeliveryAddressDto(String name, String phoneNumber, String code, String city,
        String district, String ward, String street, String description) {
}
