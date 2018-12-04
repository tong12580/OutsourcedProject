package com.business.pojo.dto.user;

import com.business.pojo.dto.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 用户信息
 *
 * @author yutong
 * @version 1.0
 * @since 2018/2/14 18:10
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_info", indexes = {@Index(name = "user_id", columnList = "user_id")})
public class UserInfoDTO extends BaseEntity {
    @ApiModelProperty("用户ID")
    @Column(name = "user_id", unique = true)
    private Long userId;

    @ApiModelProperty("公司名称")
    @Column(name = "company_name")
    private String companyName;

    @ApiModelProperty("行业类型")
    @Column(name = "industry_type")
    private String industryType;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String county;

    @ApiModelProperty("邮政编码")
    @Column(name = "postal_code")
    private String postalCode;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("传真")
    private String fax;
}
