package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.RequestUserDto;
import org.springframework.http.ResponseEntity;


public interface UserService {
    ResponseEntity<String> login(RequestUserDto userDto);
}
