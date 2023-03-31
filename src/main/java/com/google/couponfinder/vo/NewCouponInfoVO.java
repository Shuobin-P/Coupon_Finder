package com.google.couponfinder.vo;

import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/30 20:12
 */
@Data
public class NewCouponInfoVO {
    String title;
    String description;
    Long quantity;
    Long originalPrice;
    Long presentPrice;
    String category;
    String productURL;
    String []productDetailURL;
    /**
     * 单位：毫秒
     */
    Long startDate;
    Long expireDate;
}
