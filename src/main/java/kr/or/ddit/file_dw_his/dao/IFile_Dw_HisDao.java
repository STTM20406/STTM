package kr.or.ddit.file_dw_his.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.file_dw_his.model.File_Dw_HisVo;

public interface IFile_Dw_HisDao {

	/**
	 * Method 		: historyPagination
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @return
	 * Method 설명 	: 다운 로드 기록 pagination 
	 */
	List<File_Dw_HisVo> historyPagination(Map<String , Object> map);
	
	
	/**
	 * Method 		: historyCnt
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 	: 다운로드 기록 Pagination Cnt
	 */
	int historyCnt(int prj_id);
}
