package com.google.couponfinder.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/14 17:41
 */
@Data
public class WalletCouponDTO {
    private Long id;
    private String title;
    private String description;
    private String pictureUrl;
    private String originalPrice;
    private String presentPrice;
    private Date expireDate;
}
