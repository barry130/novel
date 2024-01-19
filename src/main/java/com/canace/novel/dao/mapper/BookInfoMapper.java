package com.canace.novel.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.canace.novel.dao.entity.BookInfo;
import com.canace.novel.dto.req.BookSearchReqDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 小说信息 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @date 2024/01/15
 */
public interface BookInfoMapper extends BaseMapper<BookInfo> {

    void addVisitCount(Long bookId);

    // 传入page信息，Mybatis-plus会自动进行分页查询
    List<BookInfo> searchBooks(IPage<BookInfo> page, @Param("bookSearchReqDto") BookSearchReqDto bookSearchReqDto);
}
