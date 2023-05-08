package com.google.couponfinder.dto;

import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/5/6 14:17
 */
@Data
public class ReleasedValidCouponDTO {
    private Long id;
    private String title;
    private String description;
    private String pictureUrl;
    private Long totalQuantity;
    private Long usedQuantity;
    private Long collectedQuantity;
    private String originalPrice;
    private String presentPrice;
}
