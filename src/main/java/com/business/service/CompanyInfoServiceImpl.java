package com.business.service;

import com.business.common.http.token.JwtTokenUtil;
import com.business.common.message.CopyWriteUI;
import com.business.common.response.IResult;
import com.business.common.response.IResultUtil;
import com.business.dao.users.AreaInfoDTORepository;
import com.business.dao.users.UserDTORepository;
import com.business.dao.users.UserInfoDTORepository;
import com.business.pojo.dto.user.AreaInfoDTO;
import com.business.pojo.dto.user.UserDTO;
import com.business.pojo.dto.user.UserInfoDTO;
import com.business.service.interfaces.user.CompanyInfoService;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2018/3/2 20:46
 */
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Resource
    private AreaInfoDTORepository areaInfoDTORepository;
    @Resource
    private UserInfoDTORepository userInfoDTORepository;
    @Resource
    private UserDTORepository userDTORepository;
    @Resource
    private CopyWriteUI copyWriteUI;

    @Override
    public List<AreaInfoDTO> findBySuperiorIdIsNull() {
        return areaInfoDTORepository.findBySuperiorIdIsNull();
    }

    @Override
    public List<AreaInfoDTO> findBySuperiorId(Integer superiorId) {
        return areaInfoDTORepository.findBySuperiorId(superiorId);
    }

    @Override
    public IResult<String> updateCompanyInfo(UserInfoDTO userInfoDTO) {
        return null;
    }

    @Override
    public IResult<UserInfoDTO> queryUserInfo(HttpServletRequest request) {
        UserDTO userDTO;
        UserInfoDTO userInfoDTO;
        //查报头
        String authHeader = request.getHeader(copyWriteUI.getTokenHeader());
        if (authHeader.startsWith(copyWriteUI.getTokenHead())) {
            //token
            String authToken = authHeader.substring(copyWriteUI.getTokenHead().length());
            userDTO = JwtTokenUtil.getAuthentication(authToken);
            userInfoDTO = userInfoDTORepository.findByUserId(userDTO.getId());
        } else {
            //basic
            String authToken = authHeader.substring(copyWriteUI.getBasicHead().length());
            String tempInfo = new String(Base64Utils.decode(authToken.getBytes()));
            String username = tempInfo.substring(0, tempInfo.indexOf(":"));
            userDTO = userDTORepository.findByUsername(username);
            userInfoDTO = userInfoDTORepository.findByUserId(userDTO.getId());
        }
        if (null == userInfoDTO) {
            userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserId(userDTO.getId());
        }
        return IResultUtil.successResult(userInfoDTO);
    }
}