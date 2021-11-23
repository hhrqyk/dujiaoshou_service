package com.qykhhr.dujiaoshouservice.service;


import com.qykhhr.dujiaoshouservice.bean.FeedBack;

import java.util.List;

public interface FeedBackService {

    void saveFeedBack(String uid, String content);

    List<FeedBack> findPage(Integer pageNum, Integer pageSize);
}
