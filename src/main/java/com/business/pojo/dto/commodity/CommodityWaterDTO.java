package com.business.pojo.dto.commodity;

import com.business.pojo.dto.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author yutong
 * @version 1.0
 * @description 订单流水表
 * @since 2017/1/30 20:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "commodity_water")
public class CommodityWaterDTO extends BaseEntity {

    @Column(name = "water_code")
    private String waterCode;

    @Column(name = "commodity_id")
    private Integer commodityId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "num")
    private Integer num;

    @Column(name = "courier_name")
    private String courierName;

    @Column(name = "courier_fees")
    private BigDecimal courierFees;

    @Column(name = "total_prices")
    private BigDecimal totalPrices;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "pay_status")
    private Integer payStatus;
}
