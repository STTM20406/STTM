package kr.or.ddit.work_al_mem.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.work_al_mem.model.Work_Al_MemVo;

@Repository
public class Work_Al_MemDao implements IWork_Al_MemDao{

	@Resource(name = "sqlSession")
	SqlSessionTemplate sqlSession;
	
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
		return sqlSession.insert("work.insertWorkReservationMem", work_al_memVo);
	}

}
