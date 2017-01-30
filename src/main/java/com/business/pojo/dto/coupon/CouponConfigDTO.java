package com.business.pojo.dto.coupon;

import com.business.pojo.dto.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author yutong
 * @version 1.0
 * @description 优惠券配置表
 * @since 2017/1/30 20:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "coupon_config")
public class CouponConfigDTO extends BaseEntity {

    @Column(name = "coupon_type_name")
    private String couponTypeName;

    @Column(name = "context")
    private String context;

    @Column(name = "status")
    private String status;

    @Column(name = "end_time")
    private Timestamp endTime;
}
