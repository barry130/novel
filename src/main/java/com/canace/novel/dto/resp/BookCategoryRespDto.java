package com.canace.novel.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author canace
 * @version 1.0
 * @description 书籍分类列表响应DTO
 * @date 2024/1/18 17:43
 */

@Data
public class BookCategoryRespDto implements Serializable {

    /**
     * 类别ID
     */
    @Schema(description = "类别ID")
    private Long id;

    /**
     * 类别名
     */
    @Schema(description = "类别名")
    private String name;
}
