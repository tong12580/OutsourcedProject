package com.business.pojo.enums;

import lombok.Getter;

/**
 * @author yutong
 * @version 1.0
 * @description 权限对照枚举
 * @since 2018/2/22 21:17
 */
public enum RoleEnum {

    ROLE_ADMIN(1, "管理员"),
    ROLE_USER(2, "普通用户"),
    ROLE_LIMIT(3, "受限用户"),
    ROLE_READ_ONLY(4, "只读用户"),
    ROLE_ROOT(5, "超级用户");

    @Getter
    private String roleName;
    @Getter
    private Integer id;

    RoleEnum(Integer id, String role) {
        this.id = id;
        this.roleName = role;
    }

}
