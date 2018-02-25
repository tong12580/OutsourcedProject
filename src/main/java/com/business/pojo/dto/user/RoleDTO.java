package com.business.pojo.dto.user;

import com.business.pojo.dto.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yuton
 * @version 1.0
 * @description com.example.demo.pojo
 * @since 上午10:19 2017/12/25
 */

@Data
@Entity
@Table(name = "role")
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends BaseEntity {
    @ApiModelProperty("权限名称")
    private String name;
}