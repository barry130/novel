package com.canace.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canace.novel.core.constant.CacheConsts;
import com.canace.novel.core.constant.DatabaseConsts;
import com.canace.novel.dao.entity.BookContent;
import com.canace.novel.dao.mapper.BookContentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author canace
 * @version 1.0
 * @description 书籍章节内容缓存管理器
 * @date 2024/1/17 16:26
 */

@Component
@RequiredArgsConstructor
public class BookContentCacheManager {
    private final BookContentMapper bookContentMapper;

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER, value = CacheConsts.BOOK_CONTENT_CACHE_NAME, key = "#chapterId")
    public String getBookContentByChapterId(Long chapterId) {
        QueryWrapper<BookContent> contentQueryWrapper = new QueryWrapper<>();
        contentQueryWrapper.eq(DatabaseConsts.BookContentTable.COLUMN_CHAPTER_ID, chapterId)
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        BookContent bookContent = bookContentMapper.selectOne(contentQueryWrapper);
        if (ObjectUtils.isEmpty(bookContent)) {
            return null;
        }
        return bookContent.getContent();
    }
}
