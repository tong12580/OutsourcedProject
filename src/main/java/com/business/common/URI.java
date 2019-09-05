package com.business.common;

/**
 * 链接地址
 * URI
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-9-5 14:51
 */
public interface URI {

    String VERSION_NUMBER_1 = "/v1";
    String INTERFACE_TYPE_API = "/api";
    String INTERFACE_TYPE_ADMIN = "/admin";

    /** api **/
    /* 注册 */
    String REGISTERED = "/registered";
    String REFRESH_TOKEN = "/refreshToken";

    /* 城市 */
    String PROVINCES = "provinces";
    String SUBORDINATE_ADMINISTRATIVE_UNITS = "subordinateAdministrativeUnits";

    /** admin **/
    /* 权限 */
    String ROLE = "/role";
    String ROLES = "/roles";
    String USERS = "/users";

}
