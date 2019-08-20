package kr.or.ddit.friend_req.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.config.spring.ApplicationDatasource;
import kr.or.ddit.config.spring.ApplicationTransaction;
import kr.or.ddit.config.spring.RootContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationDatasource.class,
								 ApplicationTransaction.class,
								 RootContext.class})
public class Friend_ReqServiceTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
