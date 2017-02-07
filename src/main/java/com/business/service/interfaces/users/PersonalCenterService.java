package com.business.service.interfaces.users;

import com.business.common.response.IResult;
import com.business.pojo.dto.user.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuton
 * @version 1.0
 * @description 用户个人中心服务
 * @since 2017/2/7 16:04
 */
public interface PersonalCenterService {

    /**
     * @param userDTO {@link UserDTO}
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @return
     * @description Register 注册
     */
    IResult register(UserDTO userDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * @param userDTO {@link UserDTO}
     * @return
     * @description login 用户登录服务
     */
    IResult login(UserDTO userDTO);

    /**
     * @param userDTO {@link UserDTO}
     * @return
     * @description logOut 登出
     */
    IResult logOut(UserDTO userDTO);

    /**
     * @param oldPassWord {@link String}
     * @param newPassWord {@link String}
     * @return
     * @description 修改密码 根据旧的密码
     */
    IResult updatePassWordByOldPassWord(String oldPassWord, String newPassWord);

    /**
     * @param code        {@link String}
     * @param newPassWord {@link String}
     * @return
     * @description 修改密码 根据验证码
     */
    IResult updatePassWordByVerificationCode(String code, String newPassWord);

    /**
     * @param img {@link String}
     * @return
     * @description 上传头像
     */
    IResult uploadImg(String img);

}
