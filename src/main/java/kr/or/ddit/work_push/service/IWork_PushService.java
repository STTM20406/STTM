package kr.or.ddit.work_push.service;

import kr.or.ddit.work_push.model.Work_PushVo;

public interface IWork_PushService {
	
	/**
	 * 
	 * Method 			: insertWorkReservation
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-23 최초 생성
	 * @param work_pushVo
	 * @return
	 * Method 설명 		: 예약 알림 생성
	 */
	int insertWorkReservation(Work_PushVo work_pushVo);
	
	/**
	 * 
	 * Method 			: updateWorkReservation
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-23 최초 생성
	 * @return
	 * Method 설명 		: 업무 예약 알림 삭제(플래그 업데이트)
	 */
	int deleteWorkReservation(int wrk_rv_id);
	
	/**
	 * 
	 * Method 			: getWorkReservation
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-23 최초 생성
	 * @param Work_PushVo
	 * @return
	 * Method 설명 		: 업무 예약 알림 조회
	 */
	Work_PushVo getWorkReservation(Work_PushVo work_PushVo);

}
