package com.apple;

import com.apple.utils.MD5Utils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author pengmin
 * @date 2020/10/16 11:25
 */

public class MD5Test {

    //@Test
    public void test01(){
        //81dc9bdb52d04dc20036dbd8313ed055
        String code = MD5Utils.md5("1234");
        String code01 = MD5Utils.md5("1234");
        System.out.println(code01);
    }

    //@Test
    public void test02(){
        /**
         * $2a$10$lEp01Lh06ec6ImkmhWikkuaMWRQAwB8UBvo/zbH3IfRraIsISK.RK
         * $2a$10$/Emnv4JnpkaDZfEBlmIWYebl7lSFcr1jpjkzaN0grmOY55D49W9d2
         */
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String s = encoder.encode("abc");
        System.out.println(s);
        String s1 = encoder.encode("abc");
        System.out.println(s1);

        System.out.println(encoder.matches("abc","$2a$10$lEp01Lh06ec6ImkmhWikkuaMWRQAwB8UBvo/zbH3IfRraIsISK.RK"));
    }
}
