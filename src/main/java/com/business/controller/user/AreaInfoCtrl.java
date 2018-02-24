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

/**
 * @author yutong
 * @version 1.0
 * @description 行政区划查询器
 * @since 2018/2/25 00:27
 */
@RestController
@RequestMapping("/api")
public class AreaInfoCtrl {
    @Resource
    private AreaInfoDTORepository areaInfoDTORepository;

    @GetMapping("/provinces")
    public IResult<List<AreaInfoDTO>> queryProvinces() {
        return IResultUtil.successResult(ResultMessage.STATUS_SUCCESS, areaInfoDTORepository.findBySuperiorIdIsNull());
    }

    @GetMapping("/subordinateAdministrativeUnits")
    public IResult<List<AreaInfoDTO>> querySubordinateAdministrativeUnits(Integer id) {
        return IResultUtil.successResult(ResultMessage.STATUS_SUCCESS, areaInfoDTORepository.findBySuperiorId(id));
    }
}
