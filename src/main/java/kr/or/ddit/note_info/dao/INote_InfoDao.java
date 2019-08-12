package kr.or.ddit.note_info.dao;

import java.util.List;

import kr.or.ddit.note_info.model.NoteTotalVo;
import kr.or.ddit.note_info.model.Note_InfoVo;
import kr.or.ddit.paging.model.PageVo;

public interface INote_InfoDao {
	
	/**
	 * Method 		: rcvList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @param pageVo
	 * @return
	 * Method 설명 	: 받은 쪽지리스트
	 */
	List<NoteTotalVo> rcvList(PageVo pageVo);
	
	/**
	 * Method 		: rcvCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @return
	 * Method 설명 	: 받은 쪽지개수
	 */
	int rcvCnt();
	
	/**
	 * Method 		: sendList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @param pageVo
	 * @return
	 * Method 설명 	: 보낸 쪽지리스트
	 */
	List<NoteTotalVo> sendList(PageVo pageVo);
	
	/**
	 * Method 		: sendCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @return
	 * Method 설명 	: 보낸 쪽지개수
	 */
	int sendCnt();
	
	/**
	 * Method 		: insertNoteContent
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @param content
	 * @return
	 * Method 설명 	: 쪽지 내용보내기
	 */
	int insertNoteContent(String content);
	
	/**
	 * Method 		: insertNoteInfo
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-12 최초 생성
	 * @param noteInfo
	 * @return
	 * Method 설명 	: 쪽지 상세정보
	 */
	int insertNoteInfo(Note_InfoVo noteInfo);
	
}
