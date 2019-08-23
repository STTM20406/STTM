package kr.or.ddit.notification.dao;

import java.util.List;

import javax.annotation.Resource;

import kr.or.ddit.notification.model.NotificationReciverVo;
import kr.or.ddit.notification.model.NotificationVo;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.receiver.model.ReceiverVo;

public interface INotificationDao {
	
	List<NotificationVo> notifiList(PageVo pageVo);
	
	int notifiCnt(String rcv_email);
	
	int insertNotifi(NotificationVo notiVo);
	
	int insertReceiver(ReceiverVo receiverVo);
	
	
	
}
