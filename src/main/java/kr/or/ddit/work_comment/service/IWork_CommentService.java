package kr.or.ddit.work_comment.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.work_comment.model.Work_CommentVo;

public interface IWork_CommentService {
	
	/**
	 * Method 		: commentInsert
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @param commentVo
	 * @return
	 * Method 설명 	: 업무코멘트 등록
	 */
	int commentInsert(Work_CommentVo commentVo);
	
	/**
	 * Method 		: commentList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @return
	 * Method 설명 	: 업무코멘트 리스트
	 */
	Map<String, Object> commentList(PageVo pageVo);
	
	/**
	 * Method 		: commUpdate
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-01 최초 생성
	 * @param commentVo
	 * @return
	 * Method 설명 	: 업무코멘트 수정
	 */
	int commUpdate(Work_CommentVo commentVo);
	
	/**
	 * Method 		: commDelete
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-01 최초 생성
	 * @param commentVo
	 * @return
	 * Method 설명 	: 업무코멘트 삭제(실제 데이터는 존재하고 컬럼 값만 변경된다.)
	 */
	int commDelete(Work_CommentVo commentVo);
}
