package org.example.automanager.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    @Value("${token.expire}")
    private int jwtExpirationMs;

    public  String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public int getJwtExpirationMs() {
        return jwtExpirationMs;
    }
}
