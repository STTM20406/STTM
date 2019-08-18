package kr.or.ddit.memo.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.memo.model.MemoVo;
import kr.or.ddit.paging.model.PageVo;

public interface IMemoService {
	
	String mergeMemo(MemoVo memoVo);

	String mergeMemoYd(MemoVo memoVo);
	
//	String memoList(MemoVo memoVo);
	
	Map<String, Object> memoList(PageVo pageVo);
	
	List<MemoVo> getYdTdCon(MemoVo memoVo);
	
//	String getMemo(MemoVo memoVo);
	
	MemoVo getMemo(MemoVo memoVo);
	
	
}
