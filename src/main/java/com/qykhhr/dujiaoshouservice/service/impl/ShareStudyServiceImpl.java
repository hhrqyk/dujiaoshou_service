package com.qykhhr.dujiaoshouservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qykhhr.dujiaoshouservice.bean.ShareStudy;
import com.qykhhr.dujiaoshouservice.bean.User;
import com.qykhhr.dujiaoshouservice.bean.data.ShareStudyData;
import com.qykhhr.dujiaoshouservice.exceptionhandler.DujiaoshouException;
import com.qykhhr.dujiaoshouservice.mapper.ShareStudyMapper;
import com.qykhhr.dujiaoshouservice.service.ShareStudyService;
import com.qykhhr.dujiaoshouservice.service.UserService;
import com.qykhhr.dujiaoshouservice.util.R;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ShareStudyServiceImpl extends ServiceImpl<ShareStudyMapper, ShareStudy> implements ShareStudyService {

    @Value("${request.head.url}")
    private String requestHeadUrl;


    @Value("${upload.image.path}")
    private String uploadImageFilePath;

    @Value("${image.path}")
    private String imagePath;

    @Autowired
    private UserService userService;


    @Override
    public Map<String, Object> findPage(long page,long id) {
        int pageSize = 10;

        log.info("page = " + page);
        log.info("id = " + id);

        QueryWrapper<ShareStudy> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");

        if (id != 0){
            wrapper.gt("id",id);
            pageSize = Integer.MAX_VALUE;
        }

        Page<ShareStudy> pageTeacher = new Page<>(page, pageSize);

        ArrayList<ShareStudyData> list = new ArrayList<>();

        baseMapper.selectPage(pageTeacher, wrapper);
//        baseMapper.findAllPage(page,size);

        List<ShareStudy> shareStudyList = pageTeacher.getRecords();
        for (ShareStudy shareStudy: shareStudyList){
            ShareStudyData shareStudyData = new ShareStudyData();
            User user = userService.findUserByUid(shareStudy.getUid());
            Gson gson = new Gson();
            // gson
            List<String> studyImageList = gson.fromJson(shareStudy.getImageList(), new TypeToken<List<String>>() {
            }.getType());
            if (studyImageList != null && studyImageList.size() > 0){
                for (int i = 0; i < studyImageList.size(); i++) {
                    // 图片默认保存的样式为 upload/xxx.jpg，需要进行拼串
                    String imageUrl = studyImageList.get(i);
                    if (!imageUrl.contains("http://") && !imageUrl.contains("https://")){
                        studyImageList.set(i,requestHeadUrl + imagePath +imageUrl);
                    }
                }
            }


            log.info("studyImageList = " + studyImageList);
            shareStudy.setImageList(gson.toJson(studyImageList));
            // 拼接头像
            String headPortrait = user.getHeadPortrait();
            if (!headPortrait.contains("http://") && !headPortrait.contains("https://")){
                user.setHeadPortrait(requestHeadUrl + imagePath +headPortrait);
            }
            BeanUtils.copyProperties(user,shareStudyData);
            BeanUtils.copyProperties(shareStudy,shareStudyData);
            String imageJson = shareStudy.getImageList();

            List imageList = new Gson().fromJson(imageJson, new TypeToken<List<String>>() {
            }.getType());
            shareStudyData.setImageList(imageList);

            list.add(shareStudyData);
        }

        // 当前页
        long current = pageTeacher.getCurrent();
        // 总页数
        long pages = pageTeacher.getPages();
        // 每页记录数
        long size = pageTeacher.getSize();
        // 总记录数
        long total = pageTeacher.getTotal();



        // 把分页数据获取出来，放到map集合
        HashMap<String, Object> map = new HashMap<>();

        map.put("records",list);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);

        log.info("findPage::list = " + list);

        return map;
    }

    @Override
    public void saveShareStudy(String uid, MultipartFile[] files, String content) {

        if (StringUtils.isEmpty(uid)){
            throw new DujiaoshouException(20001,"未登录，分享日常失败");
        }
        ArrayList<String> imageList = new ArrayList<>();

        if (files != null && files.length > 0) {
            String destFileName = "";
            String fileName = "";
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    try {
                        fileName = file.getOriginalFilename();
                        assert fileName != null;
                        String[] split = fileName.split("\\.");

                        if ("JPG".equals(split[split.length - 1].toUpperCase()) || "PNG".equals(split[split.length - 1].toUpperCase()) || "JPEG".equals(split[split.length - 1].toUpperCase())) {
                            fileName = System.currentTimeMillis() + split[split.length - 1].toLowerCase();

                            //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
                            destFileName = uploadImageFilePath + File.separator + fileName;

                            log.info("destFileName = " + destFileName);

                            //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
                            File destFile = new File(destFileName);
                            if (!destFile.getParentFile().exists()) {
                                destFile.getParentFile().mkdirs();
                            }

                            long size = file.getSize();
                            log.info("上传文件大小：" + size);
                            // 忽略，5MB以上的图片文件
                            if (size <= 1000000){
                                // 将上传的图片文件压缩到希望的位置
                                Thumbnails.of(file.getInputStream()).scale(0.9).toFile(destFile);
                                // 将文件名添加到集合
                                imageList.add(fileName);
                            }else if (size <= 2000000){
                                // 将上传的图片文件压缩到希望的位置
                                Thumbnails.of(file.getInputStream()).scale(0.8).toFile(destFile);
                                // 将文件名添加到集合
                                imageList.add(fileName);
                            }else if (size <= 3000000){
                                // 将上传的图片文件压缩到希望的位置
                                Thumbnails.of(file.getInputStream()).scale(0.7).toFile(destFile);
                                // 将文件名添加到集合
                                imageList.add(fileName);
                            }else if (size <= 4000000){
                                // 将上传的图片文件压缩到希望的位置
                                Thumbnails.of(file.getInputStream()).scale(0.6).toFile(destFile);
                                // 将文件名添加到集合
                                imageList.add(fileName);
                            }else if (size <= 5000000){
                                // 将上传的图片文件压缩到希望的位置
                                Thumbnails.of(file.getInputStream()).scale(0.5).toFile(destFile);
                                // 将文件名添加到集合
                                imageList.add(fileName);
                            }

                            //5.把浏览器上传的文件复制到希望的位置
//                    file.transferTo(destFile);
                        } else {
                            throw new DujiaoshouException(20001, "不支持的图片类型！");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 获取当前时间戳
        long timeMillis = System.currentTimeMillis();
        log.info("timeMillis = " + timeMillis);

        ShareStudy shareStudy = new ShareStudy();
        shareStudy.setId(timeMillis);
        shareStudy.setUid(uid);
        shareStudy.setContent(content);
        if (imageList.size() > 0){
            shareStudy.setImageList(new Gson().toJson(imageList));
        }

        log.info("shareStudy = " + shareStudy);
        int insert = baseMapper.insert(shareStudy);
        if (insert != 1) {
            throw new DujiaoshouException(20001, "分享日常失败！");
        }
    }

    /**
     *
     * @param uid 点击删除按钮的uid
     * @param id 分享日常的 id
     * @param shareStudyUid 分享日常的作者id
     */
    @Override
    public void deleteShareStudy(String uid,Long id,String shareStudyUid) {
        log.info("deleteShareStudy::uid = " + uid + ",id = " + id);

        QueryWrapper<ShareStudy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid);

        User user = userService.findUserByUid(uid);
        User userShareUid = userService.findUserByUid(shareStudyUid);

        // 管理员删除，不需要比对，只需要判断是否存在就行，并且需要比较每个role大于当前文章id才能进行删除
        if (user.getRoleId() > userShareUid.getRoleId()){
            QueryWrapper<ShareStudy> shareStudyQueryWrapper = new QueryWrapper<>();
            shareStudyQueryWrapper.eq("id",id);
            int delete = baseMapper.delete(shareStudyQueryWrapper);
            if (delete != 1){
                throw new DujiaoshouException(20001,"删除失败");
            }
            return;
        }
        // 普通用户删除，需要判断用户id和表id
        queryWrapper.eq("id",id);

        int delete = baseMapper.delete(queryWrapper);
        if (delete != 1){
            throw new DujiaoshouException(20001,"删除失败");
        }
    }
}
