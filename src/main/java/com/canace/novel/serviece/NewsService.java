package com.canace.novel.serviece;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.dto.resp.NewsInfoRespDto;

import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 新闻资讯服务类
 * @date 2024/1/16 19:32
 */
public interface NewsService {

    /**
     * 最新新闻资讯(前两条)
     * @return 新闻资讯列表
     */
    RestResp<List<NewsInfoRespDto>> listLatestNews();

    RestResp<NewsInfoRespDto> getNewsById(Long id);
}
