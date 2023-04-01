package com.google.couponfinder.mapper;

import com.google.couponfinder.entity.CardPackageCoupon;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/15 21:17
 */
@Mapper
public interface CardPackageCouponMapper {
    void getNewCoupon(Long cardPackageID, Long couponID);

    Integer getRecord(Long cardPackageID, Long couponID);

    CardPackageCoupon getEntity(Long couponID, Long cardPackageID);

    void removeRecord(Long couponID, Long cardPackageID);
}
