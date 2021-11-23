package com.qykhhr.dujiaoshouservice.bean.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareStudyData {
    //This primary key of "id" is primitive
    // long要写成Long
    private Long id;

    private String uid;
    private Integer roleId;

    private String content;
    private List<String> imageList;

    private String nickName;

    private String headPortrait;

    // 文章的时间
    private Date createTime;
    private Date updateTime;
}
