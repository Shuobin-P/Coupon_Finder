package com.google.couponfinder.util;

import java.util.UUID;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/29 19:30
 */
public class StringUtils {
    /**
     * @Description: 生成唯一图片名称
     * @Param: fileName
     * @return: 保存到云服务器中的fileName
     */
    public static String getRandomImgName(String fileName) {

        int index = fileName.lastIndexOf(".");

        if (fileName.isEmpty() || index == -1) {
            throw new IllegalArgumentException();
        }
        // 获取文件后缀
        String suffix = fileName.substring(index).toLowerCase();
        // 生成UUID
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 生成上传至云服务器的路径
        return "userAvatar:" + uuid + suffix;
    }
}
