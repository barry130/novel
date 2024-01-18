package com.canace.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canace.novel.core.constant.CacheConsts;
import com.canace.novel.core.constant.DatabaseConsts;
import com.canace.novel.dao.entity.BookChapter;
import com.canace.novel.dao.mapper.BookChapterMapper;
import com.canace.novel.dto.resp.BookChapterRespDto;
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
 * @description 书籍章节缓存管理
 * @date 2024/1/17 16:18
 */
@Component
@RequiredArgsConstructor
public class BookChapterCacheManager {

    private final BookChapterMapper bookChapterMapper;

    /**
     * 根据章节id获取章节信息
     *
     * @param chapterId 章节ID
     * @return 单个章节详情
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.BOOK_CHAPTER_CACHE_NAME, key = "#chapterId")
    public BookChapterRespDto getChapterInfoById(Long chapterId) {
        // 根据章节ID获取章节信息
        BookChapter bookChapter = bookChapterMapper.selectById(chapterId);
        if (bookChapter == null) {
            return null;
        }

        // 封装返回结果
        BookChapterRespDto bookChapterRespDto = new BookChapterRespDto();
        BeanUtils.copyProperties(bookChapter, bookChapterRespDto);

        return bookChapterRespDto;
    }

    /**
     * 根据书籍ID获取书籍章节数
     *
     * @param bookId 书籍ID
     * @return 章节数
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.BOOK_CHAPTER_COUNT_CACHE_NAME, key = "#bookId")
    public Long getChapterCountByBookId(Long bookId) {
        QueryWrapper<BookChapter> bookChapterQueryWrapper = new QueryWrapper<>();
        bookChapterQueryWrapper.eq(DatabaseConsts.BookChapterTable.COLUMN_BOOK_ID, bookId);
        return bookChapterMapper.selectCount(bookChapterQueryWrapper);
    }

    /**
     * 根据书籍ID获取书籍的全部章节信息
     * @param bookId 书籍ID
     * @return 全部章节信息
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.BOOK_ALL_CHAPTER_CACHE_NAME, key = "#bookId")
    public List<BookChapterRespDto> listBookChapter(Long bookId) {
        QueryWrapper<BookChapter> bookChapterQueryWrapper = new QueryWrapper<>();
        bookChapterQueryWrapper.eq(DatabaseConsts.BookChapterTable.COLUMN_BOOK_ID, bookId).orderByAsc(DatabaseConsts.BookChapterTable.COLUMN_BOOK_ID);
        List<BookChapter> bookChapters = bookChapterMapper.selectList(bookChapterQueryWrapper);
        if (CollectionUtils.isEmpty(bookChapters)) {
            return Collections.emptyList();
        }
        // 封装返回结果
        return bookChapters.stream().map(v -> {
            BookChapterRespDto bookChapterRespDto = new BookChapterRespDto();
            BeanUtils.copyProperties(v, bookChapterRespDto);
            return bookChapterRespDto;
        }).toList();

    }
}
