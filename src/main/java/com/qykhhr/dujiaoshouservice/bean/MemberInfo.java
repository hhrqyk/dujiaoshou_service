package com.qykhhr.dujiaoshouservice.bean;


import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberInfo {
    private Integer id;
    private String uid;
    private String year;
    private String userName;
    private String study;
    private String className;
    private String imageUrl;

    //"逻辑删除 1（true）已删除， 0（false）未删除"
    @TableLogic
    private Integer isDeleted;
}
