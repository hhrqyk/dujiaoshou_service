package com.qykhhr.dujiaoshouservice.service;


import com.qykhhr.dujiaoshouservice.bean.CrashExceptionFile;

import java.util.List;

public interface CrashExceptionFileService {

    void saveCrashFile(String fileName);

    List<CrashExceptionFile> findPage(int pageNum, int pageSize);

}
