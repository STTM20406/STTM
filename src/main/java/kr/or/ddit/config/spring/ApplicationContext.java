package kr.or.ddit.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import kr.or.ddit.util.HandShakeInterceptor;
import kr.or.ddit.util.WebSocket;

@ComponentScan(basePackages = {"kr.or.ddit"}
			, useDefaultFilters = false
			, includeFilters = {@Filter(type = FilterType.ANNOTATION, classes= {Controller.class, ControllerAdvice.class})} )
@EnableWebMvc // <mvc:annotation-driven/>
@EnableWebSocket
@Configuration
//@ImportResource(locations = {"classpath:kr/or/ddit/config/spring/application-websocket.xml"})
public class ApplicationContext extends WebMvcConfigurerAdapter implements WebSocketConfigurer{
	
	//<mvc:default-servlet-handler/>
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean
	public ViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver irvr 
		= new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
		irvr.setOrder(3);
		return irvr;
	}
	
	@Bean
	public ViewResolver beanNameViewResolver() {
		BeanNameViewResolver bnvr = new BeanNameViewResolver();
		bnvr.setOrder(2);
		return bnvr;
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("classpath:kr/or/ddit/config/tiles/tiles-config.xml");
		return tilesConfigurer;
	}
	
	@Bean 
	public ViewResolver tilesViewResolver() {
		TilesViewResolver tvr = new TilesViewResolver();
		tvr.setOrder(1);
		tvr.setViewClass(TilesView.class);
		return tvr;
	}
	
	@Bean
	public View jsonView() {
		return new MappingJackson2JsonView();
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver cmr = new CommonsMultipartResolver();
		cmr.setMaxUploadSize(1024*1024*3*5);
		cmr.setMaxUploadSizePerFile(1024*1024*3);
		return cmr;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(socketHandler(), "/echo.do")
			.setAllowedOrigins("*") //어떤 도메인이든 상관없이 처리
			.addInterceptors(new HandShakeInterceptor())
			.withSockJS();
	}
	
	@Bean
	public WebSocket socketHandler() {
		return new WebSocket();
	}
	
	
//	@Bean
//	public HttpSessionHandshakeInterceptor httpSessionHandshakeInterceptor() {
//		HttpSessionHandshakeInterceptor a = new 
//		
//	}
	
	
}
