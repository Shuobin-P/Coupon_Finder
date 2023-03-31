package com.google.couponfinder.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/31 19:32
 */

@Mapper
public interface CategoryMapper {
    Long getCategoryID(String category);
}
