package com.example.bidwar.services;

import com.example.bidwar.dtos.AuthDto;

public interface AuthService {
    //registering user
    void register(AuthDto dto);
}
