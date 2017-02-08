package com.business.pojo.dto.user;

import com.business.pojo.dto.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author yuton
 * @version 1.0
 * @description 用户权限表
 * @since 2017/2/8 11:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_oauth")
public class UserOauthDTO extends BaseEntity {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "token")
    private String token;

    @Column(name = "type")
    private Integer type;

    @Column(name = "valid_time")
    private Integer validTime;

    @Column(name = "status")
    private Boolean status;
}
