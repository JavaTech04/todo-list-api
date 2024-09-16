package com.javatech.service.impl;

import com.javatech.exceptions.EntityNotFoundException;
import com.javatech.model.User;
import com.javatech.repository.UserRepository;
import com.javatech.service.SysAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysAdminServiceImpl implements SysAdminService {
    private final UserRepository userRepository;

    @Override
    public void deleteUser(long userId) {
        User user = getUser(userId);
        userRepository.delete(user);
    }

    @Override
    public User getUser(long userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s not found!", userId)));
    }
}
