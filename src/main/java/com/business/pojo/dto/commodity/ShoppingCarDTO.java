package com.business.pojo.dto.commodity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author yutong
 * @version 1.0
 * @description 购物车
 * @since 2017/1/30 21:12
 */
@Data
@Entity
@Table(name = "shopping_car")
public class ShoppingCarDTO {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column(name = "commodity_id")
    private Integer commodityId;

    @Column(name = "commodity_name")
    private String commodityName;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "num")
    private Integer num;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "create_time")
    private Timestamp createTime;
}
