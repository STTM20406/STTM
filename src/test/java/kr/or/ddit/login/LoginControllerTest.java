package kr.or.ddit.login;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import kr.or.ddit.config.spring.ApplicationDatasource;
import kr.or.ddit.config.spring.ApplicationTransaction;
import kr.or.ddit.config.spring.RootContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationDatasource.class,
								 ApplicationTransaction.class,
								 RootContext.class})
public class LoginControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);
	
	@Test
	public void loginProcessTest() {
		
		/***Given***/
		String user_email = "galbi@naver.com";
		String user_pass = "galbi1234";
		
		/***When***/
//		MvcResult mvcResult = mockMvc.per
		
		
		/***Then***/

	}

}
