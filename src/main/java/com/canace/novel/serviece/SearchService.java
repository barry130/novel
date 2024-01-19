package com.canace.novel.serviece;

import com.canace.novel.core.common.resp.PageRespDto;
import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.dto.req.BookSearchReqDto;
import com.canace.novel.dto.resp.BookInfoRespDto;

import java.io.IOException;

/**
 * @author canace
 * @version 1.0
 * @description 搜索服务类
 * @date 2024/1/19 15:47
 */
public interface SearchService {
    RestResp<PageRespDto<BookInfoRespDto>> searchBooks(BookSearchReqDto bookSearchReqDto) throws IOException;
}
