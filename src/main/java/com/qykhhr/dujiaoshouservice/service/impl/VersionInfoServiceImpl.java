package com.qykhhr.dujiaoshouservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qykhhr.dujiaoshouservice.bean.VersionInfo;
import com.qykhhr.dujiaoshouservice.exceptionhandler.DujiaoshouException;
import com.qykhhr.dujiaoshouservice.mapper.VersionInfoMapper;
import com.qykhhr.dujiaoshouservice.service.VersionInfoService;
import com.qykhhr.dujiaoshouservice.util.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VersionInfoServiceImpl extends ServiceImpl<VersionInfoMapper, VersionInfo> implements VersionInfoService {


    @Value("${request.head.url}")
    private String requestHeadUrl;

    @Override
    public VersionInfo getVersionInfo(Integer code) {
        VersionInfo versionInfo = baseMapper.selectById(code);
        if (versionInfo == null){
            QueryWrapper<VersionInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("code");
            queryWrapper.last("limit 1");
            versionInfo = baseMapper.selectOne(queryWrapper);
        }
        if(versionInfo.getCode() <= code){
            throw new DujiaoshouException(20001,"已经是最新版本");
        }
        versionInfo.setUrl(requestHeadUrl + "apk/" + versionInfo.getUrl());

        return versionInfo;
    }
}
