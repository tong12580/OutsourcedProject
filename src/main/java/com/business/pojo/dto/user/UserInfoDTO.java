package com.business.pojo.dto.user;

import com.business.pojo.dto.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yutong
 * @version 1.0
 * @description 用户信息表
 * @since 2018/2/14 18:10
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_info", indexes = {@Index(name = "user_id", columnList = "user_id")})
public class UserInfoDTO extends BaseEntity {
    @Column(name = "user_id", unique = true)
    private Long userId;//用户ID

    @Column(name = "company_name")
    private String companyName;//公司名称

    @Column(name = "industry_type")
    private String industryType;//行业类型

    private String province;//省;

    private String city;//市;

    private String county;//区;

    @Column(name = "postal_code")
    private String postalCode;//邮政编码；

    private String phone;

    private String fax;//传真
}
