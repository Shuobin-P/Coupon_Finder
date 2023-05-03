package com.google.couponfinder.service;

import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/5/3 16:26
 */
@Service
public interface SearchService {
    void updateKeywordsAppearingTimes(String keywords);
    Set<ZSetOperations.TypedTuple<String>> getTopNHotSearchKeywords(Long top_n);
}
