package com.trc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) 
	{
		registry
	        .addResourceHandler("/pbb/clients/**","/pbb/website/**") 
	        .addResourceLocations("file:c:\\Public\\pbbDocs\\clients\\","file:c:\\Public\\pbbDocs\\website\\")
	        .setCachePeriod(0);
		
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() 
	{
        return new BCryptPasswordEncoder();
    }
	
	
	
}
