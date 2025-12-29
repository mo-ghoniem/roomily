package com.moghoneim.roomily.exception;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;

}
