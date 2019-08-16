package kr.or.ddit.work.service;

import java.util.List;

import kr.or.ddit.work.model.WorkVo;

public interface IWorkService {
	/**
	 * Method : updateWork
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-31 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 : 업무 정보 업데이트 메서드
	 */
	int updateWork(WorkVo workVo);
	
	
	/**
	 * 
	 * Method 			: getWork
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-10 최초 생성
	 * @param wrk_lst_id
	 * @return
	 * Method 설명 		: 해당 프로젝트의 업무리스트에 포함된 업무 조회
	 */
	List<WorkVo> getWork(int wrk_lst_id);
	
	
	/**
	 * 
	 * Method 			: getWorkListCnt
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-15 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 해당 업무리스트의 완료된 업무와 진행중인 업무 카운트 조회
	 */
	WorkVo getWorkListCnt (WorkVo workVo);
	
	
	/**
	 * 
	 * Method 			: insertWork
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-15 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 해당 업무리스트에 업무 생성
	 */
	int insertWork(WorkVo workVo);
	
	
	/**
	 * 
	 * Method 			: updateWorkID
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-16 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 업무리스트 업무를 다른 업무리스트로 이동 시켰을 때 업무리스트 아이디 업데이트
	 */
	int updateWorkID(WorkVo workVo);
}
