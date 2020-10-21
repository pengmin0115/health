package com.apple;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 * @author pengmin
 * @date 2020/10/18 10:12
 */
public class MonthTest {
    //@Test
    public void test0(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);//获得当前日期之前12个月的日期
        System.out.println(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        List<String> list = new ArrayList<String>();
        for(int i=0;i<12;i++){
            calendar.add(Calendar.MONTH,1);
            String month= new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
            System.out.println(month);
            list.add(month);
        }
    }

    //@Test
    public void test02(){
        //测试value是否为map中保留字段
    }
}
