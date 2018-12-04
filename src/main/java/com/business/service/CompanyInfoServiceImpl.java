package com.business.service;

import com.business.common.message.CopyWriteUI;
import com.business.dao.users.AreaInfoDTORepository;
import com.business.dao.users.UserDTORepository;
import com.business.dao.users.UserInfoDTORepository;
import com.business.pojo.dto.user.AreaInfoDTO;
import com.business.pojo.dto.user.UserDTO;
import com.business.pojo.dto.user.UserInfoDTO;
import com.business.service.interfaces.user.CompanyInfoService;
import com.jokers.common.http.token.JwtTokenUtil;
import com.jokers.common.response.IResult;
import com.jokers.common.response.IResultUtil;
import com.jokers.pojo.bo.JwtBo;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yutong
 * @version 1.0
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
        UserDTO userDTO = null;
        UserInfoDTO userInfoDTO;
        JwtBo jwtBo = null;
        //查报头
        String authHeader = request.getHeader(copyWriteUI.getTokenHeader());
        if (authHeader.startsWith(copyWriteUI.getTokenHead())) {
            //token
            String authToken = authHeader.substring(copyWriteUI.getTokenHead().length());
            jwtBo = JwtTokenUtil.getAuthentication(authToken);
            userInfoDTO = userInfoDTORepository.findByUserId(jwtBo.getUserId());
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
            if (null != jwtBo) {
                userInfoDTO.setUserId(jwtBo.getUserId());
            }
            if (null != userDTO) {
                userInfoDTO.setUserId(userDTO.getId());
            }

        }
        return IResultUtil.successResult(userInfoDTO);
    }
}
