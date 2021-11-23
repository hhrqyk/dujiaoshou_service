package com.qykhhr.dujiaoshouservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qykhhr.dujiaoshouservice.bean.User;
import com.qykhhr.dujiaoshouservice.exceptionhandler.DujiaoshouException;
import com.qykhhr.dujiaoshouservice.mapper.UserMapper;
import com.qykhhr.dujiaoshouservice.service.UserService;
import com.qykhhr.dujiaoshouservice.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Value("${request.head.url}")
    private String requestHeadUrl;

    @Value("${image.path}")
    private String imagePath;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void updateHeadPortrait(String uid, String headPortrait) {
        int update = baseMapper.updateHeadPortrait(uid,headPortrait);
        if (update != 1){
            throw new DujiaoshouException(20001,"上传图片失败！");
        }
    }

    @Override
    public void updateUserNickName(String uid,String nickName) {
        int update = baseMapper.updateUserNickName(uid,nickName);
        System.out.println("update = " + update);
        if (update != 1){
            throw new DujiaoshouException(20001,"修改昵称失败！");
        }
    }

    @Override
    public void updateUserPassword(String uid, String password,String email, String code) {
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(uid)){
            throw new DujiaoshouException(20001,"信息输入有误！");
        }

        User userByDb = findUserByUid(uid);
        if (!userByDb.getEmail().equals(email)){
            throw new DujiaoshouException(20001,"与注册邮箱不符，！");
        }

        String codeForRedis = redisTemplate.opsForValue().get(email);

        log.info("codeForRedis = " + codeForRedis);

        if (!StringUtils.isEmpty(codeForRedis) && code.equals(codeForRedis)){
            int update = baseMapper.updateUserPassword(uid,password.length() == 32?password:MD5.encrypt(password));
            if (update != 1){
                throw new DujiaoshouException(20001,"修改密码失败，请查看学号是否输入错误！");
            }
        }else{
            throw new DujiaoshouException(20001,"验证码已失效，请重新获取！");
        }
    }

    @Override
    public User loginUser(User loginUser) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",loginUser.getUid());
        // 如果登录密码已经是32位，说明已经被加密了，不用再次加密
        wrapper.eq("password", loginUser.getPassword().length() == 32?loginUser.getPassword():MD5.encrypt(loginUser.getPassword()));
        User user = baseMapper.selectOne(wrapper);
        if (user == null){
            throw new DujiaoshouException(20001,"登录失败，请检查输入是否有误!");
        }
        // 图片默认保存的样式为 upload/xxx.jpg，需要进行拼串
        String imageUrl = user.getHeadPortrait();
        // 判断保存的url 是否是 xxx.jpg
        if (!imageUrl.contains("http://") && !imageUrl.contains("https://")){
            user.setHeadPortrait(requestHeadUrl + imagePath +imageUrl);
        }
        log.info("数据库中查询的user = " + user);
        return user;
    }

    @Override
    public User registerUser(User user) {
        // 获取admin的token，只有我的token才有效
        User admin = baseMapper.selectOne(new QueryWrapper<User>().eq("uid", "1915925197"));

        if (user != null){
            if (admin != null && admin.getToken().length() > 0){
                if (!admin.getToken().equals(user.getToken())){
                    throw new DujiaoshouException(20001,"邀请码错误!");
                }

                // 对密码进行加密
                user.setPassword(MD5.encrypt(user.getPassword()));

                int insert = baseMapper.insert(user);

                if (insert != 1){
                    throw new DujiaoshouException(20001,"注册失败!");
                }
                return loginUser(user);
            }else {
                throw new DujiaoshouException(20001,"暂不对外开放注册！");
            }

        }
        throw new DujiaoshouException(20001,"注册失败!");
    }

    /**
     * 根据用户id查询用户信息
     * @return
     */
    @Override
    public User findUserByUid(String uid) {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("uid", uid));

        if (user == null){
            throw new DujiaoshouException(20001,"查询失败，请检查用户是否存在");
        }
        // 拼接头像
        String headPortrait = user.getHeadPortrait();
        if (!headPortrait.contains("http://") && !headPortrait.contains("https://")){
            user.setHeadPortrait(requestHeadUrl + imagePath +headPortrait);
        }

        return user;
    }

}
