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
 * @description 用户表
 * @since 2017/1/23 16:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user")
public class UserDTO extends BaseEntity {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "ip")
    private String ip;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "invite_code")
    private String inviteCode;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "openid")
    private Boolean openid;
}
