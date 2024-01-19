package com.canace.novel.serviece.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canace.novel.core.common.req.PageReqDto;
import com.canace.novel.core.common.resp.PageRespDto;
import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.dao.entity.BookInfo;
import com.canace.novel.dao.mapper.BookInfoMapper;
import com.canace.novel.dto.req.BookSearchReqDto;
import com.canace.novel.dto.resp.BookInfoRespDto;
import com.canace.novel.serviece.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 搜索服务实现类(从Mysql中读取数据)
 * @date 2024/1/19 15:48
 */

@Service
@RequiredArgsConstructor
//表示只有spring.elasticsearch.enabled=false时才会加载该类
@ConditionalOnProperty(prefix = "spring.elasticsearch", name = "enabled", havingValue = "false")
public class DBSearchServiceImpl implements SearchService {

    private final BookInfoMapper bookInfoMapper;
    @Override
    public RestResp<PageRespDto<BookInfoRespDto>> searchBooks(BookSearchReqDto bookSearchReqDto) {
        // 直接从数据库进行分页查询
        IPage<BookInfo> page = new Page<>();
        page.setCurrent(bookSearchReqDto.getPageNum());
        page.setSize(bookSearchReqDto.getPageSize());
        List<BookInfo> bookInfos = bookInfoMapper.searchBooks(page, bookSearchReqDto);
        // 将bookInfos转换为BookInfoRespDto
         List<BookInfoRespDto> bookInfoRespDto = bookInfos.stream().map(bookInfo -> {
             BookInfoRespDto bookInfoRespDto1 = new BookInfoRespDto();
             BeanUtils.copyProperties(bookInfo, bookInfoRespDto1);
             return bookInfoRespDto1;
         }).toList();
        return RestResp.ok(new PageRespDto<>(page.getCurrent(), page.getSize(), page.getTotal(), bookInfoRespDto));
    }
}
