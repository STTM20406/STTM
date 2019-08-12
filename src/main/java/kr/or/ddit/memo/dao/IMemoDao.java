package kr.or.ddit.memo.dao;

import java.util.List;

import kr.or.ddit.memo.model.MemoVo;

public interface IMemoDao {
	
	int mergeMemo(MemoVo memoVo);
	
	int mergeMemoYd(MemoVo memoVo);

	List<MemoVo> memoList(MemoVo memoVo);
	
	List<MemoVo> getYdTdCon(MemoVo memoVo);
	
	MemoVo getMemo(MemoVo memoVo);
}
