package com.qykhhr.dujiaoshouservice.bean;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * @author 雨林
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User  implements Serializable {

    private String uid;
    private String userName;
    private String nickName;
    private String password;
    private String telPhone;
    private String email;
    private String headPortrait;
    private Integer roleId;
    private String token;

    // 可以使插入修改时，通过DateTimeObjectHandler自动填充时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    private Integer isDeleted;
}
