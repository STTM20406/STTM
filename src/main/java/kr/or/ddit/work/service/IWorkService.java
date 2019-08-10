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
}
