package com.apple.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.apple.constant.MessageConstant;
import com.apple.entity.Result;
import com.apple.pojo.OrderSetting;
import com.apple.service.OrderSettingService;
import com.apple.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author pengmin
 * @date 2020/10/13 22:53
 */
@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 查询预约设置信息
     * @param date
     * @return
     */
    @RequestMapping(value = "/getOrderSetting",method = RequestMethod.GET)
    public Result getOrderSetting(String date){
        try {
            List<Map> list = orderSettingService.getOrderSetting(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> listStr = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettingList = new ArrayList<>();
            if(listStr != null && listStr.size()>0){
                for (String[] str : listStr) {
                    OrderSetting orderSetting = new OrderSetting();
                    orderSetting.setOrderDate(new Date(str[0]));
                    orderSetting.setNumber(Integer.parseInt(str[1]));
                    orderSettingList.add(orderSetting);
                }
                //3.调用service服务
                orderSettingService.batchOrderSetting(orderSettingList);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * 单个设置预约设置数据
     * @param orderSetting
     * @return
     */
    @RequestMapping(value = "/updateNumberByOrderDate",method = RequestMethod.POST)
    public Result updateNumberByOrderDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.updateNumberByOrderDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
