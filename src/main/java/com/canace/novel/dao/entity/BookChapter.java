package com.canace.novel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 小说章节
 * </p>
 *
 * @author ${author}
 * @date 2024/01/15
 */
@Setter
@Getter
@TableName("book_chapter")
public class BookChapter implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 小说ID
     */
    private Long bookId;

    /**
     * 章节号
     */
    private Short chapterNum;

    /**
     * 章节名
     */
    private String chapterName;

    /**
     * 章节字数
     */
    private Integer wordCount;

    /**
     * 是否收费;1-收费 0-免费
     */
    private Byte isVip;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    public String toString() {
        return "BookChapter{" +
        "id=" + id +
        ", bookId=" + bookId +
        ", chapterNum=" + chapterNum +
        ", chapterName=" + chapterName +
        ", wordCount=" + wordCount +
        ", isVip=" + isVip +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
