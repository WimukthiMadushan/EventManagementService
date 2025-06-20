package com.eventmanagemnt.eventmanagementservice.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Map;


public class JwtUtil {
    private final JwtParser parser;

    public JwtUtil(SecretKey secretKey) {
        this.parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
    }

    public Claims extractClaims(String token) {
        return parser.parseClaimsJws(token).getBody();
    }
}
