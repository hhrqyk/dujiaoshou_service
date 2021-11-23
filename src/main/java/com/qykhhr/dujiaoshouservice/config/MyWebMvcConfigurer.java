package com.qykhhr.dujiaoshouservice.config;

import com.qykhhr.dujiaoshouservice.Interceptor.AccessLimtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 资源映射路径，只能配置一个映射
 */
@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Value("${upload.image.path}")
    private String uploadImagePath;

    @Value("${upload.crash.file.path}")
    private String uploadCrashFilePath;

    @Value("${upload.apk.path}")
    private String uploadApkPath;

    @Autowired
    private AccessLimtInterceptor accessLimtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimtInterceptor);
        super.addInterceptors(registry);
    }

    /**
     * 将D:\\upload下的文件映射到当前项目/upload/下
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");

        String os = System.getProperty("os.name");
        System.out.println("os = " + os);
        System.out.println("os.name = "+os.toLowerCase().startsWith("win"));

        if (os.toLowerCase().startsWith("win")){

        }

        //  /upload/crash/
        //addResourceHandler()里配置需要映射的文件夹，此处代表映射文件夹user下的所有资源。
        //addResourceLocations()配置文件夹在系统中的路径，使用绝对路径，格式为“file:你的路径/” 后面的 / 必须加上，否则映射失效
        registry.addResourceHandler(uploadImagePath + "**").addResourceLocations("file:"+ uploadImagePath);
        registry.addResourceHandler(uploadCrashFilePath + "**").addResourceLocations("file:"+ uploadCrashFilePath);
        registry.addResourceHandler(uploadApkPath + "**").addResourceLocations("file:"+ uploadApkPath);
    }
}
