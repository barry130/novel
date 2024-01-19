package com.canace.novel.serviece.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.json.JsonData;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.canace.novel.core.common.resp.PageRespDto;
import com.canace.novel.core.common.resp.RestResp;
import com.canace.novel.core.constant.EsConsts;
import com.canace.novel.dao.entity.BookInfo;
import com.canace.novel.dao.mapper.BookInfoMapper;
import com.canace.novel.dto.es.EsBookDto;
import com.canace.novel.dto.req.BookSearchReqDto;
import com.canace.novel.dto.resp.BookInfoRespDto;
import com.canace.novel.serviece.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author canace
 * @version 1.0
 * @description 搜索服务实现类(从elasticsearch中读取数据)
 * @date 2024/1/19 17:42
 */

@Service
@RequiredArgsConstructor
//表示只有spring.elasticsearch.enabled=true时才会加载该类
@ConditionalOnProperty(prefix = "spring.elasticsearch", name = "enabled", havingValue = "true")
@Slf4j
public class ESSearchServiceImpl implements SearchService {

    private final ElasticsearchClient elasticsearchClient;

    private final BookInfoMapper bookInfoMapper;

    @Override
    public RestResp<PageRespDto<BookInfoRespDto>> searchBooks(BookSearchReqDto bookSearchReqDto) throws IOException {
        // 第一次将数据库的书籍信息保存到elasticsearch中
//        List<BookInfo> bookInfos = bookInfoMapper.selectList(null);
//
//        // 指定索引
//        BulkRequest.Builder builder = new BulkRequest.Builder().index(EsConsts.BookIndex.INDEX_NAME);
//
//        List<EsBookDto> list = bookInfos.stream().map(v -> EsBookDto.builder().id(v.getId())
//                .bookName(v.getBookName())
//                .categoryId(v.getCategoryId())
//                .categoryName(v.getCategoryName())
//                .authorId(v.getAuthorId())
//                .authorName(v.getAuthorName())
//                .wordCount(v.getWordCount())
//                .lastChapterName(v.getLastChapterName())
//                .isVip(Integer.valueOf(v.getIsVip()))
//                .bookDesc(v.getBookDesc())
//                .bookStatus(Integer.valueOf(v.getBookStatus()))
//                .score(Integer.valueOf(v.getScore()))
//                .workDirection(Integer.valueOf(v.getWorkDirection()))
//                .lastChapterId(v.getLastChapterId())
//                .lastChapterUpdateTime(v.getLastChapterUpdateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
//                .commentCount(v.getCommentCount())
//                .visitCount(v.getVisitCount())
//                .build()).toList();
//
//        for (var product : list) {
//            builder.operations(op -> op.index(in -> in.id(product.getId().toString()).document(product)));
//        }
//        // 运行批量操作
//        BulkResponse bulk = elasticsearchClient.bulk(builder.build());


        SearchResponse<EsBookDto> search = elasticsearchClient.search(s -> {
            SearchRequest.Builder searchBuilder = s.index(EsConsts.BookIndex.INDEX_NAME);
            // 构建查询条件
            buildSearchCondition(bookSearchReqDto, searchBuilder);

            // 对结果进行排序
            if (!StringUtils.isBlank(bookSearchReqDto.getSort())) {
                searchBuilder.sort(o -> o.field(f -> f.field(StringUtils.underlineToCamel(bookSearchReqDto.getSort().split(" ")[0])).order(SortOrder.Desc)));
            }

            // 分页
            searchBuilder.from((bookSearchReqDto.getPageNum() - 1) * bookSearchReqDto.getPageSize()).size(bookSearchReqDto.getPageSize());

            // 设置高亮显示
            searchBuilder.highlight(h -> h.fields(EsConsts.BookIndex.FIELD_BOOK_NAME, t -> t.preTags("<em style='color:red'>").postTags("</em>"))
                    .fields(EsConsts.BookIndex.FIELD_AUTHOR_NAME, t -> t.preTags("<em style='color:red'>").postTags("</em>")));

            return searchBuilder;
        }, EsBookDto.class);

        // 封装返回结果
        // 总查询数据
        TotalHits total = search.hits().total();

        ArrayList<BookInfoRespDto> bookInfoRespDto = new ArrayList<>();
        List<Hit<EsBookDto>> hits = search.hits().hits();

        // 提取hits的数据
        for (var hit : hits) {
            EsBookDto source = hit.source();
            assert source != null;
            if (!CollectionUtils.isEmpty(hit.highlight().get(EsConsts.BookIndex.FIELD_BOOK_NAME))) {
                source.setBookName(hit.highlight().get(EsConsts.BookIndex.FIELD_BOOK_NAME).get(0));
            }
            if (!CollectionUtils.isEmpty(hit.highlight().get(EsConsts.BookIndex.FIELD_AUTHOR_NAME))) {
                source.setBookName(hit.highlight().get(EsConsts.BookIndex.FIELD_AUTHOR_NAME).get(0));
            }

            BookInfoRespDto bookInfoRespDto1 = new BookInfoRespDto();
            BeanUtils.copyProperties(source, bookInfoRespDto1);

            bookInfoRespDto.add(bookInfoRespDto1);
        }

        assert total != null;
        return RestResp.ok(PageRespDto.of(bookSearchReqDto.getPageNum(), bookSearchReqDto.getPageSize(), total.value(), bookInfoRespDto));
    }

    /**
     * 构建检索条件
     */
    private void buildSearchCondition(BookSearchReqDto condition, SearchRequest.Builder searchBuilder) {
        BoolQuery boolQuery = BoolQuery.of(b -> {
            // 只查找有字数的
            b.must(RangeQuery.of(m -> m.field(EsConsts.BookIndex.FIELD_WORD_COUNT).gt(JsonData.of(0)))._toQuery());

            // 如果关键字不为空就通过关键字查询,可以通过书名、作者名和书籍描述进行查询
            if (!StringUtils.isBlank(condition.getKeyword())) {
                b.must((q -> q.multiMatch(t -> t.fields(EsConsts.BookIndex.FIELD_BOOK_NAME + "^2", EsConsts.BookIndex.FIELD_AUTHOR_NAME + "^1.8", EsConsts.BookIndex.FIELD_BOOK_DESC + "^0.1").query(condition.getKeyword()))));
            }

            // 精确查询
            if (Objects.nonNull(condition.getWorkDirection())) {
                b.must(TermQuery.of(m -> m.field(EsConsts.BookIndex.FIELD_WORK_DIRECTION).value(condition.getWorkDirection()))._toQuery());
            }

            // 根据书籍分类查询
            if (Objects.nonNull(condition.getCategoryId())) {
                b.must(TermQuery.of(m -> m.field(EsConsts.BookIndex.FIELD_CATEGORY_ID).value(condition.getCategoryId()))._toQuery());
            }

            // 根据字数范围查询
            if (Objects.nonNull(condition.getWordCountMin())) {
                b.must(RangeQuery.of(m -> m.field(EsConsts.BookIndex.FIELD_WORD_COUNT).gte(JsonData.of(condition.getWordCountMin())))._toQuery());
            }
            if (Objects.nonNull(condition.getWordCountMax())) {
                b.must(RangeQuery.of(m -> m.field(EsConsts.BookIndex.FIELD_WORD_COUNT).lt(JsonData.of(condition.getWordCountMax())))._toQuery());
            }

            // 根据更新时间范围查询
            if (Objects.nonNull(condition.getUpdateTimeMin())) {
                b.must(RangeQuery.of(m -> m.field(EsConsts.BookIndex.FIELD_LAST_CHAPTER_UPDATE_TIME).lt(JsonData.of(condition.getUpdateTimeMin().getTime())))._toQuery());
            }

            return b;
        });

        searchBuilder.query(q -> q.bool(boolQuery));
    }

}
