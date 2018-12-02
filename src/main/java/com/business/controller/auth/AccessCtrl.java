package com.business.controller.auth;

import com.google.common.collect.ImmutableMap;

import com.business.common.message.CopyWriteUI;
import com.business.common.message.ResultMessage;
import com.business.common.response.IResult;
import com.business.common.response.IResultUtil;
import com.business.dao.users.UserDTORepository;
import com.business.pojo.dto.user.UserDTO;
import com.business.service.interfaces.auth.AccessService;
import com.jokers.common.http.token.JwtTokenUtil;
import com.jokers.pojo.bo.JwtBo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 访问控制器
 *
 * @author yutong
 * @version 1.0
 * @since 2018/2/22 23:35
 */
@RestController
@Api(value = "访问控制器", tags = {"访问控制器"}, description = "注册")
public class AccessCtrl {

    @Resource
    private AccessService accessService;
    @Resource
    private CopyWriteUI copyWriteUI;
    @Resource
    private UserDTORepository userDTORepository;

    @ApiOperation(value = "注册", notes = "使用用户名、密码以及权限名称注册用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true),
            @ApiImplicitParam(name = "role", value = "权限名称", dataType = "String")
    })
    @PostMapping("/registered")
    public IResult<String> registered(String username, String password, String role) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IResultUtil.errorResult(ResultMessage.INPUT_PARAMETER_IS_EMPTY, "username or password");
        }
        return accessService.registered(username, password, role);
    }

    @ApiOperation(value = "刷新token", notes = "在token未过期前刷新token")
    @GetMapping("/refreshToken")
    public IResult refreshToken(HttpServletRequest request) {
        String newToken = null;
        String authHeader = request.getHeader(copyWriteUI.getTokenHeader());
        if (authHeader != null && authHeader.startsWith(copyWriteUI.getTokenHead())) {
            final String authToken = authHeader.substring(copyWriteUI.getTokenHead().length());
            JwtBo jwtBo = new JwtBo();
            Date date = new Date();
            jwtBo.setSecret(copyWriteUI.getSecret());
            jwtBo.setIssuer(copyWriteUI.getIssuer());
            jwtBo.setIssuedAt(date);
            jwtBo.setExpiresAt(DateUtils.addWeeks(date, 1));
            newToken = JwtTokenUtil.refreshToken(authToken, jwtBo);
        }
        if (StringUtils.isEmpty(newToken)) {
            return IResultUtil.errorResult(ResultMessage.ERROR_PROMPT, "token已过期，请重新登录！");
        }
        UserDTO userDTO = userDTORepository.findByUsername(JwtTokenUtil.getUsername(newToken));
        userDTO.setToken(newToken);
        userDTORepository.saveAndFlush(userDTO);
        return IResultUtil.successResult(ImmutableMap.of("token", newToken));
    }
}
