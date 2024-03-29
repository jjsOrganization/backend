package com.jjs.ClothingInventorySaleReformPlatform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://3.38.128.50:3000", "http://3.38.128.50/","http://localhost:3000")
                .allowedMethods("OPTIONS","GET","POST","PUT","DELETE");
    }
}
