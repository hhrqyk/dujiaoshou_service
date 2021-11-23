package com.qykhhr.dujiaoshouservice.service;

import com.qykhhr.dujiaoshouservice.bean.AppHome;
import com.qykhhr.dujiaoshouservice.bean.ApplyInfo;
import com.qykhhr.dujiaoshouservice.bean.data.AppHomeData;


public interface AppHomeService {

    AppHomeData findAppHome();

    void saveAppHome(AppHome appHome);
}
