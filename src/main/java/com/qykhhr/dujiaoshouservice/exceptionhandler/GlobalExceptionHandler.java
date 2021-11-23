package com.qykhhr.dujiaoshouservice.exceptionhandler;

import com.qykhhr.dujiaoshouservice.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 异常处理机制
 * 先找到特定异常处理机制，如果没有就会调用Exception异常处理机制
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理空指针的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public R exceptionHandler(NullPointerException e){
        return R.error().message("出现空指针异常，异常信息为：" + e.getMessage());
    }

    /**
     * 指定出现什么异常来执行这个方法
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        // 分门别类处理异常
        if (e instanceof org.springframework.dao.DuplicateKeyException){
            return R.error().message("用户已被注册，如是本人，请联系管理员");
        }else if (e instanceof org.springframework.web.servlet.NoHandlerFoundException || e instanceof org.springframework.web.HttpRequestMethodNotSupportedException){
            return R.error().message("抱歉，你访问的资源已丢失");
        }else if (e instanceof org.springframework.web.method.annotation.MethodArgumentTypeMismatchException){
            return R.error().message("方法参数类型转换错误");
        }
        return R.error().message("抱歉，内部服务器错误!");
    }

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(DujiaoshouException.class)
    @ResponseBody
    public R error(DujiaoshouException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
