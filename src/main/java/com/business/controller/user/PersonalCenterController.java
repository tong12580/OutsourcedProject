package com.business.controller.user;

import com.business.common.message.ResultMessage;
import com.business.common.response.IResult;
import com.business.common.response.IResultException;
import com.business.pojo.dto.user.UserDTO;
import com.business.service.interfaces.users.PersonalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuton
 * @version 1.0
 * @description 个人中心服务API
 * @since 2017/2/8 13:38
 */
@RestController
@RequestMapping("/api")
public class PersonalCenterController {

    @Autowired
    private PersonalCenterService personalCenterService;

    /**
     * @param userDTO  {@link UserDTO}
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @return
     * @description Register 注册
     */
    @RequestMapping("/register")
    public IResult register(UserDTO userDTO, HttpServletRequest request,
                            HttpServletResponse response) throws IResultException {
        if (null == userDTO) {
            throw new IResultException(ResultMessage.REQUEST_PARAMETER_IS_EMPTY);
        }
        return personalCenterService.register(userDTO, request, response);
    }
}
