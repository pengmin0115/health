package com.apple.dao;

import com.apple.pojo.User;

/**
 * @author pengmin
 * @date 2020/10/16 17:43
 * 用户持久层接口
 */

public interface UserDao {
    /**
     * 根据用户名获取用户对象信息
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
