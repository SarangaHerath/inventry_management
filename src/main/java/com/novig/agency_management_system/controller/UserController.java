package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.RequestUserDto;
import com.novig.agency_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("**")
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RequestUserDto userDto) {
        return userService.login(userDto);
    }
}
