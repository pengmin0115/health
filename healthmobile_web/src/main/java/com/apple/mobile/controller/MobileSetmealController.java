package com.apple.mobile.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.apple.constant.MessageConstant;
import com.apple.entity.Result;
import com.apple.pojo.Setmeal;
import com.apple.service.SetMealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * @author pengmin
 * @date 2020/10/14 17:12
 */
@RestController
@RequestMapping("/mobileSetmeal")
public class MobileSetmealController {
    @Reference
    private SetMealService setMealService;

    /**
     * 获取套餐列表
     * @return
     */
    @RequestMapping(value = "getSetmeal",method = RequestMethod.GET)
    public Result getSetmeal(){
        try {
            List<Setmeal> list = setMealService.getSetmeal();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }
}
