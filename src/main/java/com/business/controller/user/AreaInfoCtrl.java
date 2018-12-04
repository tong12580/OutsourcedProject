package com.business.controller.user;

import com.business.pojo.dto.user.AreaInfoDTO;
import com.business.service.interfaces.user.CompanyInfoService;
import com.jokers.common.message.ResultMessage;
import com.jokers.common.response.IResult;
import com.jokers.common.response.IResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yutong
 * @version 1.0
 * 行政区划查询器
 * @since 2018/2/25 00:27
 */
@RestController
@RequestMapping("/api")
@Api(value = "行政区域查询控制器", tags = {"行政区域查询控制器"}, description = "查询省市县三级城市区划")
public class AreaInfoCtrl {
    @Resource
    private CompanyInfoService companyInfoService;

    @GetMapping("/provinces")
    @ApiOperation(value = "省级行政区域查询", notes = "省级行政区域查询",
            authorizations = {@Authorization(value = "basicAuth"), @Authorization(value = "token")})
    public IResult<List<AreaInfoDTO>> queryProvinces() {
        return IResultUtil.successResult(ResultMessage.STATUS_SUCCESS, companyInfoService.findBySuperiorIdIsNull());
    }

    @GetMapping("/subordinateAdministrativeUnits")
    @ApiOperation(value = "次级行政区域查询", notes = "查询市、现行政区划",
            authorizations = {@Authorization(value = "basicAuth"), @Authorization(value = "token")})
    @ApiImplicitParam(name = "id", value = "上级行政区划ID", dataType = "Int", required = true)
    public IResult<List<AreaInfoDTO>> querySubordinateAdministrativeUnits(Integer id) {
        if (null == id) {
            return IResultUtil.errorResult(ResultMessage.INTERNAL_SERVER_ERROR, "id");
        }
        return IResultUtil.successResult(ResultMessage.STATUS_SUCCESS, companyInfoService.findBySuperiorId(id));
    }
}
