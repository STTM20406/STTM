package kr.or.ddit.work_list.dao;

import java.util.List;

import kr.or.ddit.work_list.model.Work_ListVo;

public interface IWork_ListDao {

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
}
