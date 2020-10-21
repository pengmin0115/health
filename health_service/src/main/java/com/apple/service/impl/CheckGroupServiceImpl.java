package com.apple.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.apple.constant.MessageConstant;
import com.apple.dao.CheckGroupDao;
import com.apple.dao.CheckItemDao;
import com.apple.entity.PageResult;
import com.apple.pojo.CheckGroup;
import com.apple.pojo.CheckItem;
import com.apple.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author pengmin
 * @date 2020/10/10 11:00
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 分页查询检查组
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增检查组信息
     *
     * @param checkItemIds
     * @param checkGroup
     */
    @Override
    public void add(Integer[] checkItemIds, CheckGroup checkGroup) {
        checkGroupDao.addCheckGroup(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        Map map = new HashMap<>();
        map.put("checkGroupId", checkGroupId);
        for (Integer checkItemId : checkItemIds) {
            map.put("checkItemId", checkItemId);
            checkGroupDao.addRelationship(map);
        }
    }

    /**
     * 查询所有检查组
     *
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    /**
     * 根据ID查询检查组信息
     *
     * @param checkGroupId
     * @return
     */
    @Override
    public CheckGroup findById(Integer checkGroupId) {
        CheckGroup checkGroup = checkGroupDao.findById(checkGroupId);
        List<Integer> checkitemIds = new ArrayList<>();
        if (checkGroup!=null){
            List<CheckItem> checkItems = checkGroup.getCheckItems();
            if (checkItems != null && checkItems.size() > 0) {
                for (CheckItem checkItem : checkItems) {
                    Integer id = checkItem.getId();
                    checkitemIds.add(id);
                }
            }
        }
        checkGroup.setCheckitemIds(checkitemIds);
        return checkGroup;
    }

    /**
     * 修改检查组信息;
     * @param checkGroup
     */
    @Override
    public void edit(CheckGroup checkGroup) {
        List<Integer> checkitemIds = checkGroup.getCheckitemIds();
        Integer checkGroupId = checkGroup.getId();
        Map<String, Integer> map = new HashMap<>();
        map.put("checkGroupId",checkGroupId);
        checkGroupDao.update(checkGroup);
        checkGroupDao.clearCheckItemsByGroupId(checkGroupId);
        if (checkitemIds!=null && checkitemIds.size()>0){
            for (Integer checkItemId : checkitemIds) {
                map.put("checkItemId",checkItemId);
                checkGroupDao.addRelationship(map);
            }
        }
    }

    /**
     * deleteById
     * @param checkGroupId
     */
    @Override
    public void deleteById(Integer checkGroupId) {
        Long count = checkItemDao.countItemsByCheckGroupId(checkGroupId);
        if (count>0){
            throw  new RuntimeException(MessageConstant.DELETE_CHECKGROUP_FAIL2);
        }
        checkGroupDao.deleteById(checkGroupId);
    }
}

