package com.qykhhr.dujiaoshouservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qykhhr.dujiaoshouservice.bean.CrashExceptionFile;
import com.qykhhr.dujiaoshouservice.bean.ShareStudy;
import com.qykhhr.dujiaoshouservice.exceptionhandler.DujiaoshouException;
import com.qykhhr.dujiaoshouservice.mapper.CrashExceptionFileMapper;
import com.qykhhr.dujiaoshouservice.service.CrashExceptionFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrashExceptionFileServiceImpl extends ServiceImpl<CrashExceptionFileMapper, CrashExceptionFile> implements CrashExceptionFileService {

    @Value("${request.head.url}")
    private String requestHeadUrl;

    @Value("${crash.path}")
    private String crashPath;



    @Override
    public void saveCrashFile(String fileName) {
//        long millis = System.currentTimeMillis();

        int insert = baseMapper.insert(new CrashExceptionFile(null, fileName,null,null));
        if (insert != 1){
            throw new DujiaoshouException(20001,"上传报错信息失败");
        }
    }

    @Override
    public List<CrashExceptionFile> findPage(int pageNum, int pageSize) {
        QueryWrapper<CrashExceptionFile> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");

        Page<CrashExceptionFile> page = new Page<>(pageNum, pageSize);

        baseMapper.selectPage(page, wrapper);

        List<CrashExceptionFile> fileList = page.getRecords();
        for (int i = 0; i < fileList.size(); i++) {
            CrashExceptionFile exceptionFile = fileList.get(i);
            if (!exceptionFile.getFileName().contains("http://") || !exceptionFile.getFileName().contains("https://")){
                exceptionFile.setFileName(requestHeadUrl + crashPath +exceptionFile.getFileName());
            }
        }
        return fileList;
    }


}
