package com.business.pojo.dto.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2018/2/25 00:15
 */
@Data
@Entity
@Table(name = "area_info", indexes = @Index(name = "superior_id", columnList = "superior_id"))
public class AreaInfoDTO {

    @Id
    @ApiModelProperty("行政区划ID")
    private Integer id;

    @ApiModelProperty("行政区划名称")
    @Column(name = "name", length = 20)
    private String name;

    @ApiModelProperty("上级行政区划ID")
    @Column(name = "superior_id")
    private Integer superiorId;
}
