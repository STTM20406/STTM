package kr.or.ddit.filter.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.filter.model.FilterVo;
import kr.or.ddit.work.model.WorkVo;

public interface IFilterService {
	/**
	 * Method : filterList
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-23 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 검색 조건과 일치하는 업무 리스트 반환 메서드
	 */
	List<WorkVo> filterList(FilterVo filterVo);
	
	/**
	 * Method : filterListJSON
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-23 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 개인 업무 리스트 화면을 JSON 형태로 반환하는 메서드
	 */
	Map<String, Object> workListJSON(FilterVo filterVo);
	
	/**
	 * Method : workDetail
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-24 최초 생성
	 * @param wrk_id
	 * @return
	 * Method 설명 : 임시 생성한 특정 업무 상세정보 조회 메서드
	 */
	String workDetail(int wrk_id);
	
	/**
	 * Method : workListCalc
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-26 최초 생성
	 * @param workList
	 * @return
	 * Method 설명 : 제공된 업무 리스트의 통계를 계산하는 메서드
	 */
	Map<String, Object> workListCalc(List<WorkVo> workList);
	
	/**
	 * Method : ganttList
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-30 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 개인 간트 차트 화면과 데이터를 JSON형태로 반환하는 메서드
	 */
	Map<String, Object> ganttListJSON(FilterVo filterVo);
}
