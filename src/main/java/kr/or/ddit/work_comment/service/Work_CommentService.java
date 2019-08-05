package kr.or.ddit.work_comment.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.work_comment.dao.IWork_CommentDao;
import kr.or.ddit.work_comment.model.Work_CommentVo;

@Service
public class Work_CommentService implements IWork_CommentService{

	@Resource(name="work_CommentDao")
	private IWork_CommentDao commentDao;
	
	/**
	 * Method 		: commentInsert
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @param commentVo
	 * @return
	 * Method 설명 	: 업무코멘트 등록
	 */
	@Override
	public int commentInsert(Work_CommentVo commentVo) {
		return commentDao.commInsert(commentVo);
	}

	/**
	 * Method 		: commentList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @return
	 * Method 설명 	: 업무코멘트 리스트
	 */
	@Override
	public List<Work_CommentVo> commentList(Work_CommentVo commentVo) {
		return commentDao.commentList(commentVo);
	}

	/**
	 * Method 		: commUpdate
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-01 최초 생성
	 * @param commentVo
	 * @return
	 * Method 설명 	: 업무코멘트 수정
	 */
	@Override
	public int commUpdate(Work_CommentVo commentVo) {
		return commentDao.commUpdate(commentVo);
	}

	/**
	 * Method 		: commDelete
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-01 최초 생성
	 * @param commentVo
	 * @return
	 * Method 설명 	: 업무코멘트 삭제(실제 데이터는 존재하고 컬럼 값만 변경된다.)
	 */
	@Override
	public int commDelete(Work_CommentVo commentVo) {
		return commentDao.commDelete(commentVo);
	}

}
