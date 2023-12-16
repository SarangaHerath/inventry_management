package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.RequestUserDto;
import com.novig.agency_management_system.entity.User;
import com.novig.agency_management_system.repository.UserRepo;
import com.novig.agency_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public ResponseEntity<String> login(RequestUserDto userDto) {
        Optional<User> user = userRepo.findByUserName(userDto.getUserName());

        if (user != null && user.get().getPassword().equals(userDto.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }
}
