package com.krist.dto.user;

import com.krist.dto.common.ImageDto;

public record UserOverview(String firstName, String lastName, String email, ImageDto image) {
}
