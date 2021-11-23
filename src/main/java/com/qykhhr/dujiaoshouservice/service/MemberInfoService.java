package com.qykhhr.dujiaoshouservice.service;


import com.qykhhr.dujiaoshouservice.bean.MemberInfo;
import com.qykhhr.dujiaoshouservice.bean.data.MemberInfoData;

import java.util.List;

public interface MemberInfoService {


    List<MemberInfoData> findAllByYear(String year);
}
