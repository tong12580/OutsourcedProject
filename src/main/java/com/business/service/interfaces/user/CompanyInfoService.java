package com.business.service.interfaces.user;

import com.business.pojo.dto.user.AreaInfoDTO;
import com.business.pojo.dto.user.UserInfoDTO;
import com.jokers.common.response.IResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yutong
 * @version 1.0
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
