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
 * 小说评论
 * </p>
 *
 * @author ${author}
 * @date 2024/01/15
 */
@Setter
@Getter
@TableName("book_comment")
public class BookComment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论小说ID
     */
    private Long bookId;

    /**
     * 评论用户ID
     */
    private Long userId;

    /**
     * 评价内容
     */
    private String commentContent;

    /**
     * 回复数量
     */
    private Integer replyCount;

    /**
     * 审核状态;0-待审核 1-审核通过 2-审核不通过
     */
    private Byte auditStatus;

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
        return "BookComment{" +
        "id=" + id +
        ", bookId=" + bookId +
        ", userId=" + userId +
        ", commentContent=" + commentContent +
        ", replyCount=" + replyCount +
        ", auditStatus=" + auditStatus +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
