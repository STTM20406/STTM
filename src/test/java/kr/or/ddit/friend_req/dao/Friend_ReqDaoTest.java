package kr.or.ddit.friend_req.dao;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.config.spring.ApplicationDatasource;
import kr.or.ddit.config.spring.ApplicationTransaction;
import kr.or.ddit.config.spring.RootContext;
import kr.or.ddit.friend_req.model.Friend_ReqVo;
import kr.or.ddit.users.model.UserVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationDatasource.class,
								 ApplicationTransaction.class,
								 RootContext.class})
public class Friend_ReqDaoTest {
	
	@Resource(name = "friend_ReqDao")
	private IFriend_ReqDao friend_ReqDao;
	
	@Test
	public void test() throws ParseException {
		
		/***Given***/
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
//		Friend_ReqVo friendReqDao = new Friend_ReqVo(1,"paging@google.com","galbi@naver.com",sdf.parse("2019-08-20"),"FR-02",sdf.parse("2019-08-20"));
		
		
		/***When***/

		/***Then***/

		
	}

}
