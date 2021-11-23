package com.qykhhr.dujiaoshouservice.controller;

import com.qykhhr.dujiaoshouservice.bean.ApplyInfo;
import com.qykhhr.dujiaoshouservice.bean.data.ApplyInfoData;
import com.qykhhr.dujiaoshouservice.service.ApplyService;
import com.qykhhr.dujiaoshouservice.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("applyJoinUs")
@Slf4j
public class ApplyInfoController {
    @Autowired
    private ApplyService applyService;

    @PostMapping
    public R applyJoinUs(ApplyInfo applyInfo){
        applyService.addApply(applyInfo);

        log.info("applyInfo = " + applyInfo);
//        User user = userService.loginUser(applyInfo);

        return R.ok();
    }

    @GetMapping("{pageNum}/{pageSize}")
    public R findPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        List<ApplyInfoData> applyInfoData = applyService.findPage(pageNum, pageSize);
        return R.ok().data("applyInfoData",applyInfoData);
    }
}
