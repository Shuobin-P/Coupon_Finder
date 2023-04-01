package com.google.couponfinder.entity;

import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/4/1 21:08
 */
@Data
public class CardPackageCoupon {
    private Long id;
    private Long cardPackageId;
    private Long couponId;
    private Byte status;
}
