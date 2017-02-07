package com.business.service.implementation.users;

import com.business.common.CommonTools;
import com.business.common.http.token.SessionUtil;
import com.business.common.message.CopyWriteUI;
import com.business.common.message.ResultMessage;
import com.business.common.other.Files.MD5Util;
import com.business.common.response.IResult;
import com.business.dao.users.UserRepository;
import com.business.pojo.dto.user.UserDTO;
import com.business.service.interfaces.users.PersonalCenterService;
import org.springframework.stereotype.Service;

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
    private CopyWriteUI copyWriteUI;

    @Override
    public IResult register(UserDTO userDTO, HttpServletRequest request, HttpServletResponse response) {
        if (CommonTools.isPhone(userDTO.getPhone())) {
            return CommonTools.errorResult(ResultMessage.INPUT_PARAMETER_EXCEPTION, copyWriteUI.getPhoneException());
        }
        if (null != userRepository.findByPhoneAndStatusTrue(userDTO.getPhone())) {
            return CommonTools.errorResult(ResultMessage.ERROR_PROMPT, copyWriteUI.getHavePhone());
        }
        if (CommonTools.isBlank(userDTO.getPassword())) {
            return CommonTools.errorResult(ResultMessage.ERROR_PROMPT, copyWriteUI.getPasswordException());
        }
        userDTO.setSalt(CommonTools.getShortUUid());
        userDTO.setPassword(MD5Util.getMD5Encode(userDTO.getPassword(), userDTO.getSalt()));
        userDTO.setInviteCode(CommonTools.getShortUUid());
        userRepository.save(userDTO);
        SessionUtil.setSessionAttributeString(request, response, userDTO.getPhone(), SessionUtil.getSessionId(request));
        return null;
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
