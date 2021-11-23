package com.qykhhr.dujiaoshouservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qykhhr.dujiaoshouservice.bean.AppHome;
import com.qykhhr.dujiaoshouservice.bean.data.AppHomeData;
import com.qykhhr.dujiaoshouservice.exceptionhandler.DujiaoshouException;
import com.qykhhr.dujiaoshouservice.mapper.AppHomeMapper;
import com.qykhhr.dujiaoshouservice.service.AppHomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AppHomeServiceImpl extends ServiceImpl<AppHomeMapper, AppHome> implements AppHomeService {

    @Value("${request.head.url}")
    private String requestHeadUrl;

    @Value("${upload.image.path}")
    private String uploadImageFilePath;

    @Value("${image.path}")
    private String imagePath;


    @Override
    public AppHomeData findAppHome() {
        QueryWrapper<AppHome> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 1");
        AppHome appHome = baseMapper.selectOne(wrapper);
        if (appHome == null){
            throw new DujiaoshouException(20001,"暂无数据");
        }
        Gson gson = new Gson();
        AppHomeData appHomeData = new AppHomeData();

        String imageJson = appHome.getImageList();
        if (imageJson != null && imageJson.length() > 1){
            List<String> imageList = gson.fromJson(imageJson,new TypeToken<List<String>>(){}.getType());
            for (int i = 0; i < imageList.size(); i++) {
//                http://qykhhr.top:9000/upload/   images/
                imageList.set(i,requestHeadUrl + imagePath +imageList.get(i));
            }
            appHomeData.setImageList(imageList);
        }
        String titleJson = appHome.getTitleList();
        if (titleJson != null && titleJson.length() > 1){
            List<String> titleList = gson.fromJson(titleJson,new TypeToken<List<String>>(){}.getType());
            appHomeData.setTitleList(titleList);
        }
        appHomeData.setId(appHome.getId());
        appHomeData.setNotice(appHome.getNotice());

        return appHomeData;
    }

    @Override
    public void saveAppHome(AppHome appHome) {
        if (appHome == null)
            throw new DujiaoshouException(20001,"添加失败，数据为空");

        int insert = baseMapper.insert(appHome);
        if (insert != 1){
            throw new DujiaoshouException(20001,"添加失败");
        }
    }
}
