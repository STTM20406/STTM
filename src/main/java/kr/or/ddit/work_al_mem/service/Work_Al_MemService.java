package kr.or.ddit.work_al_mem.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.work_al_mem.dao.IWork_Al_MemDao;
import kr.or.ddit.work_al_mem.model.Work_Al_MemVo;

@Service
public class Work_Al_MemService implements IWork_Al_MemService{
	
	@Resource(name = "work_Al_MemDao")
	IWork_Al_MemDao workAlMemDao;

	/**
	 * 
	 * Method 			: insertWorkReservation
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-23 최초 생성
	 * @param work_al_memVo
	 * @return
	 * Method 설명 		: 업무 예약 알림 생성시 알림 받을 멤버 insert
	 */
	@Override
	public int insertWorkReservationMem(Work_Al_MemVo work_al_memVo) {
		return workAlMemDao.insertWorkReservationMem(work_al_memVo);
	}

}
