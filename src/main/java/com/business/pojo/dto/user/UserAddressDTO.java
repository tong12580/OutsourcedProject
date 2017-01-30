package com.business.pojo.dto.user;

import com.business.pojo.dto.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author yutong
 * @version 1.0
 * @description 收货地址表
 * @since 2017/1/30 19:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_address")
public class UserAddressDTO extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "recipient_phone", nullable = false)
    private String recipientPhone;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "county", nullable = false)
    private String county;

    @Column(name = "specific_address", nullable = false)
    private String specificAddress;

    @Column(name = "status", nullable = false)
    private Boolean status;
}
