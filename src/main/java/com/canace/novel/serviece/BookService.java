package com.canace.novel.serviece;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.dto.resp.BookInfoRespDto;

/**
 * @author canace
 * @version 1.0
 * @description 书籍详情服务接口
 * @date 2024/1/17 12:08
 */
public interface BookService {

    RestResp<BookInfoRespDto> getBookInfoById(Long bookId);

    RestResp<Void> addVisitCount(Long bookId);
}
