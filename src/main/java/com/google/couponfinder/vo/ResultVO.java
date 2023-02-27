package com.google.couponfinder.vo;

import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/2/13 16:41
 */
@Data
public class ResultVO {
    /**
     * @description 响应码
     */
    private Integer code;
    /**
     * @description 响应消息
     */
    private String message;
    /**
     * @description 数据
     */
    private Object data;

    private ResultVO(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResultVO getInstance(String message, Object data) {
        return new ResultVO(200, message, data);
    }

    public static ResultVO getInstance(Integer code, String message, Object data) {
        return new ResultVO(code, message, data);
    }
}
