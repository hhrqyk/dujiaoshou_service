package com.qykhhr.dujiaoshouservice.controller;

import com.qykhhr.dujiaoshouservice.bean.data.AppHomeData;
import com.qykhhr.dujiaoshouservice.mycomment.AccessLimit;
import com.qykhhr.dujiaoshouservice.service.AppHomeService;
import com.qykhhr.dujiaoshouservice.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppHomeController {

    @Autowired
    private AppHomeService appHomeService;

    @GetMapping("/index")
    @AccessLimit(seconds = 3, maxCount = 1) //15秒内 允许请求3次
    public R getImageList(){
        AppHomeData appHome = appHomeService.findAppHome();
        System.out.println("test git3");
        return R.ok().data("appHome",appHome);
    }

}
