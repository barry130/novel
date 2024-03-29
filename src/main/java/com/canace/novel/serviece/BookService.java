package com.canace.novel.serviece;

import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.dto.resp.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 书籍详情服务接口
 * @date 2024/1/17 12:08
 */
public interface BookService {

    RestResp<BookInfoRespDto> getBookInfoById(Long bookId);

    RestResp<Void> addVisitCount(Long bookId);

    RestResp<BookChapterAboutRespDto> getLastChapterAbout(Long bookId);

    RestResp<BookContentAboutRespDto> getBookContentAbout(Long chapterId);

    RestResp<List<BookInfoRespDto>> listRecBooks(Long bookId) throws NoSuchAlgorithmException;

    RestResp<List<BookChapterRespDto>> listBookChapter(Long bookId);

    RestResp<Long> getPreChapterId(Long chapterId);

    RestResp<Long> getNextChapterId(Long chapterId);

    RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection);

    RestResp<BookCommentRespDto> listNewestComments(Long bookId);
}
