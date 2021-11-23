package com.qykhhr.dujiaoshouservice.controller;

import com.qykhhr.dujiaoshouservice.bean.CrashExceptionFile;
import com.qykhhr.dujiaoshouservice.exceptionhandler.DujiaoshouException;
import com.qykhhr.dujiaoshouservice.service.CrashExceptionFileService;
import com.qykhhr.dujiaoshouservice.util.R;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/**
 * 接收应用报错信息，用于排除错误
 */
@RestController
@Slf4j
@RequestMapping("/crashException")
public class CrashExceptionFileController {

    @Value("${upload.crash.file.path}")
    private String uploadCrashFilePath;

    @Autowired
    private CrashExceptionFileService crashExceptionFileService;

    @GetMapping("{pageNum}/{pageSize}")
    public R findPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize){

        List<CrashExceptionFile> crashFileList = crashExceptionFileService.findPage(pageNum, pageSize);

        return R.ok().data("crashFileList",crashFileList);
    }

    @PostMapping("uploadFile")
    public R uploadFile(MultipartFile file) throws IOException {

        log.info("CrashExceptionFileController::uploadFile()");

        String destFileName;
        String fileName;
        if (file != null && !file.isEmpty()){
            fileName = file.getOriginalFilename();
            assert fileName != null;

            //3.保存的路径  获取当前项目的真实路径，然后拼接前面的文件名
            destFileName = uploadCrashFilePath + File.separator + fileName;

            log.info("destFileName = " + destFileName);

            //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
            File destFile = new File(destFileName);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }

            InputStream inputStream = file.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(destFile);
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            inputStream.close();
            outputStream.close();

//            fileName = requestHeadUrl + crashPath + fileName;

            crashExceptionFileService.saveCrashFile(fileName);

            // 但是返回图片路径得返回全路径
            return R.ok().message("上传报错文件成功");
        }else {
            return R.error().message("上传报错文件失败！");
        }

    }
}
