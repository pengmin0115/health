package com.apple;

import com.apple.utils.DateUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pengmin
 * @date 2020/10/15 19:52
 */

public class DateUtilsTest {
    //@Test
    public void test01() throws Exception {
        //  配置的format格式为字符串形式的日期格式; '2020-10-16';
        // date格式为: Fri Oct 16 09:39:37 CST 2020; 不可改变;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(simpleDateFormat.format(new Date()));
        System.out.println(new Date());
    }

}
