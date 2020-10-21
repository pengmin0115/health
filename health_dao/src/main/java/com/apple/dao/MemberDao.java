package com.apple.dao;
import com.apple.pojo.Member;

import java.util.Map;

/**
 * @author pengmin
 * @date 2020/10/15 20:16
 */

public interface MemberDao {

    /**
     * 根据手机号查找会员;
     * @param telephone
     * @return
     */
    Member findMemberByTelephone(String telephone);

    /**
     * 增加会员;
     * @param member
     */
    void add(Member member);

    /**
     * 查询截至指定日期之前的会员人数;
     * @param month
     * @return
     */
    Integer CountMemberBeforeDate(String month);

    /**
     * 统计当前日期新增的会员数;
     * @param reportDate
     * @return
     */
    Long countNewMemberByDate(String reportDate);

    /**
     * 统计会员总数
     * @return
     */
    Long countTotalMember();

    /**
     * 统计本周新增会员数量;
     * @param queryMap
     * @return
     */
    Long countThisWeekNewMember(Map queryMap);

    /**
     * 统计本月新增会员数量;
     * @param queryMap
     * @return
     */
    Long countThisMonthNewMember(Object queryMap);
}
