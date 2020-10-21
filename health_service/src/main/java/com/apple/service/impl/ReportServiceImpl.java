package com.apple.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.apple.dao.MemberDao;
import com.apple.dao.OrderDao;
import com.apple.dao.SetMealDao;
import com.apple.service.ReportService;
import com.apple.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author pengmin
 * @date 2020/10/18 17:02
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SetMealDao setMealDao;

    /**
     * 获取会员注册信息
     *
     * @return
     */
    @Override
    public Map getMemberReport() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            months.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            month = month + "-31";
            Integer count = memberDao.CountMemberBeforeDate(month);
            memberCount.add(count);
        }
        Map map = new HashMap();
        map.put("months", months);
        map.put("memberCount", memberCount);
        return map;
    }

    @Override
    public Map getSetmealReport() {
        Map map = new HashMap();
        List<Map> setmealCount = orderDao.getSetmealReport();
        List<String> setmealNames = new ArrayList<>();
        for (Map namesMap : setmealCount) {
            setmealNames.add((String) namesMap.get("name"));
        }
        map.put("setmealNames", setmealNames);
        map.put("setmealCount", setmealCount);
        return map;
    }

    /**
     * 获取运营统计数据
     *
     * @return
     */
    @Override
    public Map getBusinessReportData() throws Exception {
        Map map = new HashMap();
        Map queryMap = new HashMap();
        String reportDate = DateUtils.parseDate2String(new Date());
        Date monday = DateUtils.getThisWeekMonday();
        Date sunday = DateUtils.getSundayOfThisWeek();
        Date month_1st_day = DateUtils.getFirstDay4ThisMonth();
        Date month_last_day = DateUtils.getLastDay4ThisMonth();
        queryMap.put("monday", monday);
        queryMap.put("sunday", sunday);
        queryMap.put("month_1st_day", month_1st_day);
        queryMap.put("month_last_day", month_last_day);
        Long todayNewMember = memberDao.countNewMemberByDate(reportDate);
        Long totalMember = memberDao.countTotalMember();
        Long thisWeekNewMember = memberDao.countThisWeekNewMember(queryMap);
        Long thisMonthNewMember = memberDao.countThisMonthNewMember(queryMap);
        Long todayOrderNumber = orderDao.countTodayOrderNumber(reportDate);
        Long todayVisitsNumber = orderDao.countTodayVisitsNumber(reportDate);
        Long thisWeekOrderNumber = orderDao.countOrderNumberDuringWeek(queryMap);
        Long thisWeekVisitsNumber = orderDao.countVisitNumberDuringWeek(queryMap);
        Long thisMonthOrderNumber = orderDao.countOrderNumberDuringMonth(queryMap);
        Long thisMonthVisitsNumber = orderDao.countVisitNumberDuringMonth(queryMap);
        List<Map> hotSetmeal = setMealDao.getHotSetmeal();
        // return data
        map.put("reportDate", reportDate);
        map.put("todayNewMember", todayNewMember);
        map.put("totalMember", totalMember);
        map.put("thisWeekNewMember", thisWeekNewMember);
        map.put("thisMonthNewMember", thisMonthNewMember);
        map.put("todayOrderNumber", todayOrderNumber);
        map.put("todayVisitsNumber", todayVisitsNumber);
        map.put("thisWeekOrderNumber", thisWeekOrderNumber);
        map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        map.put("thisMonthOrderNumber", thisMonthOrderNumber);
        map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        map.put("hotSetmeal", hotSetmeal);
        return map;
    }
}
