package com.apple.dao;
import com.apple.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;
/**
 * @author pengmin
 * @date 2020/10/12 21:02
 */
public interface SetMealDao {
    /**
     * 分页查询套餐
     * @param queryString
     * @return
     */
    Page<Setmeal> findPage(String queryString);

    /**
     * 添加套餐信息
     * @param setmeal
     */
    void addSetmeal(Setmeal setmeal);

    /**
     * 添加套餐和检查组中间表数据
     * @param map
     */
    void addSetmealAndCheckgroup(Map map);

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
     * 修改套餐信息
     * @param setmeal
     */
    void updateSetmeal(Setmeal setmeal);

    /**
     * 清除对应套餐的检查组信息
     * @param id
     */
    void clearSetmealAndCheckgroup(Integer id);

    /**
     * 统计套餐对应检查组的数量
     * @param setmealId
     * @return
     */
    Long countCheckGroup(Integer setmealId);

    /**
     * 删除套餐数据
     * @param setmealId
     */
    void delete(Integer setmealId);

    /**
     * 获取套餐列表
     * @return
     */
    List<Setmeal> getSetmeal();

    /**
     * 获取热门套餐报表数据;
     * @return
     */
    List<Map> getHotSetmeal();
}
