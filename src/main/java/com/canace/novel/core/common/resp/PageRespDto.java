package com.canace.novel.core.common.resp;

import lombok.Getter;

import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 分页响应数据格式封装
 * @date 2023/11/25 15:42
 */

@Getter
public class PageRespDto<T> {
    //页码
    private final long pageNum;

    //每页记录数
    private final long pageSize;

    //总记录数
    private final long total;

    //分页数据集
    private final List<? extends T> list;


    public PageRespDto(long pageNum, long pageSize, long total, List<? extends T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    public static <T> PageRespDto<T> of(long pageNum, long pageSize, long total, List<? extends T> list) {
        return new PageRespDto<>(pageNum, pageSize, total, list);
    }

    /**
     * 获取分页数
     */
    public long getPages() {
        if (this.pageSize == 0L) {
            return 0L;
        } else {
            long pages = this.total / this.pageSize;
            if (this.total % this.pageSize != 0L) {
                ++pages;
            }

            return pages;

        }
    }
}
