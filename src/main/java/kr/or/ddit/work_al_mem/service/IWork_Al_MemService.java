package kr.or.ddit.work_al_mem.service;

import kr.or.ddit.work_al_mem.model.Work_Al_MemVo;

public interface IWork_Al_MemService {
	
	/**
	 * 
	 * Method 			: insertWorkReservation
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-23 최초 생성
	 * @param work_al_memVo
	 * @return
	 * Method 설명 		: 업무 예약 알림 생성시 알림 받을 멤버 insert
	 */
	int insertWorkReservationMem(Work_Al_MemVo work_al_memVo);

}
