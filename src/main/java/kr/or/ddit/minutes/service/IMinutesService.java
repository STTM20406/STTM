package kr.or.ddit.minutes.service;

import java.util.List;

import kr.or.ddit.minutes.model.MinutesVo;

public interface IMinutesService {
	/**
	 * Method 		: minutesList
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 	: 해당 프로젝트에 프로젝트 리스트를 받아오는 메서드
	 */
	List<MinutesVo> minutesList(int prj_id);
	
}
