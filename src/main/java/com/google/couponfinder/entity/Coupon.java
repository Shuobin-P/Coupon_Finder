package com.google.couponfinder.entity;

import lombok.Data;

@Data
public class Coupon {

    private long id;
    private String title;
    private String status;
    private String pictureUrl;
    private String description;
    private long totalQuantity;
    private long usedQuantity;
    private long collectedQuantity;
    private java.sql.Timestamp startDate;
    private java.sql.Timestamp expireDate;
    private long categoryId;
    private Double originalPrice;
    private Double presentPrice;
    private Long merchantID;


}
