package kr.or.ddit.memo.dao;

import java.util.List;

import kr.or.ddit.memo.model.MemoVo;
import kr.or.ddit.paging.model.PageVo;

public interface IMemoDao {
	
	int mergeMemo(MemoVo memoVo);
	
	int mergeMemoYd(MemoVo memoVo);

	List<MemoVo> memoList(PageVo pageVo);
	
	List<MemoVo> getYdTdCon(MemoVo memoVo);
	
	MemoVo getMemo(MemoVo memoVo);
	
	int memoListCnt (MemoVo memoVo);
}
