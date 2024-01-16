package com.canace.novel.controller;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.core.constant.ApiRouterConsts;
import com.canace.novel.dto.resp.HomeFriendLinkRespDto;
import com.canace.novel.dto.resp.NewsInfoRespDto;
import com.canace.novel.serviece.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 前台门户-新闻模块
 * @date 2024/1/16 19:36
 */

@RequestMapping(ApiRouterConsts.API_FRONT_NEWS_URL_PREFIX)
@RequiredArgsConstructor
@RestController
@Tag(name = "NewsController", description = "前台门户-新闻模块")
public class NewsController {

    private final NewsService newsService;
    @Operation(summary = "最新新闻列表查询接口")
    @GetMapping("latest_list")
    public RestResp<List<NewsInfoRespDto>> listNewsInfo() {
        return newsService.listLatestNews();
    }

    @Operation(summary = "根据新闻id查询新闻详情")
    @GetMapping("{id}")
    public RestResp<NewsInfoRespDto> getNewsById(@Parameter(description = "新闻id") @PathVariable("id") Long id) {
        return newsService.getNewsById(id);
    }
}
