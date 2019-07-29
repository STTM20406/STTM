package kr.or.ddit.chat_room;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.chat_room.dao.IChat_RoomDao;
import kr.or.ddit.chat_room.model.Chat_RoomVo;
import kr.or.ddit.config.spring.ApplicationDatasource;
import kr.or.ddit.config.spring.ApplicationTransaction;
import kr.or.ddit.config.spring.RootContext;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:kr/or/ddit/config/spring/root-context.xml",
//	"classpath:kr/or/ddit/config/spring/application-datasource-dev.xml",
//	"classpath:kr/or/ddit/config/spring/application-transaction.xml"})
@ContextConfiguration(classes= {RootContext.class, ApplicationDatasource.class,ApplicationTransaction.class})
public class chat_roomDaoTest {

	@Resource(name="chat_RoomDao")
	private IChat_RoomDao roomDao ;
	
	private static final Logger logger = LoggerFactory.getLogger(chat_roomDaoTest.class);
	
	@Test
	public void getRoomListTest() {
		/***Given***/
		

		/***When***/
		List<Chat_RoomVo> list = roomDao.getRoomList("dubu@naver.com");

		logger.debug("list: {}", list);
		/***Then***/
		assertEquals(list.get(0).getCt_nm(), "채팅방2");
	}

}
