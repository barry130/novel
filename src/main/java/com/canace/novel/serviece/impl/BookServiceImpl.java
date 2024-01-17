package com.canace.novel.serviece.impl;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.dao.mapper.BookInfoMapper;
import com.canace.novel.dto.resp.BookChapterAboutRespDto;
import com.canace.novel.dto.resp.BookChapterRespDto;
import com.canace.novel.dto.resp.BookContentAboutRespDto;
import com.canace.novel.dto.resp.BookInfoRespDto;
import com.canace.novel.manager.cache.BookChapterCacheManager;
import com.canace.novel.manager.cache.BookContentCacheManager;
import com.canace.novel.manager.cache.BookInfoCacheManager;
import com.canace.novel.serviece.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author canace
 * @version 1.0
 * @description 书籍详情服务实现类
 * @date 2024/1/17 12:09
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookInfoCacheManager bookInfoCacheManager;

    private final BookInfoMapper bookInfoMapper;

    private final BookChapterCacheManager bookChapterCacheManager;

    private final BookContentCacheManager bookContentCacheManager;
    @Override
    public RestResp<BookInfoRespDto> getBookInfoById(Long bookId) {
        BookInfoRespDto bookInfoById = bookInfoCacheManager.getBookInfoById(bookId);
        if(bookInfoById == null){
            return RestResp.fail();
        }
        return RestResp.ok(bookInfoById);
    }

    @Override
    public RestResp<Void> addVisitCount(Long bookId) {
        // 先直接写到数据库，后续在改为缓存处理
        bookInfoMapper.addVisitCount(bookId);
        return RestResp.ok();
    }

    @Override
    public RestResp<BookChapterAboutRespDto> getLastChapterAbout(Long bookId) {
        // 从书籍详情缓存管理器中获取书籍详情
        BookInfoRespDto bookInfoRespDto = bookInfoCacheManager.getBookInfoById(bookId);
        if (ObjectUtils.isEmpty(bookInfoRespDto)){
            return RestResp.fail();
        }
        // 从书籍详情中获取最新章节ID
        Long lastChapterId = bookInfoRespDto.getLastChapterId();
        // 根据最新章节ID查询最新章节信息
        BookChapterRespDto chapterInfoById = bookChapterCacheManager.getChapterInfoById(lastChapterId);
        if (ObjectUtils.isEmpty(chapterInfoById)){
            return RestResp.fail();
        }
        // 根据最新章节ID查询最新章节内容
        String bookContentByChapterId = bookContentCacheManager.getBookContentByChapterId(lastChapterId);
        if (bookContentByChapterId == null){
            return RestResp.fail();
        }

        // 查询章节总数，根据bookId查询count
        Long chapterCount = bookChapterCacheManager.getChapterCountByBookId(bookId);

        // 封装返回结果
        BookChapterAboutRespDto bookChapterAboutRespDto = new BookChapterAboutRespDto();
        bookChapterAboutRespDto.setChapterInfo(chapterInfoById);
        bookChapterAboutRespDto.setContentSummary(bookContentByChapterId);
        bookChapterAboutRespDto.setChapterTotal(chapterCount);

        return RestResp.ok(bookChapterAboutRespDto);
    }

    @Override
    public RestResp<BookContentAboutRespDto> getBookContentAbout(Long chapterId) {
        // 从章节缓存管理器中读取章节信息
        BookChapterRespDto chapterInfoById = bookChapterCacheManager.getChapterInfoById(chapterId);
        if (ObjectUtils.isEmpty(chapterInfoById)){
            return RestResp.fail();
        }

        // 从章节内容缓存管理器中读取章节内容
        String bookContentByChapterId = bookContentCacheManager.getBookContentByChapterId(chapterId);

        //从书籍详情缓存管理器中读取书籍详情
        BookInfoRespDto bookInfoById = bookInfoCacheManager.getBookInfoById(chapterInfoById.getBookId());
        if (ObjectUtils.isEmpty(bookInfoById)){
            return RestResp.fail();
        }

        // 封装返回结果
        BookContentAboutRespDto bookContentAboutRespDto = new BookContentAboutRespDto();
        bookContentAboutRespDto.setChapterInfo(chapterInfoById);
        bookContentAboutRespDto.setBookContent(bookContentByChapterId);
        bookContentAboutRespDto.setBookInfo(bookInfoById);

        return RestResp.ok(bookContentAboutRespDto);
    }
}
