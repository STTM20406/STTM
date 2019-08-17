package kr.or.ddit.note_info.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.note_content.model.Note_ContentVo;
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
	public int rcvCnt(String user_email) {
		return sqlSession.selectOne("note.rcvCnt",user_email);
	}

	@Override
	public List<NoteTotalVo> sendList(PageVo pageVo) {
		return sqlSession.selectList("note.sendList", pageVo);
	}

	@Override
	public int sendCnt(String user_email) {
		return sqlSession.selectOne("note.sendCnt",user_email);
	}

	@Override
	public int insertNoteContent(Note_ContentVo conVo) {
		return sqlSession.insert("note.insertContent", conVo);
	}

	@Override
	public int insertNoteInfo(Note_InfoVo noteInfo) {
		return sqlSession.insert("note.insertInfo",noteInfo);
	}

	@Override
	public int rcvDel(int note_con_id) {
		return sqlSession.update("note.rcvDel",note_con_id);
	}

}
