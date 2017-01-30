package com.business.pojo.dto.coupon;

import com.business.pojo.dto.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author yutong
 * @version 1.0
 * @description 用户优惠券表
 * @since 2017/1/30 19:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "coupon")
public class CouponDTO extends BaseEntity {

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "coupon_amount")
    private String couponAmount;

    @Column(name = "coupon_type")
    private String couponType;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "user_id")
    private Integer userId;
}
