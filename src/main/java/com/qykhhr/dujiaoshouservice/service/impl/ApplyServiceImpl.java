package com.qykhhr.dujiaoshouservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qykhhr.dujiaoshouservice.bean.ApplyInfo;
import com.qykhhr.dujiaoshouservice.bean.User;
import com.qykhhr.dujiaoshouservice.bean.data.ApplyInfoData;
import com.qykhhr.dujiaoshouservice.exceptionhandler.DujiaoshouException;
import com.qykhhr.dujiaoshouservice.mapper.ApplyMapper;
import com.qykhhr.dujiaoshouservice.mapper.UserMapper;
import com.qykhhr.dujiaoshouservice.service.ApplyService;
import com.qykhhr.dujiaoshouservice.service.UserService;
import com.qykhhr.dujiaoshouservice.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, ApplyInfo> implements ApplyService {


    /**
     * 添加申请信息
     * @param applyInfo
     * @return
     */
    @Override
    public void addApply(ApplyInfo applyInfo) {
        if (applyInfo == null){
            throw new DujiaoshouException(20001,"上传失败！");
        }

        int insert = baseMapper.insert(applyInfo);
        if (insert != 1){
            throw new DujiaoshouException(20001,"上传失败！");
        }
    }

    @Override
    public void deleteApply(String stuNumber) {

        int delete = baseMapper.deleteById(stuNumber);
        if (delete != 1){
            throw new DujiaoshouException(20001,"删除申请信息失败");
        }
    }

    @Override
    public List<ApplyInfoData> findPage(Integer pageNum, Integer pageSize) {
        QueryWrapper<ApplyInfo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");

        Page<ApplyInfo> applyInfoPage = new Page<>(pageNum,pageSize);
        baseMapper.selectPage(applyInfoPage,wrapper);

        List<ApplyInfo> records = applyInfoPage.getRecords();
        List<ApplyInfoData> list = new ArrayList<>(records.size());
        for (ApplyInfo record : records) {
            ApplyInfoData applyInfoData = new ApplyInfoData();
            BeanUtils.copyProperties(record, applyInfoData);
            list.add(applyInfoData);
        }

        return list;
    }
}
