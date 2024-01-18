package com.canace.novel.controller;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.core.constant.ApiRouterConsts;
import com.canace.novel.dto.resp.*;
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

import java.security.NoSuchAlgorithmException;
import java.util.List;

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

    /**
     * 小说内容相关信息查询接口
     */
    @Operation(summary = "小说内容相关信息查询接口")
    @GetMapping("content/{chapterId}")
    public RestResp<BookContentAboutRespDto> getBookContentAbout(
            @Parameter(description = "章节ID") @PathVariable("chapterId") Long chapterId) {
        return bookService.getBookContentAbout(chapterId);
    }

    /**
     * 小说推荐列表查询接口
     */
    @Operation(summary = "小说推荐列表查询接口")
    @GetMapping("rec_list")
    public RestResp<List<BookInfoRespDto>> listRecBooks(
            @Parameter(description = "小说ID") Long bookId) throws NoSuchAlgorithmException {
        return bookService.listRecBooks(bookId);
    }

    /**
     * 数据全部章节查询
     * @param bookId 书籍ID
     * @return 书籍全部章节信息
     */
    @Operation(summary = "小说全部章节查询")
    @GetMapping("chapter/list")
    public RestResp<List<BookChapterRespDto>> listBookChapter(
            @Parameter(description = "小说ID")  @RequestParam("bookId") Long bookId) {
        return bookService.listBookChapter(bookId);
    }

    /**
     * 获取上一章节ID接口
     */
    @Operation(summary = "获取上一章节ID接口")
    @GetMapping("pre_chapter_id/{chapterId}")
    public RestResp<Long> getPreChapterId(
            @Parameter(description = "章节ID") @PathVariable("chapterId") Long chapterId) {
        return bookService.getPreChapterId(chapterId);
    }

    /**
     * 获取下一章节ID接口
     */
    @Operation(summary = "获取下一章节ID接口")
    @GetMapping("next_chapter_id/{chapterId}")
    public RestResp<Long> getNextChapterId(
            @Parameter(description = "章节ID") @PathVariable("chapterId") Long chapterId) {
        return bookService.getNextChapterId(chapterId);
    }

    /**
     * 小说分类列表查询接口
     */
    @Operation(summary = "小说分类列表查询接口")
    @GetMapping("category/list")
    public RestResp<List<BookCategoryRespDto>> listCategory(
            @Parameter(description = "作品方向", required = true) Integer workDirection) {
        return bookService.listCategory(workDirection);
    }
}
