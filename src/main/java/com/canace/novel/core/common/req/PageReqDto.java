package com.canace.novel.core.common.req;

import lombok.Data;

/**
 * @version 1.0
 * @description 分页请求数据格式封装，所有分页请求的Dto类都继承该类
 * @author canace
 * @date 2023/11/25 15:38
 */

@Data
public class PageReqDto {

    /**
     * 请求页码，默认第一页
     */
    private int pageNum = 1;

    /**
     * 每页大小，默认每页10条数据
     */
    private int pageSize = 10;

    /**
     * 是否查询所有，默认不查所有
     * 为true时，pageNum和pageSize不生效
     */
    private boolean fetchAll = false;
}
