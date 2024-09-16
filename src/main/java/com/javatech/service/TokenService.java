package com.javatech.service;

import com.javatech.model.redis_model.Token;

public interface TokenService {

    void deleteToken(String id);

    void saveToken(Token token);

    Token getToken(String id);
}
