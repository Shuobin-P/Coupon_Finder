package com.google.couponfinder.mapper;

import com.google.couponfinder.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/15 21:23
 */
@Mapper
public interface UserMapper {
    Long getCardPackageID(String open_id);

    Long updateUserCardPackageID(Long user_id, Long cardPackageId);

    Long getUserID(String openID);

    Long insertUser(User user);
}
