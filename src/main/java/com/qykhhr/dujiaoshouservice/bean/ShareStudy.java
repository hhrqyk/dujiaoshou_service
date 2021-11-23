package com.qykhhr.dujiaoshouservice.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShareStudy {

    private Long id;

    private String uid;

    private String content;
    // 通过gson将List集合转为json传入数据
    private String imageList;

    //"逻辑删除 1（true）已删除， 0（false）未删除"
    @TableLogic
    private Integer isDeleted;

    // 可以使插入修改时，通过DateTimeObjectHandler自动填充时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
