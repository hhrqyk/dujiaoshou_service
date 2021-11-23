package com.qykhhr.dujiaoshouservice.controller;

import com.qykhhr.dujiaoshouservice.bean.User;
import com.qykhhr.dujiaoshouservice.exceptionhandler.DujiaoshouException;
import com.qykhhr.dujiaoshouservice.service.MsmService;
import com.qykhhr.dujiaoshouservice.service.UserService;
import com.qykhhr.dujiaoshouservice.util.R;
import com.qykhhr.dujiaoshouservice.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/email")
public class MsmController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{email}/{uid}")
    public R sendMsm(@PathVariable String email,@PathVariable String uid)  {

        User userByDb = userService.findUserByUid(uid);
        if (!userByDb.getEmail().equals(email)){
            throw new DujiaoshouException(20001,"与注册邮箱不符，请联系管理员！");
        }

        // 1、从redis获取验证码，如果获取到直接返回，获取到说明以及发送过了，且没有过期，就不再进行发送，如果过期了，就不能从redis获取到数据，需要重新发送

        System.out.println("email = " + email);
        System.out.println("redisTemplate = " + redisTemplate);

        String code = redisTemplate.opsForValue().get(email);
        if (!StringUtils.isEmpty(code)){
            return R.error().message("验证码已发送，5分钟内有效！请登录邮箱查看验证码");
        }
        // 2、如果redis获取不到，就发送邮件
        // 生成随机值，传递阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        // 调用service发送短信的方法
        boolean isSend = msmService.sendMail(param,email);

        if (isSend){
            // 发送成功，把发送成功验证码放到redis里面
            // 设置有效时间
            redisTemplate.opsForValue().set(email,code,5, TimeUnit.MINUTES);
            return R.ok();
        }
        else{
            return R.error().message("短信发送失败");
        }

    }

}
