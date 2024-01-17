package com.canace.novel.serviece.impl;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.dao.mapper.BookInfoMapper;
import com.canace.novel.dto.resp.BookInfoRespDto;
import com.canace.novel.manager.cache.BookInfoCacheManager;
import com.canace.novel.serviece.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
