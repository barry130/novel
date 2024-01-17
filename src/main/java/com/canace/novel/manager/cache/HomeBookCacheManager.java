package com.canace.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canace.novel.core.constant.CacheConsts;
import com.canace.novel.core.constant.DatabaseConsts;
import com.canace.novel.dao.entity.BookInfo;
import com.canace.novel.dao.entity.HomeBook;
import com.canace.novel.dao.mapper.BookInfoMapper;
import com.canace.novel.dao.mapper.HomeBookMapper;
import com.canace.novel.dto.resp.HomeBookRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author canace
 * @version 1.0
 * @description 首页推荐书籍缓存管理器
 * @date 2024/1/15 21:16
 */

@Component
@RequiredArgsConstructor //简化@Autowired的写法
@Slf4j
public class HomeBookCacheManager {

    private final HomeBookMapper homeBookMapper;

    private final BookInfoMapper bookInfoMapper;

    /**
     * 查询首页的小说推荐，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.HOME_BOOK_CACHE_NAME)
    public List<HomeBookRespDto> listHomeBook() {
        // 查询首页推荐的书籍
        QueryWrapper<HomeBook> homeBookQueryWrapper = new QueryWrapper<>();
        // 首先根据sort排序，然后在查询列表
        homeBookQueryWrapper.orderByAsc(DatabaseConsts.CommonColumnEnum.SORT.getName());
        List<HomeBook> homeBookList = homeBookMapper.selectList(homeBookQueryWrapper);

        // 查询书籍信息
        //先获取书籍id(homeBookList的bookId)
        if(!CollectionUtils.isEmpty(homeBookList)){
            List<Long> bookIdList = homeBookList.stream().map(HomeBook::getBookId).toList();
            //再根据书籍id获取书籍信息
            QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
            bookInfoQueryWrapper.in(DatabaseConsts.CommonColumnEnum.ID.getName(), bookIdList);
            List<BookInfo> bookInfos = bookInfoMapper.selectList(bookInfoQueryWrapper);

            // 封装返回结果
            if (!CollectionUtils.isEmpty(bookInfos)) {
                // 将书籍信息List转换为 Map,其中Key为id,value为BookInfo对象
                Map<Long, BookInfo> bookInfoMap = bookInfos.stream().collect(Collectors.toMap(BookInfo::getId, Function.identity()));
                return homeBookList.stream().map(v -> {
                    // 根据书籍 id 从map中获取书籍信息
                    BookInfo bookInfo = bookInfoMap.get(v.getBookId());
                    HomeBookRespDto homeBookRespDto = new HomeBookRespDto();
                    BeanUtils.copyProperties(bookInfo, homeBookRespDto);
                    homeBookRespDto.setBookId(v.getBookId());
                    homeBookRespDto.setType(Integer.valueOf(v.getType()));
                    return homeBookRespDto;
                }).toList();
            }
            log.error("根据书籍ID获取书籍失败，书籍信息列表为空");
        }
        log.error("查询首页推荐的书籍失败，首页推荐书籍列表空");
        return Collections.emptyList();
    }
}
