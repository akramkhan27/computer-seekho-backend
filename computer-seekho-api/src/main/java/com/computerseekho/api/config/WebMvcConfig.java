package com.computerseekho.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static images from /assets/staff/
        registry.addResourceHandler("/assets/staff/**")
                .addResourceLocations("classpath:/static/assets/staff/")
                .setCachePeriod(3600);

        System.out.println("✅ Static resource handler configured for /assets/staff/");
    }
}