package com.canace.novel.serviece.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canace.novel.core.common.constant.ErrorCodeEnum;
import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.core.constant.DatabaseConsts;
import com.canace.novel.dao.entity.NewsContent;
import com.canace.novel.dao.entity.NewsInfo;
import com.canace.novel.dao.mapper.NewsContentMapper;
import com.canace.novel.dao.mapper.NewsInfoMapper;
import com.canace.novel.dto.resp.NewsInfoRespDto;
import com.canace.novel.manager.cache.NewsCacheManager;
import com.canace.novel.serviece.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 新闻资讯服务实现类
 * @date 2024/1/16 19:34
 */

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsCacheManager newsCacheManager;

    private final NewsInfoMapper newsInfoMapper;

    private final NewsContentMapper newsContentMapper;

    @Override
    public RestResp<List<NewsInfoRespDto>> listLatestNews() {
        return RestResp.ok(newsCacheManager.listLatestNews());
    }

    /**
     * 根据新闻id查询新闻详情(不使用缓存)
     *
     * @param id 新闻id
     * @return 新闻详情
     */
    @Override
    public RestResp<NewsInfoRespDto> getNewsById(Long id) {
        // 根据新闻id查询新闻详情
        NewsInfo newsInfo = newsInfoMapper.selectById(id);

        // 根据新闻id查询新闻内容
        QueryWrapper<NewsContent> newsContentQueryWrapper = new QueryWrapper<>();
        newsContentQueryWrapper.eq(DatabaseConsts.NewsContentTable.COLUMN_NEWS_ID, id).last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        NewsContent newsContent = newsContentMapper.selectOne(newsContentQueryWrapper);

        // 封装返回结果
        if (newsInfo != null && newsContent != null) {
            NewsInfoRespDto newsInfoRespDto = new NewsInfoRespDto();
            BeanUtils.copyProperties(newsInfo, newsInfoRespDto);
            newsInfoRespDto.setContent(newsContent.getContent());
            return RestResp.ok(newsInfoRespDto);
        }
        return null;
    }
}
