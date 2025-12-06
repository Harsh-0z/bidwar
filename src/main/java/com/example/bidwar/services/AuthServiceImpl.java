package com.example.bidwar.services;

import com.example.bidwar.dtos.AuthDto;
import com.example.bidwar.entities.UserEntity;
import com.example.bidwar.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;

    @Override
    public void register(AuthDto dto){
        // 1 checking the user is already exsists or not
        if(userRepo.findByUsername(dto.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }
        // 2. SECURITY FIX: Force Role to USER
        // Even if they send {"role": "ADMIN"}, we ignore it and set ROLE_USER.
        String assignedRole = "R0LE_USER";

        //create user

        UserEntity newUser = UserEntity.builder()
                .username(dto.getUsername())
                .password("{noop}"+dto.getPassword())
                .role(assignedRole)
                .build();

        userRepo.save(newUser);
    }


}
