package com.business.controller.user;

import com.business.common.URI;
import com.business.pojo.dto.user.UserInfoDTO;
import com.business.service.interfaces.user.CompanyInfoService;
import com.jokers.common.response.IResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yutong
 * @version 1.0
 * @since 2018/3/2 20:41
 */
@RestController
@RequestMapping(URI.VERSION_NUMBER_1 + URI.INTERFACE_TYPE_API)
@Api(value = "公司信息", tags = {"公司信息"},
        authorizations = {@Authorization(value = "basicAuth"), @Authorization(value = "token")})
public class CompanyInfoCtrl {

    @Resource
    private CompanyInfoService companyInfoService;

    @GetMapping("/user")
    @ApiOperation(value = "查询公司信息", notes = "查询公司信息")
    public IResult<UserInfoDTO> queryUserInfo(HttpServletRequest request) {
        return companyInfoService.queryUserInfo(request);
    }
}
