package com.business.pojo.dto.user;

import com.business.pojo.dto.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * MessageDTO
 * Created by yuTong on 2018/03/28.
 */
@Data
@Entity
@Table(name = "message")
@EqualsAndHashCode(callSuper = true)
public class MessageDTO extends BaseEntity {
    private String title;
    private String content;
    private String extraInfo;
    private Integer type;
}
