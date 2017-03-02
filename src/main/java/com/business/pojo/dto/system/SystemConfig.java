package com.business.pojo.dto.system;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author yuton
 * @version 1.0
 * @description 系统配置表
 * @since 2017/1/23 17:28
 */
@Data
@Entity
@Table(name = "sys_config")
public class SystemConfig {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Integer id;

    @Column(name = "sys_name", nullable = false)
    private String sysName;

    @Column(name = "sys_key", nullable = false)
    private String sysKey;

    @Column(name = "sys_value", nullable = false)
    private String sysValue;

    @Column(name = "create_time")
    private Timestamp createTime;
}
