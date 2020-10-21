package com.apple.service;

import com.apple.entity.PageResult;
import com.apple.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author pengmin
 * @date 2020/10/12 20:59
 */

public interface SetMealService {
    /**
     * 分页查询套餐
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 新增套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 根据套餐ID查询套餐信息
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 根据套餐ID查询对应检查组信息
     * @param setmealId
     * @return
     */
    List<Integer> findCheckGroupIds(Integer setmealId);

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkGroupIds
     */
    void edit(Setmeal setmeal, Integer[] checkGroupIds);

    /**
     * 删除套餐
     * @param setmealId
     */
    void delete(Integer setmealId);

    /**
     * 获取套餐列表
     * @return
     */
    List<Setmeal> getSetmeal();
}
