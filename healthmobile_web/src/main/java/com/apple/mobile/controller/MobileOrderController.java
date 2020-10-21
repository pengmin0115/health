package com.apple.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.apple.constant.MessageConstant;
import com.apple.constant.RedisMessageConstant;
import com.apple.entity.Result;
import com.apple.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;
/**
 * @author pengmin
 * @date 2020/10/15 17:46
 */
@RestController
@RequestMapping("/order")
public class MobileOrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 添加预约信息;
     * @param map
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Result submit(@RequestBody Map map) {
        /**
         * idCard: "123456789012345678"
         * name: "张三"
         * orderDate: "2020-10-16"
         * setmealId: "12"
         * sex: "1"
         * telephone: "16625100447"
         * validateCode: "1122"
         */
        String clientCode = (String) map.get("validateCode");
        String telephone = (String) map.get("telephone");
        //check the ValidateCode;
        String redisCode = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone);
        if (StringUtils.isEmpty(clientCode) || StringUtils.isEmpty(redisCode) || !clientCode.equals(redisCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //submit request
        return orderService.submitOrder(map);
    }

    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    public Result findById(Integer id){
        try {
            Map map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
