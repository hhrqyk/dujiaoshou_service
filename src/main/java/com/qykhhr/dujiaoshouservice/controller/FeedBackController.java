package com.qykhhr.dujiaoshouservice.controller;

import com.qykhhr.dujiaoshouservice.bean.FeedBack;
import com.qykhhr.dujiaoshouservice.service.FeedBackService;
import com.qykhhr.dujiaoshouservice.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@Slf4j
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;

    @PostMapping
    public R feedBack(String uid,String content){
        log.info("反馈的意见：content = " + content);
        feedBackService.saveFeedBack(uid,content);
        return R.ok();
    }

    @GetMapping("{pageNum}/{pageSize}")
    public R findPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        List<FeedBack> feedBackList = feedBackService.findPage(pageNum,pageSize);
        return R.ok().data("feedBackList",feedBackList);
    }
}
