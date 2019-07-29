package kr.or.ddit.user.service;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.config.spring.ApplicationDatasource;
import kr.or.ddit.config.spring.ApplicationTransaction;
import kr.or.ddit.config.spring.RootContext;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.users.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationDatasource.class,
								 ApplicationTransaction.class,
								 RootContext.class})
public class UserServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
	
	@Resource(name = "userService")
	private IUserService userService;

	/**
	 * 
	* Method : inserUserTest
	* 작성자 : 김경호
	* 변경이력 : 2019-07-23
	* Method 설명 : 일반 사용자 등록 테스트
	 */
	@Test
	public void inserUserTest() {
		/***Given***/
		UserVo userVo = new UserVo("kimkang624@naver.com","kkim0624","김경호","010-8253-3971","개발자","","U","N","","");
		
		/***When***/
		int insertCnt = userService.insertUser(userVo);
		/***Then***/
		assertEquals(1, insertCnt);
		
		userService.deleteUser(userVo.getUser_email());
	}
	
	/**
	 * 
	* Method : getUserTest
	* 작성자 : 김경호
	* 변경이력 : 2019-07-22
	* Method 설명 : 사용자 정보 조회 테스트
	 */
	@Test
	public void getUserTest() {
		/***Given***/
		String user_email = "galbi@naver.com";
		/***When***/
		UserVo userVo = userService.getUser(user_email);
		logger.debug("userVo : {}", userVo);
		/***Then***/
		assertEquals("김갈비", userVo.getUser_nm());
		assertEquals("engineer", userVo.getUser_job());
	}
	
}
