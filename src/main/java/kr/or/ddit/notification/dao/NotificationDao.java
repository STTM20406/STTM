package kr.or.ddit.notification.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.notification.model.NotificationReciverVo;
import kr.or.ddit.notification.model.NotificationVo;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.receiver.model.ReceiverVo;

@Repository
public class NotificationDao implements INotificationDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<NotificationVo> notifiList(PageVo pageVo) {
		return sqlSession.selectList("notification.notifiList", pageVo);
		
	}

	@Override
	public int notifiCnt(String rcv_email) {
		return sqlSession.selectOne("notification.notifiCnt", rcv_email);
	}

	@Override
	public int insertNotifi(NotificationVo notiVo) {
		return sqlSession.insert("notification.insertNotification", notiVo);
	}

	@Override
	public int insertReceiver(ReceiverVo receiverVo) {
		return sqlSession.insert("notification.insertReceiver", receiverVo);
	}

	@Override
	public int deleteReceiver(int not_id) {
		return sqlSession.delete("notification.deleteReceiver", not_id);
	}

	@Override
	public int deleteNotification(int not_id) {
		return sqlSession.delete("notification.deleteNotification", not_id);
	}

}
