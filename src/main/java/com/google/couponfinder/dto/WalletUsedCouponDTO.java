package com.google.couponfinder.dto;

import lombok.Data;

import java.sql.Date;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/5/4 14:48
 */
@Data
public class WalletUsedCouponDTO {
    private Long id;
    private String title;
    private String description;
    private String pictureUrl;
    private Double originalPrice;
    private Double presentPrice;
    private Date ts;
}
