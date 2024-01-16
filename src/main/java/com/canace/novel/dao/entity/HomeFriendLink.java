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
 * 友情链接
 * </p>
 *
 * @author ${author}
 * @date 2024/01/15
 */
@Setter
@Getter
@TableName("home_friend_link")
public class HomeFriendLink implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 链接url
     */
    private String linkUrl;

    /**
     * 排序号
     */
    private Byte sort;

    /**
     * 是否开启;0-不开启 1-开启
     */
    private Byte isOpen;

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
        return "HomeFriendLink{" +
        "id=" + id +
        ", linkName=" + linkName +
        ", linkUrl=" + linkUrl +
        ", sort=" + sort +
        ", isOpen=" + isOpen +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
