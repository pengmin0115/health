package com.apple.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.apple.constant.MessageConstant;
import com.apple.dao.SetMealDao;
import com.apple.entity.PageResult;
import com.apple.pojo.Setmeal;
import com.apple.service.SetMealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengmin
 * @date 2020/10/12 21:00
 */
@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealDao setMealDao;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")
    private String outputPrePath;

    /**
     * 分页查询套餐
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setMealDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增套餐并生成对应的静态页面
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setMealDao.addSetmeal(setmeal);
        addSetmealAndCheckgroup(setmeal, checkgroupIds);
        // 生成对应静态页面
        generateStaticSetmealHtml();
    }

    /**
     * 生成对应静态页面
     */
    private void generateStaticSetmealHtml() {
        List<Setmeal> setmealList = setMealDao.getSetmeal();
        generateSetmealListStaticHtml(setmealList);
        generateSetmealDetailStaticHtml(setmealList);
    }

    /**
     * 生成套餐列表静态页面
     *
     * @param setmealList
     */
    private void generateSetmealListStaticHtml(List<Setmeal> setmealList) {
        if (setmealList != null && setmealList.size() > 0) {
            Map data = new HashMap();
            data.put("setmealList", setmealList);
            generateHtml("mobile_setmeal.ftl", data, "m_setmeal.html");
        }
    }

    /**
     * 生成套餐详情静态页面
     *
     * @param setmealList
     */
    private void generateSetmealDetailStaticHtml(List<Setmeal> setmealList) {
        if (setmealList != null && setmealList.size() > 0) {
            for (Setmeal setmeal : setmealList) {
                Setmeal resultSetmeal = setMealDao.findById(setmeal.getId());
                Map data = new HashMap();
                data.put("setmeal", resultSetmeal);
                generateHtml("mobile_setmeal_detail.ftl", data, "setmeal_detail_"+setmeal.getId()+".html");
            }
        }
    }

    /**
     * 生成套餐静态页面
     * @param templateFileName
     * @param data
     * @param outputPath
     */
    private void generateHtml(String templateFileName, Map data, String outputPath) {
        Writer out = null;
        try {
            //1.获取配置对象
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            //2.得到模板对象
            Template template = configuration.getTemplate(templateFileName);
            //3.BufferWriter对象输出文件  效率高于FileWriter
            //静态页面输出目录
            String outpathFileName = outputPrePath+"/"+outputPath;
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outpathFileName))));
            //4.填充数据
            template.process(data,out);
        } catch (Exception e) {
            System.out.println("生成静态页面报错了。。。");
            e.printStackTrace();
        }finally {
            if(out !=null){
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 根据套餐ID查询套餐信息
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }

    /**
     * 根据套餐ID查询对应检查组信息
     *
     * @param setmealId
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIds(Integer setmealId) {
        return setMealDao.findCheckGroupIds(setmealId);
    }

    /**
     * 编辑套餐
     *
     * @param setmeal
     * @param checkGroupIds
     */
    @Override
    public void edit(Setmeal setmeal, Integer[] checkGroupIds) {
        setMealDao.updateSetmeal(setmeal);
        setMealDao.clearSetmealAndCheckgroup(setmeal.getId());
        addSetmealAndCheckgroup(setmeal, checkGroupIds);
    }

    /**
     * 删除套餐
     *
     * @param setmealId
     */
    @Override
    public void delete(Integer setmealId) {
        Long count = setMealDao.countCheckGroup(setmealId);
        if (count > 0) {
            throw new RuntimeException(MessageConstant.DELETE_SETMEAL_FAIL2);
        }
        setMealDao.delete(setmealId);
    }

    /**
     * 获取套餐列表
     *
     * @return
     */
    @Override
    public List<Setmeal> getSetmeal() {
        return setMealDao.getSetmeal();
    }

    private void addSetmealAndCheckgroup(Setmeal setmeal, Integer[] checkGroupIds) {
        Map map = new HashMap();
        map.put("setmeal_id", setmeal.getId());
        for (Integer checkGroupId : checkGroupIds) {
            map.put("checkgroup_id", checkGroupId);
            setMealDao.addSetmealAndCheckgroup(map);
        }
    }
}
