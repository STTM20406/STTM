package kr.or.ddit.work_list.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.work_list.model.Work_ListVo;

public interface IWork_ListService {
	
	/**
	 * 
	 * Method 			: workList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-09 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 		: 해당 프로젝트의 업무 리스트 및 업무 조회
	 */
	List<Work_ListVo> workList(int prj_id);
	
	/**
	 * 
	 * Method 			: insertWorkList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-11 최초 생성
	 * @param workListVo
	 * @return
	 * Method 설명 		: 해당 프로젝트에 업무리스트 생성
	 */
	int insertWorkList(Work_ListVo workListVo);
	
	
	/**
	 * 
	 * Method 			: updateWorkList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-12 최초 생성
	 * @param wrk_lst_id
	 * @return
	 * Method 설명 		: 해당 프로젝트 업무리스트 이름 수정
	 */
	int updateWorkList(Work_ListVo workListVo);
	
	/**
	 * 
	 * Method 			: deleteWorkList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-12 최초 생성
	 * @param wrk_lst_id
	 * @return
	 * Method 설명 		: 해당 프로젝트 업무리스트 삭제 (플래그 업데이트)
	 */
	int deleteWorkList(int wrk_lst_id);

	/**
	 * 
	* Method : workListPagingList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-13
	* @param pageVo
	* @return
	* Method 설명 : 타이머 - 프로젝트에 세션정보를 받아와 해당 프로젝트의 업무리스트 조회
	 */
	Map<String, Object> timerWorkListPagingList(Map<String, Object> map);
	
}
