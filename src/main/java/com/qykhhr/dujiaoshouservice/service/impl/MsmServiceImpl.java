package com.qykhhr.dujiaoshouservice.service.impl;


import com.qykhhr.dujiaoshouservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean sendMail(Map<String, Object> param, String email)  {

        if (StringUtils.isEmpty(email))
            return false;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("qykhhr@qq.com");
        message.setTo(email);
        message.setSubject("独角兽博客APP验证");
        message.setText("【独角兽工作室】您的验证码是  " + param.get("code") + " ,用于独角兽信息验证，仅5分钟内有效，请尽快使用！请勿将验证码透露给他人。登录 www.mail.qq.com 查询验证码");

        mailSender.send(message);

        return true;
    }
}
