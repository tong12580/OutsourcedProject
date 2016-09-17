package com.business.pojo.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by yuton on 2016/9/13.
 */
@Data
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Integer id;

    @Column(name = "login_info_id", nullable = false)
    private Integer loginInfoId;

    @Column(name = "user_name", nullable = false, length = 6)
    private String userName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "sex", nullable = false)
    private Byte sex;

    @Column(name = "id_number", nullable = true, length = 18)
    private String idNumber;

    @Column(name = "type", nullable = false)
    private Byte type;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @Column(name = "update_date", nullable = false)
    private Timestamp updateDate;

}
