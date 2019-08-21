package kr.or.ddit.work_comment.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.work_comment.model.Work_CommentVo;

@Repository
public class Work_CommentDao implements IWork_CommentDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
	/**
	 * Method 		: commInsert
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @param commentVo
	 * @return
	 * Method 설명 	: 업무코멘트 등록
	 */
	@Override
	public int commInsert(Work_CommentVo commentVo) {
		return sqlSession.insert("work.commInsert",commentVo);
	}

	/**
	 * Method 		: commentList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @return
	 * Method 설명 	: 업무코멘트 리스트
	 */
	@Override
	public List<Work_CommentVo> commentList(PageVo pageVo) {
		return sqlSession.selectList("work.commList", pageVo);
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
		return sqlSession.update("work.commUpdate",commentVo);
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
		return sqlSession.update("work.commDelete",commentVo);
	}

	@Override
	public int commCnt(PageVo pageVo) {
		return sqlSession.selectOne("work.commCnt", pageVo);
	}

}
