package kr.co.iei;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	@Value(value="${file.root}")
	private String root;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/templates/","classpath:/static/");

		registry.addResourceHandler("/news/image/**")
				.addResourceLocations("file:///"+root+"/news/");
		
		registry.addResourceHandler("/banner/image/**")
		.addResourceLocations("file:///"+root+"/banner/");
	}
	
	
	
}
