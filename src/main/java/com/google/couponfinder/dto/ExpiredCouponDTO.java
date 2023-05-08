package com.google.couponfinder.dto;

import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/5/6 17:38
 */
@Data
public class ExpiredCouponDTO {
    private Long id;
    private String title;
    private String description;
    private String pictureUrl;
    private String originalPrice;
    private String presentPrice;
}
