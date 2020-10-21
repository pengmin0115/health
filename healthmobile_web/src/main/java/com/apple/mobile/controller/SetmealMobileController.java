package com.apple.mobile.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.apple.constant.MessageConstant;
import com.apple.entity.Result;
import com.apple.pojo.Setmeal;
import com.apple.service.SetMealService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author pengmin
 * @date 2020/10/15 16:52
 */
@RestController
@RequestMapping("/setmealMobile")
public class SetmealMobileController {
    @Reference
    private SetMealService setMealService;
    @PostMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
