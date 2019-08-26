package kr.or.ddit.filter.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.filter.model.FilterVo;
import kr.or.ddit.project.model.ProjectVo;
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
	
	/**
	 * Method : getWork
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-31 최초 생성
	 * @param wrk_id
	 * @return
	 * Method 설명 : 특정 업무정보를 담은 WorkVo를 반환하는 메서드
	 */
	WorkVo getWork(int wrk_id);
	
	/**
	 * Method : projectOverviewJSON
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-05 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 특정 프로젝트의 업무 개요정보를 반환하는 메서드
	 */
	Map<String, Object> projectOverviewJSON(FilterVo filterVo);
	
	/**
	 * Method : calendarTemplateJSON
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-07 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 캘린더 화면에 필요한 데이터를 JSON 형태로 반환하는 메서드
	 */
	Map<String, Object> calendarTemplateJSON(FilterVo filterVo);

	/**
	 * Method : updatePrj
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-08 최초 생성
	 * @param prjVo
	 * @return
	 * Method 설명 : 프로젝트 개요 화면에서 날짜 변경시 DB 정보를 업데이트 하는 메서드
	 */
	ProjectVo updatePrj(ProjectVo prjVo);
	
	/**
	 * Method : prjList
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-09 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 사용자가 참여하고 있는 프로젝트 리스트를 반환하는 메서드
	 */
	List<String> prjList(String user_email);
}
