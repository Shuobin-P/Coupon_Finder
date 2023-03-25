package com.google.couponfinder.entity;

import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/25 17:45
 */
@Data
public class UserRole {
    private Long id;
    private Long userID;
    private Long roleID;
}
