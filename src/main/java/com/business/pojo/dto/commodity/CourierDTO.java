package com.business.pojo.dto.commodity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author yutong
 * @version 1.0
 * @description 物流信息
 * @since 2017/1/30 21:17
 */
@Data
@Entity
@Table(name = "courier")
public class CourierDTO {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column(name = "courier_code")
    private String courierCode;
}
