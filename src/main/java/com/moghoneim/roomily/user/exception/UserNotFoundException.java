package com.moghoneim.roomily.user.exception;

import com.moghoneim.roomily.exception.ErrorResponse;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

