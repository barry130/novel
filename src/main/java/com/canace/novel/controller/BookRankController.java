package com.canace.novel.controller;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.core.constant.ApiRouterConsts;
import com.canace.novel.dto.resp.BookRankRespDto;
import com.canace.novel.serviece.BookRankService;
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
 * @description 书籍排行榜API接口
 * @date 2024/1/16 15:29
 */

@Tag(name = "BookRankController", description = "前台门户-书籍排行")
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRouterConsts.API_FRONT_BOOK_URL_PREFIX)
public class BookRankController {

    private final BookRankService bookRankService;

    @Operation(summary = "书籍点击排行榜接口")
    @GetMapping("visit_rank")
    public RestResp<List<BookRankRespDto>> listBookRankByVisit(){
        return bookRankService.listBookRankByVisit();
    }

    @Operation(summary = "书籍创建时间(新书)排行榜接口")
    @GetMapping("newest_rank")
    public RestResp<List<BookRankRespDto>> listBookRankByCreateTime(){
        return bookRankService.listBookRankByCreateTime();
    }

    @Operation(summary = "书籍更新时间排行榜接口")
    @GetMapping("update_rank")
    public RestResp<List<BookRankRespDto>> listBookRankByUpdateTime(){
        return bookRankService.listBookRankByUpdateTime();
    }
}
