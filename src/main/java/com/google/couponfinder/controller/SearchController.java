package com.google.couponfinder.controller;

import com.google.couponfinder.service.SearchService;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/5/3 16:01
 */
@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/updateKeywordsAppearingTimes")
    public ResultVO updateKeywordsAppearingTimes(@RequestParam String keywords) {
        searchService.updateKeywordsAppearingTimes(keywords);
        return ResultVO.getInstance("成功更新" + keywords + "出现次数", null);
    }

    @GetMapping("/getTopNHotSearchKeywords")
    public ResultVO getTopNHotSearchKeywords(@RequestParam Long top_n) {
        return ResultVO.getInstance("成功获取前" + top_n + "个热词", searchService.getTopNHotSearchKeywords(top_n));
    }
}
