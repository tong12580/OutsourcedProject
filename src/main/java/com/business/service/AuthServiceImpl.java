package com.business.service;

import com.business.common.response.IResult;
import com.business.common.response.IResultUtil;
import com.business.dao.auth.RoleDTORepository;
import com.business.dao.users.UserDTORepository;
import com.business.pojo.dto.user.RoleDTO;
import com.business.pojo.dto.user.UserDTO;
import com.business.service.interfaces.auth.AuthService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yutong
 * @version 1.0
 * @since 2018/2/14 21:17
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private RoleDTORepository roleDTORepository;
    @Resource
    private UserDTORepository userDTORepository;

    @Override
    public IResult queryRoles() {
        return IResultUtil.successResult(roleDTORepository.findAll());
    }

    @Override
    public IResult<String> updateRole(Long roleId, String roleName) {
        RoleDTO roleDTO = roleDTORepository.getOne(roleId);
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

    @Override
    public IResult<Page<UserDTO>> queryUsers(Integer pageNum, Integer pageSize) {
        return IResultUtil.successResult(userDTORepository.findAll(PageRequest.of(pageNum - 1, pageSize)));
    }

    @Override
    public IResult<String> updateUserRole(Long userId, String newRole) {
        return null;
    }
}
