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
 * 新闻信息
 * </p>
 *
 * @author ${author}
 * @date 2024/01/15
 */
@Setter
@Getter
@TableName("news_info")
public class NewsInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类别ID
     */
    private Long categoryId;

    /**
     * 类别名
     */
    private String categoryName;

    /**
     * 新闻来源
     */
    private String sourceName;

    /**
     * 新闻标题
     */
    private String title;

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
        return "NewsInfo{" +
        "id=" + id +
        ", categoryId=" + categoryId +
        ", categoryName=" + categoryName +
        ", sourceName=" + sourceName +
        ", title=" + title +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
