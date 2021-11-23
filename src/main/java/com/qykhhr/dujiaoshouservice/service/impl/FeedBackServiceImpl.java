package com.qykhhr.dujiaoshouservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qykhhr.dujiaoshouservice.bean.CrashExceptionFile;
import com.qykhhr.dujiaoshouservice.bean.FeedBack;
import com.qykhhr.dujiaoshouservice.exceptionhandler.DujiaoshouException;
import com.qykhhr.dujiaoshouservice.mapper.CrashExceptionFileMapper;
import com.qykhhr.dujiaoshouservice.mapper.FeedBackMapper;
import com.qykhhr.dujiaoshouservice.service.CrashExceptionFileService;
import com.qykhhr.dujiaoshouservice.service.FeedBackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackServiceImpl extends ServiceImpl<FeedBackMapper, FeedBack> implements FeedBackService {


    @Override
    public void saveFeedBack(String uid, String content) {
        FeedBack feedBack = new FeedBack();
        feedBack.setUid(uid);
        feedBack.setContent(content);
        int insert = baseMapper.insert(feedBack);
        if (insert != 1){
            throw new DujiaoshouException(20001,"反馈意见提交失败");
        }
    }

    @Override
    public List<FeedBack> findPage(Integer pageNum, Integer pageSize) {
        QueryWrapper<FeedBack> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        Page<FeedBack> feedBackPage = new Page<>();
        baseMapper.selectPage(feedBackPage,wrapper);
        List<FeedBack> records = feedBackPage.getRecords();
        if (records == null || records.size() <= 0){
            throw new DujiaoshouException(20001,"暂无反馈信息");
        }
        return records;
    }
}
