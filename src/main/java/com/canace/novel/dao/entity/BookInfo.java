package com.canace.novel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 小说信息
 * </p>
 *
 * @author ${author}
 * @date 2024/01/15
 */
@Setter
@Getter
@TableName("book_info")
public class BookInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 作品方向;0-男频 1-女频
     */
    private Byte workDirection;

    /**
     * 类别ID
     */
    private Long categoryId;

    /**
     * 类别名
     */
    private String categoryName;

    /**
     * 小说封面地址
     */
    private String picUrl;

    /**
     * 小说名
     */
    private String bookName;

    /**
     * 作家id
     */
    private Long authorId;

    /**
     * 作家名
     */
    private String authorName;

    /**
     * 书籍描述
     */
    private String bookDesc;

    /**
     * 评分;总分:10 ，真实评分 = score/10
     */
    private Byte score;

    /**
     * 书籍状态;0-连载中 1-已完结
     */
    private Byte bookStatus;

    /**
     * 点击量
     */
    private Long visitCount;

    /**
     * 总字数
     */
    private Integer wordCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 最新章节ID
     */
    private Long lastChapterId;

    /**
     * 最新章节名
     */
    private String lastChapterName;

    /**
     * 最新章节更新时间
     */
    private LocalDateTime lastChapterUpdateTime;

    /**
     * 是否收费;1-收费 0-免费
     */
    private Byte isVip;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    @Override
    public String toString() {
        return "BookInfo{" +
        "id=" + id +
        ", workDirection=" + workDirection +
        ", categoryId=" + categoryId +
        ", categoryName=" + categoryName +
        ", picUrl=" + picUrl +
        ", bookName=" + bookName +
        ", authorId=" + authorId +
        ", authorName=" + authorName +
        ", bookDesc=" + bookDesc +
        ", score=" + score +
        ", bookStatus=" + bookStatus +
        ", visitCount=" + visitCount +
        ", wordCount=" + wordCount +
        ", commentCount=" + commentCount +
        ", lastChapterId=" + lastChapterId +
        ", lastChapterName=" + lastChapterName +
        ", lastChapterUpdateTime=" + lastChapterUpdateTime +
        ", isVip=" + isVip +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
