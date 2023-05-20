package com.rmit.authservice.service;

import com.rmit.authservice.entity.RefreshToken;
import com.rmit.authservice.repository.RefreshTokenRepository;
import com.rmit.authservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserCredentialRepository userCredentialRepository;

    public RefreshToken createRefreshToken(String userEmail){
        RefreshToken refreshToken =  RefreshToken.builder()
                .userId(userCredentialRepository.findByEmail(userEmail).orElseThrow().getId())
                .token(UUID.randomUUID().toString())
                .expiredDate(LocalDateTime.now().plusMinutes(60))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiredDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
