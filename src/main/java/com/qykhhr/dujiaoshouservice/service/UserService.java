package com.qykhhr.dujiaoshouservice.service;

import com.qykhhr.dujiaoshouservice.bean.User;


public interface UserService {

    User loginUser(User user);

    User registerUser(User user);

    User findUserByUid(String uid);

    void updateHeadPortrait(String uid, String headPortrait);

    void updateUserNickName(String uid,String nickName);

    void updateUserPassword(String uid, String password,String email, String code);
}
