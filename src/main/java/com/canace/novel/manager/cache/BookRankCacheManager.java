package com.canace.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canace.novel.core.constant.CacheConsts;
import com.canace.novel.core.constant.DatabaseConsts;
import com.canace.novel.dao.entity.BookInfo;
import com.canace.novel.dao.mapper.BookInfoMapper;
import com.canace.novel.dto.resp.BookRankRespDto;
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
 * @description 书籍排行榜
 * @date 2024/1/16 14:23
 */

@Component
@RequiredArgsConstructor
public class BookRankCacheManager {

    private final BookInfoMapper bookInfoMapper;

    /**
     * 查询书籍点击排行榜
     */
    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER, value = CacheConsts.BOOK_VISIT_RANK_CACHE_NAME)
    public List<BookRankRespDto> listBookRankByVisit() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        // 根据点击数排行
        bookInfoQueryWrapper.orderByDesc(DatabaseConsts.BookTable.COLUMN_VISIT_COUNT);
        return getBookRankRespDto(bookInfoQueryWrapper);
    }

    /**
     * 新书排行，以书籍的创建时间进行排序
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.BOOK_NEWEST_RANK_CACHE_NAME)
    public List<BookRankRespDto> listBookRankByCreateTime() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        // 根据创建时间排行
        bookInfoQueryWrapper.orderByDesc(DatabaseConsts.CommonColumnEnum.CREATE_TIME.getName());
        return getBookRankRespDto(bookInfoQueryWrapper);
    }

    /**
     * 书籍更新排行，以书籍的更新时间进行排序
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.BOOK_UPDATE_RANK_CACHE_NAME)
    public List<BookRankRespDto> listBookRankByUpdateTime() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        // 根据创建时间排行
        bookInfoQueryWrapper.orderByDesc(DatabaseConsts.CommonColumnEnum.UPDATE_TIME.getName());
        return getBookRankRespDto(bookInfoQueryWrapper);
    }


    /**
     * 对公共方法进行封装
     *
     * @param bookInfoQueryWrapper 查询条件
     * @return 书籍排行dto
     */
    private List<BookRankRespDto> getBookRankRespDto(QueryWrapper<BookInfo> bookInfoQueryWrapper) {
        // 只查询字数大于0的前30条数据
        bookInfoQueryWrapper.gt(DatabaseConsts.BookTable.COLUMN_WORD_COUNT, 0).last(DatabaseConsts.SqlEnum.LIMIT_30.getSql());
        // 获取结果
        List<BookInfo> bookInfos = bookInfoMapper.selectList(bookInfoQueryWrapper);
        if (!CollectionUtils.isEmpty(bookInfos)) {
            // 封装返回结果
            return bookInfos.stream().map(bookInfo -> {
                BookRankRespDto bookRankRespDto = new BookRankRespDto();
                // 将bookInfo的属性拷贝到bookRankRespDto中
                BeanUtils.copyProperties(bookInfo, bookRankRespDto);
                return bookRankRespDto;
            }).toList();
        }
        return Collections.emptyList();
    }
}
