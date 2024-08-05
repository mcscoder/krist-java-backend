package com.krist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.krist.dto.user.PersonalInformationDto;
import com.krist.entity.user.User;


@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/profile")
    public ResponseEntity<PersonalInformationDto> profile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PersonalInformationDto personalInformationDto = new PersonalInformationDto(
                user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail());

        return ResponseEntity.ok().body(personalInformationDto);
    }

}
