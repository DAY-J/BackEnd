package com.capstone.dayj.jwt.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.jwt")
@RequiredArgsConstructor
@Getter
public class JWTProperties {
    private final String secret;
    private final Long expiredAccess;
    private final Long expiredRefresh;
}
