package com.javatech.service;

import com.javatech.model.User;

public interface SysAdminService {

    void deleteUser(long userId);

    User getUser(long userId);
}
