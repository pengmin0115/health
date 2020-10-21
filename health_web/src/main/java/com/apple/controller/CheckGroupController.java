package com.apple.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.apple.constant.MessageConstant;
import com.apple.entity.PageResult;
import com.apple.entity.QueryPageBean;
import com.apple.entity.Result;
import com.apple.pojo.CheckGroup;
import com.apple.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * @author pengmin
 * @date 2020/10/10 10:05
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping(value = ("/findPage"), method = RequestMethod.POST)
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = checkGroupService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增检查组信息
     *
     * @param checkItemIds
     * @param checkGroup
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(Integer[] checkItemIds, @RequestBody CheckGroup checkGroup) {
        try {
            checkGroupService.add(checkItemIds, checkGroup);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询所有检查组
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Result findAll() {
        try {
            List<CheckGroup> checkGroupList = checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     *  根据ID查询检查组信息
     * @param checkGroupId
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    public Result findById(Integer checkGroupId){
        try {
            CheckGroup checkGroup = checkGroupService.findById(checkGroupId);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 修改检查组信息;
     * @param checkGroup
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    // Json参数的参数名不用对应;
    public Result edit(@RequestBody CheckGroup checkGroup){
        try {
            checkGroupService.edit(checkGroup);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping(value = "/deleteById",method = RequestMethod.GET)
    public Result deleteById(Integer checkGroupId){
        try {
            checkGroupService.deleteById(checkGroupId);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }catch (RuntimeException e){
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }
}
