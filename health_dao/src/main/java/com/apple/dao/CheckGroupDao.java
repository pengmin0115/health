package com.apple.dao;
import com.apple.pojo.CheckGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author pengmin
 * @date 2020/10/10 11:00
 */

public interface CheckGroupDao {
    /**
     * 根据套餐Id查询对应检查组信息;
     * @param setmealId
     * @return
     */
    List<CheckGroup> findCheckGroupList(Integer setmealId);

    /**
     * 分页查询检查组
     * @param queryString
     * @return
     */
    Page<CheckGroup> findPage(String queryString);

    /**
     * 添加检查组数据
     * @param checkGroup
     */
    void addCheckGroup(CheckGroup checkGroup);

    /**
     * 添加中间表数据
     * @param map
     */
    void addRelationship(Map map);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 根据ID查询检查组信息;
     * @param checkGroupId
     * @return
     */
    CheckGroup findById(Integer checkGroupId);

    /**
     * 更新检查组信息;
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 清楚检查组中的所有检查项数据；
     * @param checkGroupId
     */
    void clearCheckItemsByGroupId(Integer checkGroupId);

    /**
     * 删除检查组
     * @param checkGroupId
     */
    void deleteById(Integer checkGroupId);
}
