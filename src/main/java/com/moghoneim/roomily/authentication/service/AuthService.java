package com.moghoneim.roomily.authentication.service;

import com.moghoneim.roomily.authentication.dto.AuthRequest;
import com.moghoneim.roomily.authentication.dto.AuthResponse;
import com.moghoneim.roomily.authentication.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);

    AuthResponse authenticate(AuthRequest request) throws Exception;
}
