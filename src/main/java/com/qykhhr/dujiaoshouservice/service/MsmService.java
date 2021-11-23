package com.qykhhr.dujiaoshouservice.service;


import java.util.Map;

public interface MsmService {

    boolean sendMail(Map<String, Object> param, String email);
}
