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

import java.util.List;

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
        bookChapterQueryWrapper.eq(DatabaseConsts.BookChapterTable.COLUMN_BOOK_ID, bookId).orderByAsc(DatabaseConsts.BookChapterTable.COLUMN_CHAPTER_NUM).last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        BookChapter bookChapter = bookChapterMapper.selectOne(bookChapterQueryWrapper);
        if (ObjectUtils.isEmpty(bookChapter)) {
            return null;
        }

        // 封装返回结果
        BookInfoRespDto bookInfoRespDto = new BookInfoRespDto();
        BeanUtils.copyProperties(bookInfo, bookInfoRespDto);
        bookInfoRespDto.setFirstChapterId(bookChapter.getId());

        return bookInfoRespDto;
    }


    /**
     * 查询每个类别下最新更新的 500 个小说ID列表，并放入缓存中 1 个小时
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.LAST_UPDATE_BOOK_ID_LIST_CACHE_NAME)
    public List<Long> getLastUpdateIdList(Long categoryId) {
        QueryWrapper<BookInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.BookTable.COLUMN_CATEGORY_ID, categoryId).gt(DatabaseConsts.BookTable.COLUMN_WORD_COUNT, 0).orderByDesc(DatabaseConsts.BookTable.COLUMN_LAST_CHAPTER_UPDATE_TIME).last(DatabaseConsts.SqlEnum.LIMIT_500.getSql());
        return bookInfoMapper.selectList(queryWrapper).stream().map(BookInfo::getId).toList();
    }
}
