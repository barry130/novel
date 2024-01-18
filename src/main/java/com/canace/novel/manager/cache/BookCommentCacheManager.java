package com.canace.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canace.novel.core.common.req.PageReqDto;
import com.canace.novel.core.constant.CacheConsts;
import com.canace.novel.core.constant.DatabaseConsts;
import com.canace.novel.dao.entity.BookComment;
import com.canace.novel.dao.entity.UserInfo;
import com.canace.novel.dao.mapper.BookCommentMapper;
import com.canace.novel.dao.mapper.UserInfoMapper;
import com.canace.novel.dto.resp.BookCommentRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author canace
 * @version 1.0
 * @description 书籍评论缓存管理
 * @date 2024/1/18 20:10
 */

@Component
@RequiredArgsConstructor
public class BookCommentCacheManager {

    private final BookCommentMapper bookCommentMapper;

    private final UserInfoMapper userInfoMapper;

    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, cacheNames = CacheConsts.BOOK_COMMENT_CACHE_NAME)
    public BookCommentRespDto listComments(Long bookId, PageReqDto pageReqDto) {
        // 查询当前书籍ID的评论总数
        QueryWrapper<BookComment> bookCommentQueryWrapper = new QueryWrapper<>();
        bookCommentQueryWrapper.eq(DatabaseConsts.BookCommentTable.COLUMN_BOOK_ID, bookId);
        Long l = bookCommentMapper.selectCount(bookCommentQueryWrapper);
        // 这里不需要判断l是否为0，因此有可能书籍是没有评论的
        // 先把书籍评论总数封装入返回结果中
        BookCommentRespDto bookCommentRespDto = new BookCommentRespDto();
        bookCommentRespDto.setCommentTotal(l);

        // 设置页面参数
        IPage<BookComment> page = new Page<>();
        page.setCurrent(pageReqDto.getPageNum());
        page.setSize(pageReqDto.getPageSize());
        long l1 = (page.getCurrent() - 1) * page.getSize();
        //如果l>需要的评论数 就读取评论
        if (l > l1) {
            // 分页查询
            QueryWrapper<BookComment> bookCommentQueryWrapper1 = new QueryWrapper<>();
            bookCommentQueryWrapper1.eq(DatabaseConsts.BookCommentTable.COLUMN_BOOK_ID, bookId).orderByDesc(DatabaseConsts.CommonColumnEnum.UPDATE_TIME.getName());
            IPage<BookComment> bookCommentPage = bookCommentMapper.selectPage(page, bookCommentQueryWrapper1);
            // 取出记录
            List<BookComment> records = bookCommentPage.getRecords();
            if (CollectionUtils.isEmpty(records)) {
                return null;
            }

            // 需要根据用户ID获取用户信息，这里就单独进行查询
            // 将记录中的所有用户取出来
            List<Long> userIdList = records.stream().map(BookComment::getUserId).toList();
            // 通过用户ID查询用户信息
            QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
            userInfoQueryWrapper.in(DatabaseConsts.CommonColumnEnum.ID.getName(),userIdList);
            List<UserInfo> userInfos = userInfoMapper.selectList(userInfoQueryWrapper);
            // 将用户ID放入Map中，方便后面查找
            Map<Long, UserInfo> collect = userInfos.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));

            // 将记录封装至返回结果中
            List<BookCommentRespDto.CommentInfo> list = records.stream().map(v -> {
                BookCommentRespDto.CommentInfo commentInfo = new BookCommentRespDto.CommentInfo();
                commentInfo.setCommentContent(v.getCommentContent());
                commentInfo.setId(v.getId());
                commentInfo.setCommentTime(v.getUpdateTime());
                commentInfo.setCommentUserId(v.getUserId());
                commentInfo.setCommentContent(v.getCommentContent());
                // 根据用户ID获取用户信息,从map中查找
                UserInfo userInfo = collect.get(v.getUserId());
                commentInfo.setCommentUser(userInfo.getUsername());
                commentInfo.setCommentUserPhoto(userInfo.getUserPhoto());
                return commentInfo;
            }).toList();

            bookCommentRespDto.setComments(list);
        }
        return bookCommentRespDto;

    }
}
