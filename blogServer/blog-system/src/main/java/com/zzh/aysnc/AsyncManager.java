package com.zzh.aysnc;

import com.zzh.common.utils.IpUtils;
import com.zzh.common.utils.RedisUtils;
import com.zzh.dto.EmailDTO;
import com.zzh.service.MusicService;
import com.zzh.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.zzh.common.constant.RedisConstant.ARTICLE_VIEW;
import static com.zzh.common.constant.RedisConstant.ARTICLE_VIEWS_COUNT;

/**
 * @author zzh
 * @description
 * @date 2022/2/7 15:05
 */

@Component
public class AsyncManager {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private MusicService musicService;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param emailDTO 邮箱内容
     * @param type 邮箱类型
     * @description 邮箱发送业务---使用自定义线程池 taskExecutor
     * @date 2022/4/17
     */
    @Async("taskExecutor")
    public void sendMail(EmailDTO emailDTO,Integer type) {
        mailUtil.send(emailDTO,type);
    }

    /**
     * @param ids 删除的id
     * @description 删除音乐文件
     * @date 2022/4/17
     */
    @Async("taskExecutor")
    public void deleteMusic(List<Integer> ids) {
        for (Integer id : ids) {
            musicService.deleteMusic(id);
        }
    }

    /**
     * @param articleId 文章id
     * @description 更新文章观看量
     * @date 2022/4/17
     */
    @Async("taskExecutor")
    public void updateArticleViewsCount(Integer articleId, HttpServletRequest request) {
        // 判断是否第一次访问，增加浏览量
        String ip = IpUtils.getIpAddr(request);
        Map<String, Set<String>> map = (HashMap) redisUtils.get(ARTICLE_VIEW);
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }
        Set<String> set = map.get(ARTICLE_VIEW + ":" + articleId);
        if (Objects.isNull(set)) {
            set = new HashSet<>();
        }
        if (!set.contains(ip)) {
            set.add(ip);
            map.put(ARTICLE_VIEW + ":" + articleId, set);
            redisUtils.set(ARTICLE_VIEW, map);
            // 浏览量+1
            redisTemplate.boundHashOps(ARTICLE_VIEWS_COUNT).increment(articleId.toString(), 1);
        }
    }
}
