package com.apple.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.apple.constant.MessageConstant;
import com.apple.constant.RedisConstant;
import com.apple.entity.PageResult;
import com.apple.entity.QueryPageBean;
import com.apple.entity.Result;
import com.apple.pojo.Setmeal;
import com.apple.service.SetMealService;
import com.apple.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;
import java.util.List;
import java.util.UUID;
/**
 * @author pengmin
 * @date 2020/10/12 20:55
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetMealService setMealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 分页查询套餐
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = setMealService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(MultipartFile imgFile) {
        try {
            //获取原始文件名称
            String fileName = imgFile.getOriginalFilename();
            //重新为上传图片生成一个新的唯一的文件名称
            String suffix = fileName.substring(fileName.indexOf("."));
            String newFileName = UUID.randomUUID().toString() + suffix;
            System.out.println("新上传的文件名为:"+newFileName);
            //调用七牛云接口上传
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), newFileName);
            //返回结果（重新生成文件名称返回）
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, newFileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 增加套餐数据
     *
     * @param checkgroupIds
     * @param setmeal
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(Integer[] checkgroupIds, @RequestBody Setmeal setmeal) {
        try {
            setMealService.add(setmeal, checkgroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    /**
     * 根据套餐ID查询套餐信息;
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 根据套餐ID查询对应检查组信息
     *
     * @param setmealId
     * @return
     */
    @RequestMapping(value = "/findCheckGroupsIds", method = RequestMethod.GET)
    public List<Integer> findCheckGroupsIds(Integer setmealId) {
        try {
            return setMealService.findCheckGroupIds(setmealId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 编辑套餐
     *
     * @param checkGroupIds
     * @param setmeal
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result edit(Integer[] checkGroupIds, @RequestBody Setmeal setmeal) {
        try {
            setMealService.edit(setmeal, checkGroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
            return new Result(true, MessageConstant.UPDATE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPDATE_SETMEAL_FAIL);
        }
    }

    /**
     * 删除套餐
     *
     * @param setmealId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result delete(Integer setmealId) {
        try {
            setMealService.delete(setmealId);
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }
}

