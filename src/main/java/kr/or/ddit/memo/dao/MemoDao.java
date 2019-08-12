package kr.or.ddit.memo.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.memo.model.MemoVo;

@Repository
public class MemoDao implements IMemoDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int mergeMemo(MemoVo memoVo) {
		return sqlSession.insert("memo.mergeMemos",memoVo);
	}

	@Override
	public int mergeMemoYd(MemoVo memoVo) {
		return sqlSession.insert("memo.mergeMemosYd",memoVo);
	}

	@Override
	public List<MemoVo> memoList(MemoVo memoVo) {
		return sqlSession.selectList("memo.memoList", memoVo);
	}

	@Override
	public List<MemoVo> getYdTdCon(MemoVo memoVo) {
		return sqlSession.selectList("memo.getYdTdCon", memoVo);
	}

	@Override
	public MemoVo getMemo(MemoVo memoVo) {
		return sqlSession.selectOne("memo.getmemo", memoVo);
	}


}
