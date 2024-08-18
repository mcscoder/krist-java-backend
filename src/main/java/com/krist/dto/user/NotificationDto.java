package com.krist.dto.user;

import java.time.LocalDateTime;

public record NotificationDto(String title, String description, LocalDateTime createAt) {
}
