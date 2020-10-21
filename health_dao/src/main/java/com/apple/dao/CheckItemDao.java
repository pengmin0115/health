package com.apple.dao;

import com.apple.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author pengmin
 * @date 2020/10/8 20:48
 */
public interface CheckItemDao {
    /**
     * 根据检查组Id查询检查项信息;
     * @param checkGroupId
     * @return
     */
    List<CheckItem> findCheckItemsByCheckGroupId(Integer checkGroupId);

    /**
     * 查找所有检查项
     *
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 增加检查项
     *
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询检查项
     *
     * @param queryString
     * @return
     */
    Page<CheckItem> findPage(String queryString);

    /**
     * 根据ID查找检查项
     *
     * @param checkItemId
     * @return
     */
    CheckItem findById(Integer checkItemId);

    /**
     * 编辑检查项
     * @param checkItem
     */
    void edit(CheckItem checkItem);

    /**
     * 查询检查项在关系表中的数据条数
     * @param checkItemId
     * @return
     */
    int findCountByCheckItemId(Integer checkItemId);

    /**
     * 根据ID删除检查项
     * @param checkItemId
     */
    void deleteById(Integer checkItemId);

    /**
     * countItemsByCheckGroupId
     * @param checkGroupId
     * @return
     */
    Long countItemsByCheckGroupId(Integer checkGroupId);
}
