package com.canace.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canace.novel.core.constant.CacheConsts;
import com.canace.novel.core.constant.DatabaseConsts;
import com.canace.novel.dao.entity.BookInfo;
import com.canace.novel.dao.entity.NewsInfo;
import com.canace.novel.dao.mapper.NewsInfoMapper;
import com.canace.novel.dto.resp.BookRankRespDto;
import com.canace.novel.dto.resp.NewsInfoRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 新闻资讯缓存管理器
 * @date 2024/1/16 19:25
 */

@Component
@RequiredArgsConstructor
public class NewsCacheManager {

    private final NewsInfoMapper newsInfoMapper;

    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.LATEST_NEWS_CACHE_NAME)
    public List<NewsInfoRespDto> listLatestNews() {
        QueryWrapper<NewsInfo> newsInfoQueryWrapper = new QueryWrapper<>();
        // 根据创建时间查询最近的两条新闻
        newsInfoQueryWrapper.orderByDesc(DatabaseConsts.CommonColumnEnum.CREATE_TIME.getName()).last(DatabaseConsts.SqlEnum.LIMIT_2.getSql());
        // 获取查询结果
        List<NewsInfo> newsInfos = newsInfoMapper.selectList(newsInfoQueryWrapper);
        // 封装返回结果
        if(!CollectionUtils.isEmpty(newsInfos)){
            return newsInfos.stream().map(newsInfo -> {
                NewsInfoRespDto newsInfoRespDto = new NewsInfoRespDto();
                BeanUtils.copyProperties(newsInfo, newsInfoRespDto);
                return newsInfoRespDto;
            }).toList();
        }
        return Collections.emptyList();
    }
}
