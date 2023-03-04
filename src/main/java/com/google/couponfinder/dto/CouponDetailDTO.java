package com.google.couponfinder.dto;

import lombok.Data;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/4 20:40
 */
@Data
public class CouponDetailDTO {
    private Long id;
    private String title;
    private String status;
    private String pictureUrl;
    private String description;
    private Long totalQuantity;
    private Long usedQuantity;
    private java.sql.Timestamp startDate;
    private java.sql.Timestamp expireDate;
    private Long categoryId;
    private Double originalPrice;
    private Double presentPrice;
    private List<String> images;
}
