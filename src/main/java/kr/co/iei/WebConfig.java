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
							"/member/logout",
							"/member/mypage",
							"/member/update",
							"/member/mypage2",
							"/member/update2",
							"/member/delete",
							"/customer/**",
							"/news/**"
							
//							
						)
				.excludePathPatterns(
							"/customer/filedown",
							"/customer/list",
							"/news/list",
							"/news/noticeView",
							"/news/view",
							"/news/bannerSelect",
							"/notice/editor/**",
							"/banana/image/**",
							"/product/**",
							"/news/image/**",
							"/news/list",
							"/news/noticeView",
							"/news/view",
							"/news/bannerSelect"
							
							
						);
		registry.addInterceptor(new AdminInterceptor())
				.addPathPatterns("/admin/**");
	}
	
	
	
	
}
