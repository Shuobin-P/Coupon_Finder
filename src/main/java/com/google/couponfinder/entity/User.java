package com.google.couponfinder.entity;

import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/5/5 16:47
 */
@Data
public class User {
    private Long id;
    private String name;
    private String open_id;
    private Long cardPackageID;
}
