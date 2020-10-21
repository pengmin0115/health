package com.apple.dao;

import com.apple.pojo.Order;
import com.apple.pojo.OrderSetting;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author pengmin
 * @date 2020/10/15 19:27
 */

public interface OrderDao {
    /**
     * 根据预约日期获取预约设置对象数据;
     * @param date
     * @return
     */
    OrderSetting findOrderSettingByOrderDate(String date);

    /**
     * 通过会员信息获取预约信息;
     * @param orderCondition
     * @return
     */
    Order findOrderByMemberCondition(Order orderCondition);

    /**
     * 新增预约信息;
     * @param orderCondition
     */
    void add(Order orderCondition);

    /**
     * 查询预约信息;
     * @param id
     * @return
     */
    Map findById(Integer id);

    /**
     * 获取预约报表数据;
     * @return
     */
    List<Map> getSetmealReport();

    /**
     * 统计当前日期预约体检人数;
     * @param reportDate
     * @return
     */
    Long countTodayOrderNumber(String reportDate);

    /**
     * 统计当前日期到诊体检人数;
     * @param reportDate
     * @return
     */
    Long countTodayVisitsNumber(String reportDate);

    /**
     * 统计当前周预约体检人数;
     * @param queryMap
     * @return
     */
    Long countOrderNumberDuringWeek(Map queryMap);

    /**
     * 统计本周到诊人数
     * @param queryMap
     * @return
     */
    Long countVisitNumberDuringWeek(Map queryMap);

    /**
     * 统计当前月预约体检人数;
     * @param queryMap
     * @return
     */
    Long countOrderNumberDuringMonth(Map queryMap);

    /**
     * 统计本月到诊人数
     * @param queryMap
     * @return
     */
    Long countVisitNumberDuringMonth(Map queryMap);
}
