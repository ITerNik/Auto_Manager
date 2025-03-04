package org.example.automanager.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${token.signing.key}")
    private static String jwtSigningKey;

    @Value("${token.expire}")
    private static int jwtExpirationMs;

    public static String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public static int getJwtExpirationMs() {
        return jwtExpirationMs;
    }
}
