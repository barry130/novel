package com.canace.novel.serviece;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.dto.resp.HomeBookRespDto;

import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 首页服务类
 * @date 2024/1/16 10:41
 */
public interface HomeService {

    /**
     * 查询首页的小说推荐
     */
    RestResp<List<HomeBookRespDto>> listHomeBook();
}
