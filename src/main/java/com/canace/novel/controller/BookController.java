package com.canace.novel.controller;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.core.constant.ApiRouterConsts;
import com.canace.novel.dto.resp.BookChapterAboutRespDto;
import com.canace.novel.dto.resp.BookInfoRespDto;
import com.canace.novel.serviece.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author canace
 * @version 1.0
 * @description 书籍API接口
 * @date 2024/1/17 11:59
 */

@Tag(name = "BookController", description = "前台门户-书籍详情")
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRouterConsts.API_FRONT_BOOK_URL_PREFIX)
@Slf4j
public class BookController {

    private final BookService bookService;

    /**
     * 根据书籍id查询书籍详情
     */
    @Operation(summary = "小说信息查询接口")
    @GetMapping("{id}")
    public RestResp<BookInfoRespDto> getBookInfoById(@Parameter(description = "小说 ID") @PathVariable("id") Long bookId) {
        return bookService.getBookInfoById(bookId);
    }

    /**
     * 增加小说点击量接口
     */
    @Operation(summary = "增加小说点击量接口")
    @PostMapping("visit")
    public RestResp<Void> addVisitCount(@Parameter(description = "小说ID") @RequestBody String s) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(s);
        String bookId = jsonNode.get("bookId").asText();
        return bookService.addVisitCount(Long.valueOf(bookId));
    }

    /**
     * 小说最新章节相关信息查询接口
     */
    @Operation(summary = "小说最新章节相关信息查询接口")
    @GetMapping("last_chapter/about")
    public RestResp<BookChapterAboutRespDto> getLastChapterAbout(
            @Parameter(description = "小说ID") Long bookId) {
        return bookService.getLastChapterAbout(bookId);
    }
}
