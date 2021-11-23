package com.qykhhr.dujiaoshouservice.controller;


import com.qykhhr.dujiaoshouservice.bean.ApplyInfo;
import com.qykhhr.dujiaoshouservice.bean.User;
import com.qykhhr.dujiaoshouservice.exceptionhandler.DujiaoshouException;
import com.qykhhr.dujiaoshouservice.service.ApplyService;
import com.qykhhr.dujiaoshouservice.service.UserService;
import com.qykhhr.dujiaoshouservice.util.R;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Value("${request.head.url}")
    private String requestHeadUrl;

    @Value("${upload.image.path}")
    private String uploadImageFilePath;

    @Value("${image.path}")
    private String imagePath;


    @Autowired
    private UserService userService;


    @PostMapping("login")
    public R login(User loginUser){

        log.info("user = " + loginUser);
        User user = userService.loginUser(loginUser);
        return R.ok().message("ok").data("user",user);
    }


    @PostMapping("register")
    public R register(User registerUser){
        log.info("user = " + registerUser);
        User user = userService.registerUser(registerUser);
        log.info("注册并登录后的user = " + user);
        return R.ok().message("ok").data("user",user);
    }

    @PostMapping("updateUserNickName")
    public R updateUserNickName(User user){
        log.info("user = " + user);
        userService.updateUserNickName(user.getUid(),user.getNickName());
        return R.ok().message("ok");
    }

    @PostMapping("updateUserPassword")
    public R updateUserPassword(String uid,String password,String email,String code){
        log.info("uid = " + uid);
        log.info("password = " + password);
        log.info("code = " + code);
        log.info("email = " + email);

        userService.updateUserPassword(uid,password,email,code);
        return R.ok().message("ok");
    }


    @PostMapping("uploadFile")
    public R uploadFile(String uid,MultipartFile file){

        log.info("uid = " + uid);

        String destFileName;
        String fileName;
        if (file != null && !file.isEmpty()){
            try {
                fileName = file.getOriginalFilename();
                assert fileName != null;
                String[] split = fileName.split("\\.");

                if ("jpg".equals(split[split.length - 1]) || "png".equals(split[split.length - 1]) || "jpeg".equals(split[split.length - 1])){
                    fileName = System.currentTimeMillis() + fileName;

                    //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
                    destFileName = uploadImageFilePath + File.separator + fileName;

                    log.info("destFileName = " + destFileName);

                    //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
                    File destFile = new File(destFileName);
                    if (!destFile.getParentFile().exists()) {
                        destFile.getParentFile().mkdirs();
                    }

                    // 将上传的图片文件压缩到希望的位置
                    Thumbnails.of(file.getInputStream()).scale(0.8).toFile(destFile);


                    // 保存头像只需要保存文件名即可
                    userService.updateHeadPortrait(uid, fileName);

                    //request.head.url=http://qykhhr.top:9000/upload/
                    fileName = requestHeadUrl + imagePath + fileName;

                    // 但是返回图片路径得返回全路径
                    return R.ok().message("上传成功").data("imagePath",fileName);
                    //5.把浏览器上传的文件复制到希望的位置
//                    file.transferTo(destFile);
                }else {
                    throw new DujiaoshouException(20001,"不支持的图片类型！");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return R.error().message("上传图片失败！");
            }
        }else {
            return R.error().message("上传图片失败！");
        }

    }

}
