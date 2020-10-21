package com.apple.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.apple.constant.MessageConstant;
import com.apple.dao.CheckItemDao;
import com.apple.entity.PageResult;
import com.apple.pojo.CheckItem;
import com.apple.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * @author pengmin
 * @date 2020/10/8 20:26
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据ID查找检查项
     */
    @Override
    public CheckItem findById(Integer checkItemId) {
        return checkItemDao.findById(checkItemId);
    }

    /**
     * 编辑检查项
     */
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public void deleteById(Integer checkItemId) {
        //先查询该查询项是否和中间表有关系
        int count = checkItemDao.findCountByCheckItemId(checkItemId);
        if (count>0){
            throw new RuntimeException(MessageConstant.DELETE_CHECKITEM_FAIL2);
        }
        checkItemDao.deleteById(checkItemId);
    }
}
