package kr.or.ddit.memo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.memo.dao.IMemoDao;
import kr.or.ddit.memo.model.MemoVo;
import kr.or.ddit.paging.model.PageVo;

@Service 
public class MemoService implements IMemoService{
	
	@Resource(name="memoDao")
	private IMemoDao memoDao;
	
	@Override
	public String mergeMemo(MemoVo memoVo) {
		int memoResult = memoDao.mergeMemo(memoVo);
		String result="";
		
		if(memoResult == 1) {
			result = "OK";
		}else {
			result = "NO";
		}
		
		return result;
	}
	
	@Override
	public String mergeMemoYd(MemoVo memoVo) {
		int memoResult = memoDao.mergeMemoYd(memoVo);
		String result="";
		
		if(memoResult == 1) {
			result = "OK";
		}else {
			result = "NO";
		}
		
		return result;
	}

	@Override
	public List<MemoVo> getYdTdCon(MemoVo memoVo) {
		return memoDao.getYdTdCon(memoVo);
	}


	@Override
	public MemoVo getMemo(MemoVo memoVo) {
		return memoDao.getMemo(memoVo);
	}

	@Override
	public Map<String, Object> memoList(PageVo pageVo) {
		MemoVo memoVo = new MemoVo();
		memoVo.setPrj_id(pageVo.getPrj_id());
		memoVo.setMemo_email(pageVo.getMemo_email());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("memoList", memoDao.memoList(pageVo));
		
		int memoCnt = memoDao.memoListCnt(memoVo);
		int paginationSize = (int) Math.ceil((double)memoCnt/pageVo.getPageSize());
		resultMap.put("memoPaginationSize", paginationSize);
		
		return resultMap;
	}

}
