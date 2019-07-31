package kr.or.ddit.work_comment.service;

import java.util.List;

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
	List<Work_CommentVo> commentList(Work_CommentVo commentVo);
}
