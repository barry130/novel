package com.canace.novel.controller;

import com.canace.novel.core.common.resp.PageRespDto;
import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.core.constant.ApiRouterConsts;
import com.canace.novel.dto.req.BookSearchReqDto;
import com.canace.novel.dto.resp.BookInfoRespDto;
import com.canace.novel.serviece.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author canace
 * @version 1.0
 * @description 前台门户-搜索模块
 * @date 2024/1/19 15:47
 */

@Tag(name = "SearchController", description = "前台门户-搜索模块")
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_SEARCH_URL_PREFIX)
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "搜索书籍")
    @GetMapping("books")
    public RestResp<PageRespDto<BookInfoRespDto>> searchBooks(@ParameterObject BookSearchReqDto bookSearchReqDto) throws IOException {
        return searchService.searchBooks(bookSearchReqDto);
    }
}
