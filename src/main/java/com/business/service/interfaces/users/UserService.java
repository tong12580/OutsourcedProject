package com.business.service.interfaces.users;

import com.business.common.response.IResult;
import com.business.pojo.dto.user.UserDTO;

/**
 * @author yuton
 * @version 1.0
 * @description
 * @since 2017/1/23 18:02
 */
public interface UserService {
    IResult<Object> userRegister(UserDTO userDTO);
}
