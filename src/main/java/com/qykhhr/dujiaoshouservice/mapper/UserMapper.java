package com.qykhhr.dujiaoshouservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qykhhr.dujiaoshouservice.bean.User;


public interface UserMapper extends BaseMapper<User> {

    int updateHeadPortrait(String uid,String headPortrait);
    int updateUserNickName(String uid,String nickName);
    int updateUserPassword(String uid, String password);
}
