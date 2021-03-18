package com.wzr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 1.添加配置文件注解
public class UploadConfig implements WebMvcConfigurer { // 2.实现WebMvcConfigurer接口
    //    @Value("${img.path}")
    private String locationPath = "E:/IntelliJ IDEA 2020.2.2/workspace/NoteView_PROJ/target/classes/static/imageUpload/"; // 3.文件本地路径
    private static final String netPath = "/image/**"; // 映射路径
    // 目前发现如果本地路径不是以分隔符结尾，在访问时否需要把在最后一个文件夹名添加在映射路径后面
    // 如：
    // locationPath-->F:\img\   	访问路径-->ip:port/img/1.png
    // locationPath-->F:\img   		访问路径-->ip:port/img/img/1.png
    // locationPath-->F:\img\123\ 	访问路径-->ip:port/img/1.png
    // locationPath-->F:\img\123  	访问路径-->ip:port/img/123/1.png

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 目前在本地Win系统测试需要在本地路径前添加 "file:"
        // 有待确认Linux系统是否需要添加（已确认）
        // Linux系统也可以添加 "file:"
        registry.addResourceHandler(netPath).addResourceLocations("file:"+locationPath);
    }
}
