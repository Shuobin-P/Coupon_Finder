package com.google.couponfinder.vo;

import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/2/14 16:26
 */
@Data
public class ErrorResultVO {
    /**
     * @description 响应码
     */
    private Integer code;

    /**
     * @description 错误信息
     * 简要描述后端出错原因
     */
    private String errorMessage;
    /**
     * @description 用户提示信息
     */
    private String hintMessage;

    public ErrorResultVO(Integer code, String errorMessage, String hintMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.hintMessage = hintMessage;
    }
}
