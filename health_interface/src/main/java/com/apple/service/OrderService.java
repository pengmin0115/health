package com.apple.service;

import com.apple.entity.Result;

import java.util.Map;

/**
 * @author pengmin
 * @date 2020/10/15 17:52
 */

public interface OrderService {
    /**
     * 添加预约数据;
     * @param map
     * @return
     */
    Result submitOrder(Map map);

    /**
     * 查询预约信息;
     * @param id
     * @return
     */
    Map findById(Integer id);
}
