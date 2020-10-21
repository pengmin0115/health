package com.apple.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.apple.dao.UserDao;
import com.apple.pojo.User;
import com.apple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author pengmin
 * @date 2020/10/16 17:37
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    /**
     * 根据用户名获取用户对象信息;
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
}
