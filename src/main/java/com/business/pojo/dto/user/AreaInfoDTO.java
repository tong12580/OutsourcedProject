package com.business.pojo.dto.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

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
    private Integer id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "superior_id")
    private Integer superiorId;
}
