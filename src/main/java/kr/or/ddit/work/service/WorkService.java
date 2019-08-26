package kr.or.ddit.work.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.work.dao.IWorkDao;
import kr.or.ddit.work.model.WorkVo;

@Service
public class WorkService implements IWorkService{
	
	@Resource(name="workDao")
	IWorkDao workDao;
	
	@Override
	public int updateWork(WorkVo workVo) {
		return workDao.updateWork(workVo);
	}

	
	/**
	 * 
	 * Method 			: getWork
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-10 최초 생성
	 * @param wrk_lst_id
	 * @return
	 * Method 설명 		: 해당 프로젝트의 업무리스트에 포함된 업무 조회
	 */
	@Override
	public List<WorkVo> getWork(int wrk_lst_id) {
		return workDao.getWork(wrk_lst_id);
	}


	/**
	 * 
	 * Method 			: getWorkListCnt
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-15 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 해당 업무리스트의 완료된 업무와 진행중인 업무 카운트 조회
	 */
	@Override
	public WorkVo getWorkListCnt(WorkVo workVo) {
		return workDao.getWorkListCnt(workVo);
	}


	/**
	 * 
	 * Method 			: insertWork
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-15 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 해당 업무리스트에 업무 생성
	 */
	@Override
	public int insertWork(WorkVo workVo) {
		return workDao.insertWork(workVo);
	}


	/**
	 * 
	 * Method 			: updateWorkID
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-16 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 업무리스트 업무를 다른 업무리스트로 이동 시켰을 때 업무리스트 아이디 업데이트
	 */
	@Override
	public int updateWorkID(WorkVo workVo) {
		return workDao.updateWorkID(workVo);
	}

	
	/**
	 * 
	 * Method 			: getWorkInfo
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-17 최초 생성
	 * @param wrk_id
	 * @return
	 * Method 설명 		: 업무 아이디로 업무 정보 조회
	 */
	@Override
	public WorkVo getWorkInfo(int wrk_id) {
		return workDao.getWorkInfo(wrk_id);
	}

	/**
	 * Method : getWorkInfo
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-26 최초 생성
	 * @param wrk_id
	 * @param user_email
	 * @return
	 * Method 설명 : 업무 아이디와 사용자 이메일로 업무 정보 조회
	 */
	@Override
	public WorkVo getWorkInfo(int wrk_id, String user_email) {
		Map<String, Object> params = new HashMap<>();
		params.put("wrk_id", wrk_id);
		params.put("user_email", user_email);
		return workDao.getWorkInfo(params);
	}
	/**
	 * 
	 * Method 			: updateWorkCmp
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-18 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 업무 아이디로 업무 완료 여부 업데이트
	 */
	@Override
	public int updateWorkCmp(WorkVo workVo) {
		return workDao.updateWorkCmp(workVo);
	}


	/**
	 * 
	 * Method 			: updateAllWork
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-19 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 해당 업무 정보 전체 업데이트
	 */
	@Override
	public int updateAllWork(WorkVo workVo) {
		return workDao.updateAllWork(workVo);
	}


	/**
	 * 
	 * Method 			: updateWorkColor
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-20 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 해당 업무 라벨 컬러 업데이트
	 */
	@Override
	public int updateWorkColor(WorkVo workVo) {
		return workDao.updateWorkColor(workVo);
	}



	/**
	 * 
	 * Method 			: deleteWork
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-22 최초 생성
	 * @param wrk_id
	 * @return
	 * Method 설명 		:  업무 삭제 (플래그 업데이트)
	 */
	@Override
	public int deleteWork(int wrk_id) {
		return workDao.deleteWork(wrk_id);
	}


	/**
	 * 
	 * Method 			: updateResID
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-23 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 예약 알림 생성시 예약알림 아이디 업데이트
	 */
	@Override
	public int updateResID(WorkVo workVo) {
		return workDao.updateResID(workVo);
	}

}
