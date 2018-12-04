package com.business.pojo.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;

/**
 * 实体公共属性
 *
 * @author yutong
 * @version 1.0
 * @since 2017/1/30 19:29
 */
@MappedSuperclass
@Data
public class BaseEntity {
    private static final Timestamp DELETE_AT = Timestamp.valueOf("1970-01-01 00:00:00.0");

    /**
     * 主键
     *
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     *
     */
    @Column(name = "create_time", nullable = false)
    private Timestamp createTime;

    /**
     * 更新时间
     *
     */
    @Column(name = "update_time")
    private Timestamp updateTime;

    @PrePersist
    void onCreate() {
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.updateTime = this.createTime;
    }

    @PreUpdate
    void onUpdate() {
        this.updateTime = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
