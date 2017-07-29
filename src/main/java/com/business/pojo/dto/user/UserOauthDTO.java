package com.business.pojo.dto.user;

import com.business.pojo.dto.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "expires_in")
    private Long expiresIn;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "scope")
    private Integer scope;

    @Transient
    private String phone;
}
