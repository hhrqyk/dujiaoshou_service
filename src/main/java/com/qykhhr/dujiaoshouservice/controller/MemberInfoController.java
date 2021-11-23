package com.qykhhr.dujiaoshouservice.controller;

import com.qykhhr.dujiaoshouservice.bean.MemberInfo;
import com.qykhhr.dujiaoshouservice.bean.data.MemberInfoData;
import com.qykhhr.dujiaoshouservice.service.MemberInfoService;
import com.qykhhr.dujiaoshouservice.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/memberInfo")
public class MemberInfoController {

    @Autowired
    private MemberInfoService memberInfoService;

    @GetMapping("/{year}")
    public R getMemberInfo(@PathVariable String year, HttpServletRequest request){

        List<MemberInfoData> list = memberInfoService.findAllByYear(year);

        return R.ok().data("list",list);
    }
}
