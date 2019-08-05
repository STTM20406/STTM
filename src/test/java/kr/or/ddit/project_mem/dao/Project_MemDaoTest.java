package kr.or.ddit.project_mem.dao;

import java.util.List;

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
import kr.or.ddit.project_mem.model.Project_MemVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationDatasource.class,
								 ApplicationTransaction.class,
								 RootContext.class})
public class Project_MemDaoTest {
	
	private static final Logger logger = LoggerFactory.getLogger(Project_MemDaoTest.class);
	
	@Resource(name = "project_MemDao")
	private IProject_MemDao prjMemDao;
	
	/**
	 * 
	* Method : getMyProjectMemberList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-01
	* Method 설명 : 휴면 계정으로 전환하기 위하여 나의 프로젝트 멤버를 조회
	 */
	@Test
	public void getMyProjectMemberList() {
		/***Given***/
		int prj_id = 1;
		
		/***When***/
		List<Project_MemVo> prjMemList = prjMemDao.getMyProjectMemList(prj_id);
		logger.debug("prjMemList : {} 가져옵시다",prjMemList); 
		
		/***Then***/
		
	}

}
