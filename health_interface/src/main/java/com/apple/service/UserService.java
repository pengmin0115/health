package com.apple.service;

import com.apple.pojo.User;

/**
 * @author pengmin
 * @date 2020/10/16 17:37
 */

public interface UserService {
    /**
     * 根据用户名获取用户对象;
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
