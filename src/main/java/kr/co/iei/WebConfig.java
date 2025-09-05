package kr.co.iei;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.iei.util.AdminInterceptor;
import kr.co.iei.util.LoginIntercepter;


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
		
		registry.addResourceHandler("/product/**")
		.addResourceLocations("file:///"+root+"/product/");
		
		registry.addResourceHandler("/customer/**")
		.addResourceLocations("file:///"+root+"/customer/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginIntercepter())
				.addPathPatterns(
							"/customer/listGj",
							"/customer/listComplain",
							"/customer/listOpinion",
							"/customer/writeFrm",
							"/customer/view",
							"/customer/updateFrm"
						)
				.excludePathPatterns(
							"/customer/list"
							
						);
		registry.addInterceptor(new AdminInterceptor())
				.addPathPatterns("/admin/**");
	}
	
	
	
	
}
