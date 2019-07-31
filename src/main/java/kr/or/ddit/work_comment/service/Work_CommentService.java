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
	
	@Override
	public int commentInsert(Work_CommentVo commentVo) {
		return commentDao.commInsert(commentVo);
	}

	@Override
	public List<Work_CommentVo> commentList(Work_CommentVo commentVo) {
		return commentDao.commentList(commentVo);
	}

}
