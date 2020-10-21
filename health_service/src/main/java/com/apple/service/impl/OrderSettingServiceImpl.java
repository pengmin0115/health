package com.apple.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.apple.dao.OrderSettingDao;
import com.apple.pojo.OrderSetting;
import com.apple.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengmin
 * @date 2020/10/13 22:56
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 查询预约设置信息
     *
     * @param date
     * @return
     */
    @Override
    public List<Map> getOrderSetting(String date) {
        String beginDate = date + "-01";
        String endDate = date + "-31";
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("beginDate", beginDate);
        queryMap.put("endDate", endDate);
        List<OrderSetting> orderSettingList = orderSettingDao.getOrderSetting(queryMap);
        List<Map> resList = new ArrayList<>();
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                Map map = new HashMap();
                map.put("date", orderSetting.getOrderDate().getDate());
                map.put("number", orderSetting.getNumber());
                map.put("reservations", orderSetting.getReservations());
                resList.add(map);
            }
        }
        return resList;
    }

    /**
     * 批量添加预约设置数据;
     *
     * @param orderSettingList
     */
    @Override
    public void batchOrderSetting(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                long count = orderSettingDao.findExist(orderSetting.getOrderDate());
                if (count > 0) {
                    orderSettingDao.updateNumber(orderSetting);
                } else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    /**
     * 单个设置预约设置数据
     *
     * @param orderSetting
     */
    @Override
    public void updateNumberByOrderDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findExist(orderSetting.getOrderDate());
        if (count > 0) {
            orderSettingDao.updateNumber(orderSetting);
        } else {
            orderSettingDao.add(orderSetting);
        }
    }
}
