package com.business.pojo.dto.user;

import com.business.pojo.dto.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yuton
 * @version 1.0
 * @description com.example.demo.pojo
 * @since 上午10:19 2017/12/25
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {
    private String name;
}