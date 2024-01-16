package com.canace.novel.serviece.impl;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.dto.resp.BookRankRespDto;
import com.canace.novel.manager.cache.BookRankCacheManager;
import com.canace.novel.serviece.BookRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 书籍排行服务实现类
 * @date 2024/1/16 15:27
 */
@Service
@RequiredArgsConstructor
public class BookRankServiceImpl implements BookRankService {

    private final BookRankCacheManager bookRankCacheManager;
    @Override
    public RestResp<List<BookRankRespDto>> listBookRankByVisit() {
        return  RestResp.ok(bookRankCacheManager.listBookRankByVisit());
    }
}
