package com.business.dao.coupon;

import com.business.pojo.dto.coupon.CouponConfigDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2017/1/30 21:41
 */
public interface CouponConfigRepository extends JpaRepository<CouponConfigDTO, Integer> {
}
