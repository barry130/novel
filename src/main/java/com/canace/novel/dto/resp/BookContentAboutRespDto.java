package com.canace.novel.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author canace
 * @version 1.0
 * @description 书籍章节内容响应DTO
 * @date 2024/1/17 17:44
 */
@Data
public class BookContentAboutRespDto {
    /**
     * 小说信息
     */
    @Schema(description = "小说信息")
    private BookInfoRespDto bookInfo;

    /**
     * 章节信息
     */
    @Schema(description = "章节信息")
    private BookChapterRespDto chapterInfo;

    /**
     * 章节内容
     */
    @Schema(description = "章节内容")
    private String bookContent;
}
