package com.apple.dao;

import com.apple.pojo.Order;
import com.apple.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author pengmin
 * @date 2020/10/13 22:57
 */

public interface OrderSettingDao {
    /**
     * 查询预约设置信息
     * @param queryMap
     * @return
     */
    List<OrderSetting> getOrderSetting(Map queryMap);

    /**
     * 添加预约设置数据
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 查询该日的预约设置数据是否存在;
     * @param orderDate
     * @return
     */
    long findExist(Date orderDate);

    /**
     * 修改预约设置数据;
     * @param orderSetting
     */
    void updateNumber(OrderSetting orderSetting);

    /**
     * 更新预约设置表已预约人数信息;(+1)
     * @param orderCondition
     */
    void updateAfterReservation(Order orderCondition);
}
