package com.apple.job;
import com.apple.constant.RedisConstant;
import com.apple.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import java.util.Set;
/**
 * @author pengmin
 * @date 2020/10/17 15:25
 */
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;
    public void clearImg(){
        // 得到2个redis集合的差集;
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (sdiff!=null && sdiff.size()>0){
            for (String imgFileName : sdiff) {
                QiniuUtils.deleteFileFromQiniu(imgFileName);
                // 删除redis数据库中对应垃圾图片的字段;
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,imgFileName);
            }
        }
    }
}
