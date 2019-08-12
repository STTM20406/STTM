package kr.or.ddit.note_info.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.note_info.model.NoteTotalVo;
import kr.or.ddit.note_info.model.Note_InfoVo;
import kr.or.ddit.paging.model.PageVo;

@Repository
public class Note_InfoDao implements INote_InfoDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<NoteTotalVo> rcvList(PageVo pageVo) {
		return sqlSession.selectList("note.rcvList",pageVo);
	}

	@Override
	public int rcvCnt() {
		return sqlSession.selectOne("note.rcvCnt");
	}

	@Override
	public List<NoteTotalVo> sendList(PageVo pageVo) {
		return sqlSession.selectList("note.sendList", pageVo);
	}

	@Override
	public int sendCnt() {
		return sqlSession.selectOne("note.sendCnt");
	}

	@Override
	public int insertNoteContent(String content) {
		return sqlSession.insert("note.insertContent", content);
	}

	@Override
	public int insertNoteInfo(Note_InfoVo noteInfo) {
		return sqlSession.insert("note.insertInfo",noteInfo);
	}

}
