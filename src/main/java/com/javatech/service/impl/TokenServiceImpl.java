package com.javatech.service.impl;

import com.javatech.exceptions.EntityNotFoundException;
import com.javatech.model.redis_model.Token;
import com.javatech.repository.TokenRepository;
import com.javatech.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public void deleteToken(String id) {
        Token token = getToken(id);
        this.tokenRepository.delete(token);
    }

    @Override
    public void saveToken(Token token) {
        this.tokenRepository.save(token);
    }

    @Override
    public Token getToken(String id) {
        return this.tokenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Something went wrong!"));
    }
}
