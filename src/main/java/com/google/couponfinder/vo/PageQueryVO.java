package com.google.couponfinder.vo;

import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/2/27 17:44
 */
@Data
public class PageQueryVO {
    /**
     * 第几页
     */
    private Integer pageNum;
    /**
     * 一页包含多少条数据
     */
    private Integer pageSize;
}
