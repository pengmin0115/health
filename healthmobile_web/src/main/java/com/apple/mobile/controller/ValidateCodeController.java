package com.apple.mobile.controller;

import com.aliyuncs.exceptions.ClientException;
import com.apple.constant.MessageConstant;
import com.apple.constant.RedisMessageConstant;
import com.apple.entity.Result;
import com.apple.utils.SMSUtils;
import com.apple.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author pengmin
 * @date 2020/10/15 17:11
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping(value = "/send4Order", method = RequestMethod.POST)
    public Result send4Order(String telephone) {
        try {
            String code = ValidateCodeUtils.generateValidateCode4String(6);
            System.out.println("发送的验证码为:" + code);
            if (false) {
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code);
            }
            jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone, 60 * 5, code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
