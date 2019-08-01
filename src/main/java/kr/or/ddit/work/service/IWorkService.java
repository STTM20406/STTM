package kr.or.ddit.work.service;

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
}
