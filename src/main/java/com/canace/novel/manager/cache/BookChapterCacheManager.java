package com.canace.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canace.novel.core.constant.CacheConsts;
import com.canace.novel.core.constant.DatabaseConsts;
import com.canace.novel.dao.entity.BookChapter;
import com.canace.novel.dao.mapper.BookChapterMapper;
import com.canace.novel.dto.resp.BookChapterAboutRespDto;
import com.canace.novel.dto.resp.BookChapterRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

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

    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.BOOK_CHAPTER_CACHE_NAME, key = "#chapterId")
    public BookChapterRespDto getChapterInfoById(Long chapterId) {
        // 根据章节ID获取章节信息
        BookChapter bookChapter = bookChapterMapper.selectById(chapterId);
        if (bookChapter == null) {
            return null;
        }

        // 封装返回结果
        BookChapterRespDto bookChapterRespDto = new BookChapterRespDto();
        BeanUtils.copyProperties(bookChapter,bookChapterRespDto);

        return bookChapterRespDto;
    }

    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.BOOK_CHAPTER_COUNT_CACHE_NAME, key = "#bookId")
    public Long getChapterCountByBookId(Long bookId) {
        QueryWrapper<BookChapter> bookChapterQueryWrapper = new QueryWrapper<>();
        bookChapterQueryWrapper.eq(DatabaseConsts.BookChapterTable.COLUMN_BOOK_ID, bookId);
        return bookChapterMapper.selectCount(bookChapterQueryWrapper);
    }
}
