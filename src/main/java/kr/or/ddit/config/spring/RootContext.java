package kr.or.ddit.config.spring;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

// kr/or/ddit/config/spring/root-context.xml
@ComponentScan(basePackages = {"kr.or.ddit"},
				useDefaultFilters = false, 
				includeFilters = {@Filter(type=FilterType.ANNOTATION, classes = {Service.class, Repository.class })})
public class RootContext {

	@Bean
	public JavaMailSenderImpl javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com"); 					// 호스트 설정
		mailSender.setPort(587);								// 포트 설정
		mailSender.setDefaultEncoding("UTF-8"); 				// 인코딩 설정
		mailSender.setUsername("notification.sttm@gmail.com");  // 사용자 아이디 설정
		mailSender.setPassword("java201901");					// 사용자 비밀번호 설정 
		mailSender.setProtocol("smtp");							// 프로토콜 설정
		
		Properties javaMailProp = new Properties();				// 기타 설정용 Properties 파일 생성 
		javaMailProp.setProperty("mail.smtp.auth", "true");		
		javaMailProp.setProperty("mail.smtp.starttls.enable", "true");	
		javaMailProp.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com"); ////////////////////
		mailSender.setJavaMailProperties(javaMailProp);			// 기타 설정용 Properties 파일 주입
		
		return mailSender;										// Bean 반환
	}
	
}
