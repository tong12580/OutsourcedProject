package com.business.service.interfaces.user;

import com.business.common.response.IResult;
import com.business.pojo.dto.user.AreaInfoDTO;
import com.business.pojo.dto.user.UserInfoDTO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2018/3/2 20:43
 */
public interface CompanyInfoService {

    /**
     * 查询省级行政单位
     */
    List<AreaInfoDTO> findBySuperiorIdIsNull();

    /**
     * 查询下级行政单位
     *
     * @param superiorId 上级行政单位id
     */
    List<AreaInfoDTO> findBySuperiorId(Integer superiorId);

    /**
     * 添加或修改公司信息
     *
     * @param userInfoDTO UserInfoDTO
     * @return String
     */
    IResult<String> updateCompanyInfo(UserInfoDTO userInfoDTO);

    /**
     * 查询公司信息
     * @param request HttpServletRequest
     * @return UserInfoDTO
     */
    IResult<UserInfoDTO> queryUserInfo(HttpServletRequest request);

}
