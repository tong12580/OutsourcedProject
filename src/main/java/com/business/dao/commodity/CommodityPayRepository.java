package com.business.dao.commodity;

import com.business.pojo.dto.commodity.CommodityPayDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2017/1/30 21:31
 */
public interface CommodityPayRepository extends JpaRepository<CommodityPayDTO, Integer> {
}