package com.business.service;

import com.business.common.response.IResult;
import com.business.common.response.IResultUtil;
import com.business.dao.auth.RoleDTORepository;
import com.business.pojo.dto.user.RoleDTO;
import com.business.service.interfaces.auth.AuthService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2018/2/14 21:17
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private RoleDTORepository roleDTORepository;

    @Override
    public IResult queryRoles() {
        return IResultUtil.successResult(roleDTORepository.findAll());
    }

    @Override
    public IResult<String> updateRole(Long roleId, String roleName) {
        RoleDTO roleDTO = roleDTORepository.findOne(roleId);
        if (null == roleDTO) {
            return IResultUtil.errorResult();
        }
        roleDTO.setName(roleName);
        return null == roleDTORepository.saveAndFlush(roleDTO) ?
                IResultUtil.errorResult() : IResultUtil.successResult();
    }

    @Override
    public IResult<String> addRole(String roleName) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(roleName);
        return null == roleDTORepository.save(roleDTO)
                ? IResultUtil.errorResult() : IResultUtil.successResult();
    }
}
