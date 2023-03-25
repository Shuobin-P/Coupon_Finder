package com.google.couponfinder.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/25 15:40
 */
@Mapper
public interface RoleMapper {
    Long getRoleID(String role);
}
