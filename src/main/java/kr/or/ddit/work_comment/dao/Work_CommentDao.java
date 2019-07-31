package kr.or.ddit.work_comment.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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

	@Override
	public List<Work_CommentVo> commentList(Work_CommentVo commentVo) {
		return sqlSession.selectList("work.commList", commentVo);
	}

}
