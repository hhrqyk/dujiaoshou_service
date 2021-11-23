package com.qykhhr.dujiaoshouservice.controller;

import com.qykhhr.dujiaoshouservice.bean.ShareStudy;

import com.qykhhr.dujiaoshouservice.exceptionhandler.DujiaoshouException;
import com.qykhhr.dujiaoshouservice.service.ShareStudyService;

import com.qykhhr.dujiaoshouservice.util.R;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/share")
@Slf4j
public class ShareStudyController {

    @Autowired
    private ShareStudyService shareStudyService;

    @GetMapping("{page}/{id}")
    public R findPage(@PathVariable long page,@PathVariable long id){
        log.info("收到请求 findPage  page = " + page);
        log.info("收到请求 findPage  id = " + id);

        Map<String,Object> map = shareStudyService.findPage(page,id);

        return R.ok().data(map);
    }

    @DeleteMapping("{uid}/{id}/{shareStudyUid}")
    public R deleteShareStudy(@PathVariable String uid,@PathVariable Long id,@PathVariable String shareStudyUid){
        shareStudyService.deleteShareStudy(uid,id,shareStudyUid);
        return R.ok();
    }


    @PostMapping("save")
    public R saveShareStudy(String uid, MultipartFile[] files,String content){

        log.info("uid = " + uid);
        log.info("content = " + content);
        log.info("files = " + files);

        shareStudyService.saveShareStudy(uid,files,content);
        return R.ok();
    }
}
