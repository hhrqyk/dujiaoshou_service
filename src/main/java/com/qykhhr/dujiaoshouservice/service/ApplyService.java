package com.qykhhr.dujiaoshouservice.service;

import com.qykhhr.dujiaoshouservice.bean.ApplyInfo;
import com.qykhhr.dujiaoshouservice.bean.data.ApplyInfoData;

import java.util.List;


public interface ApplyService {

    void addApply(ApplyInfo applyInfo);

    void deleteApply(String stuNumber);

    List<ApplyInfoData> findPage(Integer pageNum,Integer pageSize);
}
