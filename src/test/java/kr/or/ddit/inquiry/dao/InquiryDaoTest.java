package kr.or.ddit.inquiry.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.bd_inquiry.dao.IBd_InquiryDao;
import kr.or.ddit.config.spring.ApplicationContext;
import kr.or.ddit.config.spring.ApplicationDatasource;
import kr.or.ddit.config.spring.ApplicationTransaction;
import kr.or.ddit.config.spring.RootContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContext.class, ApplicationDatasource.class, ApplicationTransaction.class, RootContext.class})
public class InquiryDaoTest {

	private static final Logger logger = LoggerFactory.getLogger(InquiryDaoTest.class);
	@Resource(name="bd_InquiryDao")
	private IBd_InquiryDao bd_InquiryDao;
	
	@Test
	public void inquiryCntTest() {
		/***Given***/
		

		/***When***/
		int cnt = bd_InquiryDao.inquiryCnt();
		logger.debug("!@# cnt : {}",cnt);
		/***Then***/
		assertTrue(cnt>=2);
	}

}
