package com.qykhhr.dujiaoshouservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VersionInfo {

    // 版本code
    private Integer code;
    // 下载地址
    private String url;
    //更新介绍
    private String updateIntroduction;
}
