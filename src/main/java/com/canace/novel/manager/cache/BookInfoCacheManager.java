package com.canace.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canace.novel.core.constant.CacheConsts;
import com.canace.novel.core.constant.DatabaseConsts;
import com.canace.novel.dao.entity.BookChapter;
import com.canace.novel.dao.entity.BookInfo;
import com.canace.novel.dao.mapper.BookChapterMapper;
import com.canace.novel.dao.mapper.BookInfoMapper;
import com.canace.novel.dto.resp.BookInfoRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author canace
 * @version 1.0
 * @description 书籍信息缓存管理器
 * @date 2024/1/17 13:28
 */

@Component
@RequiredArgsConstructor
public class BookInfoCacheManager {

    private final BookInfoMapper bookInfoMapper;
    private final BookChapterMapper bookChapterMapper;

    /**
     * 根据书籍id查询书籍信息
     *
     * @param bookId 书籍id
     * @return 书籍信息
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.BOOK_INFO_CACHE_NAME, key = "#bookId")
    public BookInfoRespDto getBookInfoById(Long bookId) {
        // 根据书籍ID查询书籍信息
        BookInfo bookInfo = bookInfoMapper.selectById(bookId);
        if (ObjectUtils.isEmpty(bookInfo)) {
            return null;
        }
        // 查询当前书籍的首个章节ID
        QueryWrapper<BookChapter> bookChapterQueryWrapper = new QueryWrapper<>();
        bookChapterQueryWrapper.eq(DatabaseConsts.BookChapterTable.COLUMN_BOOK_ID, bookId)
                .orderByAsc(DatabaseConsts.BookChapterTable.COLUMN_CHAPTER_NUM)
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        BookChapter bookChapter = bookChapterMapper.selectOne(bookChapterQueryWrapper);
        if(ObjectUtils.isEmpty(bookChapter)){
            return null;
        }

        // 封装返回结果
        BookInfoRespDto bookInfoRespDto = new BookInfoRespDto();
        BeanUtils.copyProperties(bookInfo, bookInfoRespDto);
        bookInfoRespDto.setFirstChapterId(bookChapter.getId());

        return bookInfoRespDto;
    }

}
