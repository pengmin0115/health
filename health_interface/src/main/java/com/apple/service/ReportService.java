package com.apple.service;
import java.util.Map;
/**
 * @author pengmin
 * @date 2020/10/18 17:02
 */
public interface ReportService {
    /**
     * 获取会员注册信息;
     * @return
     */
    public Map getMemberReport();

    /**
     * 获取套餐预约报表数据;
     * @return
     */
    Map getSetmealReport();

    /**
     * 获取运营统计数据
     * @return
     * @throws Exception
     */
    Map getBusinessReportData() throws Exception;
}
