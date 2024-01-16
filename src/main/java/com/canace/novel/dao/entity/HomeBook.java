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
 * 小说推荐
 * </p>
 *
 * @author ${author}
 * @date 2024/01/15
 */
@Getter
@Setter
@TableName("home_book")
public class HomeBook implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 推荐类型;0-轮播图 1-顶部栏 2-本周强推 3-热门推荐 4-精品推荐
     */
    private Byte type;

    /**
     * 推荐排序
     */
    private Byte sort;

    /**
     * 推荐小说ID
     */
    private Long bookId;

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
        return "HomeBook{" +
        "id=" + id +
        ", type=" + type +
        ", sort=" + sort +
        ", bookId=" + bookId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
