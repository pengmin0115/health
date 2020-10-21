package com.apple.dao;

import com.apple.pojo.Role;

import java.util.Set;

/**
 * @author pengmin
 * @date 2020/10/16 17:47
 */

public interface RoleDao {
    /**
     * 根据用户ID查询角色列表;
     * @param userId
     * @return
     */
    Set<Role> findRolesByUserId(Integer userId);
}
