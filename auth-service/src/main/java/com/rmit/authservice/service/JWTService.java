package com.rmit.authservice.service;

import com.rmit.authservice.entity.UserCredential;
import com.rmit.authservice.repository.UserCredentialRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JWTService {
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private final UserCredentialRepository userCredentialRepository;

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }


    public String generateToken(String userEmail) {
        Map<String, Object> claims = new HashMap<>();
        UserCredential userCredential = userCredentialRepository.findByEmail(userEmail).orElseThrow();
        claims.put("username",userCredential.getUsername());
//        claims.put("role",userCredential.getRole());
//        claims.put("permission",userCredential.getPermission());
        return createToken(claims, userEmail);
    }

    private String createToken(Map<String, Object> claims, String userEmail) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 3000))
            .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
