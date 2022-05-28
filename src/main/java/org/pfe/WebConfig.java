package org.pfe;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("admin_home_page").setViewName("filieres");
		registry.addViewController("prof_home_page").setViewName("cours");
	}
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path userUploadDir=Paths.get("./user-images");
		String userUploadPath=userUploadDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/user-images/**").addResourceLocations("file:/"+userUploadPath+"/");
	}
	
	

}
