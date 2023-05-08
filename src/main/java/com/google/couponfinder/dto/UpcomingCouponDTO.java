package com.google.couponfinder.dto;

import lombok.Data;

import java.sql.Date;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/5/6 19:56
 */
@Data
public class UpcomingCouponDTO {
    private Long id;
    private String title;
    private String description;
    private String pictureUrl;
    private String originalPrice;
    private String presentPrice;
    private Date startDate;
}
