package kr.or.ddit.note_info.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.note_content.model.Note_ContentVo;
import kr.or.ddit.note_info.model.NoteTotalVo;
import kr.or.ddit.note_info.model.Note_InfoVo;
import kr.or.ddit.paging.model.PageVo;

public interface INote_InfoService {
	/**
	 * Method 		: rcvList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @param pageVo
	 * @return
	 * Method 설명 	: 받은 쪽지리스트
	 */
	Map<String, Object> rcvList(PageVo pageVo);
	
	/**
	 * Method 		: rcvCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @return
	 * Method 설명 	: 받은 쪽지개수
	 */
	int rcvCnt(String user_email);
	
	/**
	 * Method 		: sendList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @param pageVo
	 * @return
	 * Method 설명 	: 보낸 쪽지리스트
	 */
	Map<String, Object> sendList(PageVo pageVo);
	
	/**
	 * Method 		: sendCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @return
	 * Method 설명 	: 보낸 쪽지개수
	 */
	int sendCnt(String user_email);
	
	/**
	 * Method 		: insertNoteContent
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @param content
	 * @return
	 * Method 설명 	: 쪽지 내용보내기
	 */
	int insertNoteContent(Note_ContentVo conVo);
	
	/**
	 * Method 		: insertNoteInfo
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @param noteInfo
	 * @return
	 * Method 설명 	: 쪽지 상세정보
	 */
	int insertNoteInfo(Note_InfoVo noteInfo);
	
	/**
	 * Method 		: rcvDel
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-16 최초 생성
	 * @param note_con_id
	 * @return
	 * Method 설명 	: 수신자 쪽지 삭제(컬럼값만 변경)
	 */
	int rcvDel(int note_con_id);
}
