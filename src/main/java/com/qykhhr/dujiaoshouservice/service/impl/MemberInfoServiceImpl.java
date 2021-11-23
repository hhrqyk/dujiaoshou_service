package com.qykhhr.dujiaoshouservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qykhhr.dujiaoshouservice.bean.MemberInfo;
import com.qykhhr.dujiaoshouservice.bean.data.MemberInfoData;
import com.qykhhr.dujiaoshouservice.exceptionhandler.DujiaoshouException;
import com.qykhhr.dujiaoshouservice.mapper.MemberInfoMapper;
import com.qykhhr.dujiaoshouservice.service.MemberInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberInfoServiceImpl extends ServiceImpl<MemberInfoMapper, MemberInfo> implements MemberInfoService {

    @Value("${request.head.url}")
    private String requestHeadUrl;

    @Value("${image.path}")
    private String imagePath;

    @Override
    public List<MemberInfoData> findAllByYear(String year) {
        QueryWrapper<MemberInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year",year);
        queryWrapper.orderByAsc("id");

        List<MemberInfo> memberInfos = baseMapper.selectList(queryWrapper);
        List<MemberInfoData> list = new ArrayList<>();

        if (memberInfos == null || memberInfos.size() <= 0){
            throw new DujiaoshouException(20001,"暂无数据");
        }

        Gson gson = new Gson();

        for (int i = 0; i < memberInfos.size(); i++) {
            MemberInfo memberInfo = memberInfos.get(i);
            MemberInfoData memberInfoData = new MemberInfoData();
            BeanUtils.copyProperties(memberInfo,memberInfoData);

            list.add(i, memberInfoData);

            String imageUrlJson = memberInfo.getImageUrl();
            if (imageUrlJson != null && imageUrlJson.length() > 0){
                List<String> imageList = gson.fromJson(imageUrlJson,new TypeToken<List<String>>(){}.getType());
                for (int j = 0; j < imageList.size(); j++) {
                    imageList.set(j,requestHeadUrl + imagePath + memberInfo.getYear()+"/"+memberInfo.getUserName() + "/" +imageList.get(j));
                }
                list.get(i).setImageUrl(imageList);
            }
        }
        return list;
    }
}
