package com.canace.novel.serviece.impl;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.dto.resp.HomeBookRespDto;
import com.canace.novel.manager.cache.HomeBookCacheManager;
import com.canace.novel.serviece.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 首页服务实现类
 * @date 2024/1/16 10:42
 */
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final HomeBookCacheManager homeBookCacheManager;

    /**
     * 查询首页的小说推荐 服务实现类
     */
    @Override
    public RestResp<List<HomeBookRespDto>> listHomeBook() {
        //直接从缓存管理器中获取数据
        return RestResp.ok(homeBookCacheManager.listHomeBook());
    }
}
