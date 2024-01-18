package com.canace.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canace.novel.core.constant.CacheConsts;
import com.canace.novel.core.constant.DatabaseConsts;
import com.canace.novel.dao.entity.BookCategory;
import com.canace.novel.dao.mapper.BookCategoryMapper;
import com.canace.novel.dto.resp.BookCategoryRespDto;
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
 * @description 书籍分类列表缓存管理器
 * @date 2024/1/18 17:47
 */

@Component
@RequiredArgsConstructor
public class BookCategoryCacheManager {

    private final BookCategoryMapper bookCategoryMapper;

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER, value = CacheConsts.BOOK_CATEGORY_LIST_CACHE_NAME, key = "#workDirection")
    public List<BookCategoryRespDto> listCategory(Integer workDirection){
        // 根据workDirection查询书籍分类列表
        QueryWrapper<BookCategory> bookCategoryQueryWrapper = new QueryWrapper<>();
        bookCategoryQueryWrapper.eq(DatabaseConsts.BookCategoryTable.COLUMN_WORK_DIRECTION,workDirection);
        List<BookCategory> bookCategories = bookCategoryMapper.selectList(bookCategoryQueryWrapper);
        if(CollectionUtils.isEmpty(bookCategories)){
            return Collections.emptyList();
        }
        return bookCategories.stream().map(v -> {
            BookCategoryRespDto bookCategoryRespDto = new BookCategoryRespDto();
            BeanUtils.copyProperties(v, bookCategoryRespDto);
            return bookCategoryRespDto;
        }).toList();
    }
}
