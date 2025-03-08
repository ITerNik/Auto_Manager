package org.example.automanager.dto.auth;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class ClientInfoResponse {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private LocalDateTime birthday;
}
