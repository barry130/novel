package com.canace.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canace.novel.core.constant.CacheConsts;
import com.canace.novel.core.constant.DatabaseConsts;
import com.canace.novel.dao.entity.HomeFriendLink;
import com.canace.novel.dao.mapper.HomeFriendLinkMapper;
import com.canace.novel.dto.resp.HomeFriendLinkRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 首页友链缓存管理器
 * @date 2024/1/16 16:13
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class HomeFriendLinkCacheManager {

    private final HomeFriendLinkMapper homeFriendLinkMapper;

    /**
     * 首页友链管理
     */
    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER, value = CacheConsts.HOME_FRIEND_LINK_CACHE_NAME)
    public List<HomeFriendLinkRespDto> listHomeFriendLink() {
        QueryWrapper<HomeFriendLink> bookInfoQueryWrapper = new QueryWrapper<>();
        // 根据sort排序
        bookInfoQueryWrapper.orderByAsc(DatabaseConsts.CommonColumnEnum.SORT.getName());
        // 查询list结果
        List<HomeFriendLink> homeFriendLinks = homeFriendLinkMapper.selectList(bookInfoQueryWrapper);
        // 封装返回结果
        if (!CollectionUtils.isEmpty(homeFriendLinks)) {
            return homeFriendLinks.stream().map(homeFriendLink -> {
                HomeFriendLinkRespDto homeFriendLinkRespDto = new HomeFriendLinkRespDto();
                BeanUtils.copyProperties(homeFriendLink, homeFriendLinkRespDto);
                return homeFriendLinkRespDto;
            }).toList();
        }
        log.error("获取友链列表失败，结果为空");
        return Collections.emptyList();
    }
}
