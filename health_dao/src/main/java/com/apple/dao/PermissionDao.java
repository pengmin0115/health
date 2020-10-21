package com.apple.dao;

import com.apple.pojo.Permission;

import java.util.Set;

/**
 * @author pengmin
 * @date 2020/10/16 17:48
 */

public interface PermissionDao {
    /**
     * 根据角色Id查询权限列表;
     * @param roleId
     * @return
     */
    Set<Permission> findPermissionByRoleId(Integer roleId);
}
