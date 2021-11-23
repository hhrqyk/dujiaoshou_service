package com.qykhhr.dujiaoshouservice.bean;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppHome {
    private String id;
    private String imageList;
    private String titleList;
    private String notice;

    //"逻辑删除 1（true）已删除， 0（false）未删除"
    @TableLogic
    private Integer isDeleted;
}
