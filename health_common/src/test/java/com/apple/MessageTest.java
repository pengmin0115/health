package com.apple;
import com.apple.utils.SMSUtils;
import org.junit.Test;
/**
 * @author pengmin
 * @date 2020/10/16 23:21
 */
public class MessageTest {
    //@Test
    public void test01() throws Exception {
        SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,"15136421024", "520520");
    }
}
