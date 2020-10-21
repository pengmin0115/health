package com.apple.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.apple.constant.MessageConstant;
import com.apple.dao.MemberDao;
import com.apple.dao.OrderDao;
import com.apple.dao.OrderSettingDao;
import com.apple.entity.Result;
import com.apple.pojo.Member;
import com.apple.pojo.Order;
import com.apple.pojo.OrderSetting;
import com.apple.service.OrderService;
import com.apple.utils.DateUtils;
import com.apple.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.Map;
/**
 * @author pengmin
 * @date 2020/10/15 17:52
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    /**
     * 添加预约数据;
     * @param map
     * @return
     */
    @Override
    public Result submitOrder(Map map) {
        try {
            String orderDate = (String) map.get("orderDate");
            String telephone = (String) map.get("telephone");
            String name = (String) map.get("name");
            String idCard = (String) map.get("idCard");
            String sex = (String) map.get("sex");
            Integer setmealId = Integer.valueOf((String)map.get("setmealId"));
            // 先判断当前日期是否可以预约;
            // Date date = DateUtils.parseString2Date(orderDate);
            OrderSetting orderSetting = orderDao.findOrderSettingByOrderDate(orderDate);
            if (orderSetting==null){
                return new Result(false, MessageConstant.ORDERSETTING_FAIL_ORDERDATE);
            }
            // 判断当前日期人数是否约满;
            if (orderSetting.getReservations()>=orderSetting.getNumber()){
                return new Result(false,MessageConstant.ORDERSETTING_FAIL_OVERMAXNUM);
            }
            // 判断是否是会员
            Member member = memberDao.findMemberByTelephone(telephone);
            if(member == null){
                //不是会员,则进行注册;(member为null,需要重新创建对象)
                member= new Member();
                member.setName(name);
                member.setSex(sex);
                member.setIdCard(idCard);
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberDao.add(member);
            }
            // 判断是否重复预约,通过selectKey在映射中获取自增长ID
            //System.out.println(member.getId());]
            Order orderCondition = new Order();
            orderCondition.setMemberId(member.getId());
            orderCondition.setOrderDate(DateUtils.parseString2Date(orderDate));
            orderCondition.setSetmealId(setmealId);
            Order order = orderDao.findOrderByMemberCondition(orderCondition);
            if (order!=null){
                return new Result(false,MessageConstant.HAS_ORDERED,order);
            }
            //添加预约信息;并更新预约设置表信息;
            orderCondition.setOrderType(Order.ORDERTYPE_WEIXIN);
            orderCondition.setOrderStatus(Order.ORDERSTATUS_NO);
            orderDao.add(orderCondition);
            orderSettingDao.updateAfterReservation(orderCondition);
            if(false){
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
            }
            System.out.println("您已经成功预约传智健康体检，体检时间为"+orderDate);
            return new Result(true,MessageConstant.ORDER_SUCCESS,orderCondition);
        } catch (Exception e) {
            e.printStackTrace();
             return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @Override
    public Map findById(Integer id) {
        return orderDao.findById(id);
    }
}
