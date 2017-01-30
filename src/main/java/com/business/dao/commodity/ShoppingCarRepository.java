package com.business.dao.commodity;

import com.business.pojo.dto.commodity.ShoppingCarDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2017/1/30 21:38
 */
public interface ShoppingCarRepository extends JpaRepository<ShoppingCarDTO, Integer> {
}