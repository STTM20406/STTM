package kr.or.ddit.work_push.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.work_push.dao.IWork_PushDao;
import kr.or.ddit.work_push.model.Work_PushVo;

@Service
public class Work_PushService implements IWork_PushService{

	@Resource(name = "work_PushDao")
	IWork_PushDao workPushDao;
	
	/**
	 * 
	 * Method 			: insertWorkReservation
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-23 최초 생성
	 * @param work_pushVo
	 * @return
	 * Method 설명 		: 예약 알림 생성
	 */
	@Override
	public int insertWorkReservation(Work_PushVo work_pushVo) {
		return workPushDao.insertWorkReservation(work_pushVo);
	}

	
	/**
	 * 
	 * Method 			: updateWorkReservation
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-23 최초 생성
	 * @return
	 * Method 설명 		: 업무 예약 알림 삭제(플래그 업데이트)
	 */
	@Override
	public int deleteWorkReservation(int wrk_rv_id) {
		return workPushDao.deleteWorkReservation(wrk_rv_id);
	}


	/**
	 * 
	 * Method 			: getWorkReservation
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-23 최초 생성
	 * @param Work_PushVo
	 * @return
	 * Method 설명 		: 업무 예약 알림 조회
	 */
	@Override
	public Work_PushVo getWorkReservation(Work_PushVo work_PushVo) {
		return workPushDao.getWorkReservation(work_PushVo);
	}

}
