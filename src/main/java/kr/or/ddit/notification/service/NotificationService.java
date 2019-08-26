package kr.or.ddit.notification.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.notification.dao.INotificationDao;
import kr.or.ddit.notification.model.NotificationReciverVo;
import kr.or.ddit.notification.model.NotificationVo;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.receiver.model.ReceiverVo;

@Service
public class NotificationService implements INotificationService{
	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
	@Resource(name="notificationDao")
	private INotificationDao notiDao;
	
	@Override
	public Map<String, Object> notifiList(PageVo pageVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("notifiList", notiDao.notifiList(pageVo));
		
		logger.debug("!@# pageVo.getRcv_email() : {}",pageVo.getRcv_email());
		
		int notifiCnt = notiDao.notifiCnt(pageVo.getRcv_email());
		int notifiPageSize = (int) Math.ceil((double)notifiCnt/pageVo.getPageSize());
		resultMap.put("notifiPageSize", notifiPageSize);
		
		return resultMap;
	}

	@Override
	public int notifiCnt(String rcv_email) {
		return notiDao.notifiCnt(rcv_email);
	}

	@Override
	public int insertNotifi(NotificationVo notiVo) {
		return notiDao.insertNotifi(notiVo);
	}

	@Override
	public int insertReceiver(ReceiverVo receiverVo) {
		return notiDao.insertReceiver(receiverVo);
	}

	@Override
	public int deleteReceiver(int not_id) {
		return notiDao.deleteReceiver(not_id);
	}

	@Override
	public int deleteNotification(int not_id) {
		return notiDao.deleteNotification(not_id);
	}

}
