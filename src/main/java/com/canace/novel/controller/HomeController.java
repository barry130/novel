package com.canace.novel.controller;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.core.constant.ApiRouterConsts;
import com.canace.novel.dto.resp.HomeBookRespDto;
import com.canace.novel.serviece.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 首页 API 接口
 * @date 2024/1/16 10:47
 */

@Tag(name = "HomeController", description = "前台门户-首页模块")
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_HOME_URL_PREFIX)
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    /**
     * 首页推荐小说查询接口
     */
    @Operation(summary = "首页推荐小说查询接口")
    @GetMapping("books")
    public RestResp<List<HomeBookRespDto>> listHomeBook() {
        return homeService.listHomeBook();
    }
}
