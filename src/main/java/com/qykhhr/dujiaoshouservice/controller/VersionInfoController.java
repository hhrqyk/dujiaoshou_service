package com.qykhhr.dujiaoshouservice.controller;

import com.qykhhr.dujiaoshouservice.bean.VersionInfo;
import com.qykhhr.dujiaoshouservice.service.VersionInfoService;
import com.qykhhr.dujiaoshouservice.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
@Slf4j
public class VersionInfoController {

    @Autowired
    private VersionInfoService versionInfoService;

    /**
     * 通过code传入对比数据库中code，获取比较大的url
     * @param code
     * @return
     */
    @GetMapping("/{code}")
    public R getVersionInfo(@PathVariable Integer code){
        log.info("检查更新，传入的code = " + code);
        VersionInfo versionInfo = versionInfoService.getVersionInfo(code);
        return R.ok().data("versionInfo",versionInfo);
    }
}
