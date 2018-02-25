package com.business.controller.user;

import com.business.common.message.ResultMessage;
import com.business.common.response.IResult;
import com.business.common.response.IResultUtil;
import com.business.dao.users.AreaInfoDTORepository;
import com.business.pojo.dto.user.AreaInfoDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * @author yutong
 * @version 1.0
 * @description 行政区划查询器
 * @since 2018/2/25 00:27
 */
@RestController
@RequestMapping("/api")
@Api(value = "行政区域查询控制器", tags = {"行政区域查询控制器"}, description = "查询省市县三级城市区划")
public class AreaInfoCtrl {
    @Resource
    private AreaInfoDTORepository areaInfoDTORepository;

    @GetMapping("/provinces")
    @ApiOperation(value = "省级行政区域查询", notes = "省级行政区域查询",
            authorizations = {@Authorization(value = "basicAuth")})
    public IResult<List<AreaInfoDTO>> queryProvinces() {
        return IResultUtil.successResult(ResultMessage.STATUS_SUCCESS, areaInfoDTORepository.findBySuperiorIdIsNull());
    }

    @GetMapping("/subordinateAdministrativeUnits")
    @ApiOperation(value = "次级行政区域查询", notes = "查询市、现行政区划",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiImplicitParam(name = "id", value = "上级行政区划ID", dataType = "Int", required = true)
    public IResult<List<AreaInfoDTO>> querySubordinateAdministrativeUnits(Integer id) {
        return IResultUtil.successResult(ResultMessage.STATUS_SUCCESS, areaInfoDTORepository.findBySuperiorId(id));
    }
}