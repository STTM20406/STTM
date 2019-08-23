package kr.or.ddit.notification.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.notification.model.NotificationReciverVo;
import kr.or.ddit.notification.model.NotificationVo;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.receiver.model.ReceiverVo;

public interface INotificationService {
	
	Map<String, Object> notifiList(PageVo pageVo);
	
	int notifiCnt(String rcv_email);
	
	int insertNotifi(NotificationVo notiVo);
	
	int insertReceiver(ReceiverVo receiverVo);
}
