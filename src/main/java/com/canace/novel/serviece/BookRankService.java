package com.canace.novel.serviece;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.dto.resp.BookRankRespDto;

import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 书籍排行服务类
 * @date 2024/1/16 15:26
 */
public interface BookRankService {

    RestResp<List<BookRankRespDto>> listBookRankByVisit();

    RestResp<List<BookRankRespDto>> listBookRankByCreateTime();

    RestResp<List<BookRankRespDto>> listBookRankByUpdateTime();
}
