package com.plant.managment.security;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record RsaConfig(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
}
