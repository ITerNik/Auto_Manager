package org.example.automanager.security.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtConfig {
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    @Value("${token.expire}")
    private int jwtExpirationMs;
}
