package com.qykhhr.dujiaoshouservice.bean.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApplyInfoData {
    private String userName;
    private String uid;
    private String phone;
    private String qq;
    private String email;
    private String reason;
    private Date createTime;
}
