package com.qykhhr.dujiaoshouservice.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeedBack {

    private String id;
    private String uid;
    private String content;

    //"逻辑删除 1（true）已删除， 0（false）未删除"
    @TableLogic
    private Integer isDeleted;

    // 可以使插入修改时，通过DateTimeObjectHandler自动填充时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
