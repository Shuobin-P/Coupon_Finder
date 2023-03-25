package com.google.couponfinder.mapper;

import com.google.couponfinder.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/25 15:35
 */
@Mapper
public interface UserRoleMapper {
    void insert(Long userID, Long roleID);

    UserRole get(Long userID, Long roleID);
}
