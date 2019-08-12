package kr.or.ddit.user.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.project_mem.service.IProject_MemService;
import kr.or.ddit.user.service.UserServiceTest;
import kr.or.ddit.users.dao.IUserDao;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.users.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationDatasource.class,
								 ApplicationTransaction.class,
								 RootContext.class})
public class UserDaoTest {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
	
	@Resource(name = "userDao")
	private IUserDao userDao;
	
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
		int insertCnt = userDao.insertUser(userVo);
		/***Then***/
		assertEquals(1, insertCnt);
		
		userDao.deleteUser(userVo.getUser_email());
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
		UserVo userVo = userDao.getUser(user_email);
		logger.debug("userVo 갈비Vo : {}", userVo);
		
		/***Then***/
		assertEquals("김갈비", userVo.getUser_nm());
		assertEquals("engineer", userVo.getUser_job());
	}
	
	
	/**
	 * 
	* Method : updateUserPassTest
	* 작성자 : 김경호
	* 변경이력 : 2019-07-27
	* Method 설명 : 비밀번호 수정 테스트 
	 */
	@Test
	public void updateUserPassTest() {
	}
	
	/**
	 * 
	* Method : updateUserProfile
	* 작성자 : 김경호
	* 변경이력 : 2019-07-31
	* Method 설명 : 사용자 프로필 업데이트 테스트
	 */
	@Test
	public void updateUserProfileTest() {
		/***Given***/
		UserVo originVo = new UserVo("kkh624@nate.com","password","name","hp","job","token","right","st","path","filename");
		
		userDao.insertUser(originVo);
		
		UserVo userVo = new UserVo("kkh624@nate.com","pw","name","hp","job","token","right","st","path","filename");
		
		/***When***/
		int updateProfile = userDao.updateUserProfile(userVo);
		
		/***Then***/
		assertEquals(1, updateProfile);
		userDao.deleteUser(originVo.getUser_email());
	}

	/**
	 * 
	* Method : updateUserProfileTest2
	* 작성자 : 김경호
	* 변경이력 : 2019-07-31
	* Method 설명 : 사용자 프로필 업데이트 테스트 2 
	 */
	@Test
	public void updateUserProfileTest2() { // 업데이트 성공
		/***Given***/
		
		String user_email = "kkim0624@nate.com";
		/***When***/
		UserVo userVo = userDao.getUser(user_email);
		logger.debug("userVo : {} 테스트2",userVo);
		
		UserVo updateVo = new UserVo("kkim0624@nate.com","pw","name","hp","job","token","right","st","path","filename");
		
		int updateProfile = userDao.updateUserProfile(updateVo);
		
		/***Then***/

	}
	
}
