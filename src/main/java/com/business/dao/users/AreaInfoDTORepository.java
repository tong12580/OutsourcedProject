package com.business.dao.users;

import com.business.pojo.dto.user.AreaInfoDTO;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2018/2/25 00:22
 */
public interface AreaInfoDTORepository extends JpaRepository<AreaInfoDTO, Integer> {

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
}
