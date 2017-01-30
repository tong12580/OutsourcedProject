package com.business.dao.coupon;

import com.business.pojo.dto.coupon.CouponDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2017/1/30 21:40
 */
public interface CouponRepository extends JpaRepository<CouponDTO, Integer> {
}