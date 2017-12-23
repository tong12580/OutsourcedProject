package com.business.pojo.dto.commodity;

import com.business.pojo.dto.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author yutong
 * @version 1.0
 * @description 支付流水信息表
 * @since 2017/1/30 20:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "commodity_pay")
public class CommodityPayDTO extends BaseEntity {
    @Column(name = "commodity_id")
    private Integer commodityId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "commodity_name")
    private String commodityName;

    @Column(name = "commodity_num")
    private Integer commodityNum;

    @Column(name = "commodity_amount")
    private BigDecimal commodityAmount;

    @Column(name = "coupon_amount")
    private BigDecimal couponAmount;

    @Column(name = "coupon_id")
    private Integer couponId;

    @Column(name = "coupon_type")
    private Integer couponType;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "courier_name")
    private String courierName;

    @Column(name = "courier_fees")
    private BigDecimal courierFees;

    @Column(name = "courier_code")
    private String courierCode;

    @Column(name = "pay_amount")
    private BigDecimal payAmount;

    @Column(name = "pay_channel")
    private Integer payChannel;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "pay_time")
    private Timestamp payTime;
}
