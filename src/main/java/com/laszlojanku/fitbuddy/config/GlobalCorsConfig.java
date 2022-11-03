package com.laszlojanku.fitbuddy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class GlobalCorsConfig {
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
        	@Override
        	public void addCorsMappings(CorsRegistry registry) {        		
        		registry.addMapping("/**")        			
        			.allowedOrigins("http://localhost:8080", "https://fitbuddy-demo.herokuapp.com"); //Shouldnt be hardcoded
            }
        };
    }

}
