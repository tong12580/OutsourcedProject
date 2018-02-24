package com.business.service;

import com.business.common.message.ResultMessage;
import com.business.common.response.IResult;
import com.business.common.response.IResultUtil;
import com.business.dao.auth.RoleDTORepository;
import com.business.dao.users.UserDTORepository;
import com.business.pojo.dto.user.RoleDTO;
import com.business.pojo.dto.user.UserDTO;
import com.business.pojo.enums.RoleEnum;
import com.business.service.interfaces.auth.AccessService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * AccessServiceImpl
 * Created by yuTong on 20180223.
 */
@Service
public class AccessServiceImpl implements AccessService {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserDTORepository userDTORepository;
    @Resource
    private RoleDTORepository roleDTORepository;


    @Override
    public IResult<String> registered(String username, String password, String role) {
        UserDTO userDTO = new UserDTO();
        if (StringUtils.isBlank(role)) {
            role = RoleEnum.ROLE_USER.name();
        }
        RoleDTO r = roleDTORepository.getByName(role);
        if (null == r) {
            return IResultUtil.errorResult(ResultMessage.ERROR_PROMPT, "不存在该权限名称");
        }
        userDTO.setRoleDTO(r);
        userDTO.setPassword(passwordEncoder.encode(password));
        userDTO.setUsername(username);
        if (null == userDTORepository.save(userDTO)) {
            return IResultUtil.errorResult(ResultMessage.DATABASE_ABNORMAL, "userDTO");
        }
        return IResultUtil.successResult();
    }
}
