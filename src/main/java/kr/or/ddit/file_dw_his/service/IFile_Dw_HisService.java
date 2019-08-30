package kr.or.ddit.file_dw_his.service;

import java.util.Map;

import kr.or.ddit.file_dw_his.model.File_Dw_HisVo;

public interface IFile_Dw_HisService {

	
	/**
	 * Method 		: historyPagination
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @return
	 * Method 설명 	: 다운 로드 기록 pagination 
	 */
	Map<String , Object> historyPagination(Map<String , Object> map);
	
	/**
	 * Method 		: insertHistory
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-30 최초 생성
	 * @param file_Dw_HisVo
	 * @return
	 * Method 설명 	: 다운로드 기록 insert
	 */
	int insertHistory(File_Dw_HisVo file_Dw_HisVo);
}
