package com.google.couponfinder.service.impl;

import com.google.couponfinder.service.SearchService;
import com.google.couponfinder.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * @author W&F
 * @version 1.0
 * @date 2023/5/3 16:28
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    //Redis的好处就是分布式系统的话，比较方便数据共享。持久化应该也好处理一些。

    private String sortedSetName = "coupon_finder:hotKeywordsZSet";
    private final RedisUtils redisUtils;

    @Autowired
    public SearchServiceImpl(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }


    @Override
    public void updateKeywordsAppearingTimes(String keywords) {
        double x = 1.0;
        redisUtils.getZSetOperations().incrementScore(sortedSetName, keywords, x);
    }

    /**
     * 前N个搜索热词
     * 返回的有序集合中，score大的在前面
     *
     * @param top_n
     * @return
     */
    @Override
    public Set<ZSetOperations.TypedTuple<String>> getTopNHotSearchKeywords(Long top_n) {
        Set<ZSetOperations.TypedTuple<String>> resultSet = redisUtils.getZSetOperations().reverseRangeWithScores(sortedSetName, 0, top_n - 1);
        return resultSet;
    }

}
