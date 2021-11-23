package com.qykhhr.dujiaoshouservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qykhhr.dujiaoshouservice.bean.ShareStudy;
import com.qykhhr.dujiaoshouservice.bean.data.ShareStudyData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface ShareStudyService {

    Map<String,Object> findPage(long page,long id);

    void saveShareStudy(String uid, MultipartFile[] files,String content);

    void deleteShareStudy(String uid,Long id,String shareStudyUid);
}
