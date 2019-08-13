package kr.or.ddit.file_dw_his.service;

import java.util.Map;

public interface IFile_Dw_HisService {

	
	/**
	 * Method 		: historyPagination
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @return
	 * Method 설명 	: 다운 로드 기록 pagination 
	 */
	Map<String , Object> historyPagination(Map<String , Object> map);
}
