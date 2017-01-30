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
 * @description 商品表
 * @since 2017/1/30 20:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "commodity")
public class CommodityDTO extends BaseEntity {

    @Column(name = "commodity_name")
    private String commodityName;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "inventory")
    private Integer inventory;

    @Column(name = "reserve")
    private Integer reserve;

    @Column(name = "sell")
    private Integer sell;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "commodity_doc")
    private String commodityDoc;

    @Column(name = "long_text")
    private String longText;

    @Column(name = "picture_one")
    private String picture_one;

    @Column(name = "picture_two")
    private String picture_two;

    @Column(name = "picture_tree")
    private String picture_tree;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "coupon_type_name")
    private String couponTypeName;

    @Column(name = "coupon_type_id")
    private Integer couponTypeId;
}
