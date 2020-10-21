package com.apple.service;
import com.apple.entity.PageResult;
import com.apple.pojo.CheckItem;
import java.util.List;

/**
 * @author pengmin
 * @date 2020/10/8 20:11
 */
public interface CheckItemService {
    /**
     * 查找所有检查项
     *
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 添加检查项
     *
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据ID查找检查项
     */
    CheckItem findById(Integer checkItemId);

    /**
     * 编辑检查项
     */
    void edit(CheckItem checkItem);

    /**
     * 删除检查项
     */
    void deleteById(Integer checkItemId);
}
