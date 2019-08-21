package kr.or.ddit.work_mem_flw.service;

import java.util.List;

import kr.or.ddit.work_mem_flw.model.Work_Mem_FlwVo;

public interface IWork_Mem_FlwService {
	
	/**
	 * 
	 * Method 			: insertWorkMemFlw
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-21 최초 생성
	 * @param work_mem_flwVo
	 * @return
	 * Method 설명 		: 해당 업무에 배정된 멤버 추가
	 */
	int insertWorkMemFlw(Work_Mem_FlwVo work_mem_flwVo);
	
	
	/**
	 * 
	 * Method 			: workMemFlwList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-21 최초 생성
	 * @param work_mem_flwVo
	 * @return
	 * Method 설명 		: 업무에 해당하는 멤버 / 팔로워 조회
	 */
	List<Work_Mem_FlwVo> workMemFlwList(Work_Mem_FlwVo work_mem_flwVo);
	
	
	/**
	 * 
	 * Method 			: deleteWorkMemFlw
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-21 최초 생성
	 * @param work_mem_flwVo
	 * @return
	 * Method 설명 		: 업무에 해당하는 멤버 / 팔로워 삭제
	 */
	int deleteWorkMemFlw(Work_Mem_FlwVo work_mem_flwVo);
	
}
