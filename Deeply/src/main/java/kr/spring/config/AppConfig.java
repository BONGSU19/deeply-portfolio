package kr.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

//자바코드 기반 설정 클래스
@EnableScheduling
@Configuration
public class AppConfig implements WebMvcConfigurer{
	@Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer configurer = 
				new TilesConfigurer();
		//XML 설정 파일 경로 지정
		configurer.setDefinitions(new String[] {
				"/WEB-INF/tiles-def/main.xml",
				"/WEB-INF/tiles-def/ms.xml",
				"/WEB-INF/tiles-def/hj.xml",
				"/WEB-INF/tiles-def/hr1.xml",
				"/WEB-INF/tiles-def/hr2.xml",
				"/WEB-INF/tiles-def/hy.xml",
				"/WEB-INF/tiles-def/sw.xml"
		});
		configurer.setCheckRefresh(true);
		return configurer;
	}
	@Bean
	public TilesViewResolver tilesViewResolver() {
		final TilesViewResolver tilesViewResolver = 
				new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		return tilesViewResolver;
	}
}







