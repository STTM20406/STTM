package kr.or.ddit.work_push.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.work_push.model.Work_PushVo;

@Repository
public class Work_PushDao implements IWork_PushDao{

	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession;
	
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
		return sqlSession.insert("work.insertWorkReservation", work_pushVo);
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
		return sqlSession.update("work.deleteWorkReservation", wrk_rv_id);
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
		return sqlSession.selectOne("work.getWorkReservation", work_PushVo);
	}

}
