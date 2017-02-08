package com.business.service.implementation.users;

import com.business.common.CommonTools;
import com.business.common.http.token.SessionUtil;
import com.business.common.message.CopyWriteUI;
import com.business.common.message.ResultMessage;
import com.business.common.other.Files.MD5Util;
import com.business.common.response.IResult;
import com.business.common.uuid.UUIDUtil;
import com.business.dao.users.UserOauthRepository;
import com.business.dao.users.UserRepository;
import com.business.pojo.dto.user.UserDTO;
import com.business.pojo.dto.user.UserOauthDTO;
import com.business.pojo.vo.UserVo;
import com.business.service.interfaces.users.PersonalCenterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuton
 * @version 1.0
 * @description
 * @since 2017/2/7 16:10
 */
@Service
public class PersonalCenterServiceImpl implements PersonalCenterService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserOauthRepository userOauthRepository;
    @Resource
    private CopyWriteUI copyWriteUI;

    @Override
    @Transactional
    public IResult register(UserDTO userDTO, HttpServletRequest request, HttpServletResponse response) {
        if (!CommonTools.isPhone(userDTO.getPhone())) {
            return CommonTools.errorResult(ResultMessage.INPUT_PARAMETER_EXCEPTION, copyWriteUI.getPhoneException());
        }
        if (null != userRepository.findByPhoneAndStatusTrue(userDTO.getPhone())) {
            return CommonTools.errorResult(ResultMessage.ERROR_PROMPT, copyWriteUI.getHavePhone());
        }
        if (CommonTools.isBlank(userDTO.getPassword())) {
            return CommonTools.errorResult(ResultMessage.ERROR_PROMPT, copyWriteUI.getPasswordException());
        }
        userDTO.setSalt(UUIDUtil.getShortUUid());
        userDTO.setPassword(MD5Util.getMD5Encode(userDTO.getPassword(), userDTO.getSalt()));
        userDTO.setInviteCode(UUIDUtil.getShortUUid());
        userDTO.setNickName(userDTO.getPhone());
        userDTO.setStatus(true);
        userDTO.setType(1);
        userRepository.save(userDTO);
        UserVo vo = new UserVo.Builder().builder(userDTO).build();
        String token = SessionUtil.setSessionAttribute(request, response, userDTO.getPhone(), vo);
        UserOauthDTO userOauthDTO = new UserOauthDTO();
        userOauthDTO.setToken(token);
        userOauthDTO.setValidTime(6);
        userOauthDTO.setType(userDTO.getType());
        userOauthDTO.setUserId(userDTO.getId());
        userOauthDTO.setStatus(true);
        userOauthRepository.save(userOauthDTO);
        return CommonTools.successResult(ResultMessage.STATUS_SUCCESS, vo);
    }

    @Override
    public IResult login(UserDTO userDTO) {
        return null;
    }

    @Override
    public IResult logOut(UserDTO userDTO) {
        return null;
    }

    @Override
    public IResult updatePassWordByOldPassWord(String oldPassWord, String newPassWord) {
        return null;
    }

    @Override
    public IResult updatePassWordByVerificationCode(String code, String newPassWord) {
        return null;
    }

    @Override
    public IResult uploadImg(String img) {
        return null;
    }
}
