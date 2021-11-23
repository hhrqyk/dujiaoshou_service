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
public class CrashExceptionFile {

    private String id;
    private String fileName;

    @TableLogic
    private Integer isDeleted;

    // 可以使插入修改时，通过DateTimeObjectHandler自动填充时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
