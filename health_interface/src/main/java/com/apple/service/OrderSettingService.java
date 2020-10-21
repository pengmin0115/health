package com.apple.service;

import com.apple.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author pengmin
 * @date 2020/10/13 22:56
 */

public interface OrderSettingService {
    /**
     * 查询预约设置信息
     * @param date
     * @return
     */
    List<Map> getOrderSetting(String date);

    /**
     * 批量存储预约设置数据
     * @param orderSettingList
     */
    void batchOrderSetting(List<OrderSetting> orderSettingList);

    /**
     * 单个设置预约设置数据;
     * @param orderSetting
     */
    void updateNumberByOrderDate(OrderSetting orderSetting);
}
