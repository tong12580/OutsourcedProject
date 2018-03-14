package com.business.controller.user;

import com.business.common.response.IResult;
import com.business.pojo.dto.user.UserInfoDTO;
import com.business.service.interfaces.user.CompanyInfoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2018/3/2 20:41
 */
@RestController
@RequestMapping("/api")
@Api(value = "公司信息", tags = {"公司信息"}, description = "公司信息CURD操作")
public class CompanyInfoCtrl {

    @Resource
    private CompanyInfoService companyInfoService;

    @GetMapping("/user")
    @ApiOperation(value = "查询公司信息", notes = "查询公司信息",
            authorizations = {@Authorization(value = "basicAuth"), @Authorization(value = "token")})
    public IResult<UserInfoDTO> queryUserInfo(HttpServletRequest request) {
        return companyInfoService.queryUserInfo(request);
    }
}