package com.qykhhr.dujiaoshouservice.bean.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 返回的成员信息的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberInfoData {

    private String uid;
    private String year;
    private String userName;
    private String study;
    private String className;
    private List<String> imageUrl;
}
