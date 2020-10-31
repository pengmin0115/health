package com.apple.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.apple.pojo.Permission;
import com.apple.pojo.Role;
import com.apple.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author pengmin
 * @date 2020/10/16 17:24
 */

@Component
public class UserSecurity implements UserDetailsService {
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.apple.pojo.User user = userService.findUserByUsername(username);
        // 用户不存在直接返回null;
        if (user == null) {
            return null;
        }
        // 若用户存在则进行授权
        String password = user.getPassword();
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                list.add(new SimpleGrantedAuthority(role.getKeyword()));
                Set<Permission> permissions = role.getPermissions();
                if (permissions != null && permissions.size() > 0) {
                    for (Permission permission : permissions) {
                        list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }
        return new User(username, password, list);
    }
}
