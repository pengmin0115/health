package com.apple.service;

import com.apple.entity.PageResult;
import com.apple.pojo.CheckGroup;

import java.util.List;

/**
 * @author pengmin
 * @date 2020/10/10 10:59
 */

public interface CheckGroupService {
    /**
     * 分页查询检查组
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 新增检查组信息
     * @param checkItemIds
     * @param checkGroup
     */
    void add(Integer[] checkItemIds, CheckGroup checkGroup);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 根据ID查询检查组信息
     * @param checkGroupId
     * @return
     */
    CheckGroup findById(Integer checkGroupId);

    /**
     * 修改检查组;
     * @param checkGroup
     */
    void edit(CheckGroup checkGroup);

    /**
     * delete checkGroup
     * @param checkGroupId
     */
    void deleteById(Integer checkGroupId);
}
