package kr.or.ddit.memo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.memo.dao.IMemoDao;
import kr.or.ddit.memo.model.MemoVo;

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
	

//	@Override
//	public String memoList(MemoVo memoVo) {
//		List<MemoVo> memoList = memoDao.memoList(memoVo);
//		StringBuffer result = new StringBuffer();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년  MM월 dd일");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
//		String today_dt_str = sdf2.format(new Date());
//		
//		result.append("<table ");
//		result.append("data-memo_email='");
//		result.append(memoVo.getMemo_email()+"' ");
//		result.append("data-prj_id='");
//		result.append(memoVo.getPrj_id());
//		result.append("'>");
//		
//		for(MemoVo memo : memoList) {
//			result.append("<tr ");
//			result.append("data-memo_dt_str='");
//			result.append(memoVo.getMemo_date());
//			
//			if(memoVo.getMemo_date().equals(today_dt_str))
//				{
//					result.append("' class='todayMemo'>");
//					result.append("<td>");
//					result.append("<span>");
//					result.append(sdf.format(memoVo.getMemo_update()));
//					result.append("(오늘)</span>");
//				}
//			else 
//				{
//				result.append("' class='memoList'>");
//				result.append("<td>");
//				result.append("<span>");
//				result.append(sdf.format(memoVo.getMemo_update()));
//				result.append("</span>");
//				}
//				result.append("</td>");
//				result.append("</tr>");
//		}
//		result.append("</table>");
//		
//		return result.toString();
//	}

	@Override
	public List<MemoVo> getYdTdCon(MemoVo memoVo) {
		return memoDao.getYdTdCon(memoVo);
	}

	@Override
	public String getMemo(MemoVo memoVo) {
		MemoVo memo = memoDao.getMemo(memoVo);
		StringBuffer result = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월 dd일 HH시 mm분");
		result.append("<div>");
		result.append("<span>");
		sdf.applyPattern("yyyy년 MM월 dd일 메모");
		result.append(sdf.format(memo.getMemo_update()));
		sdf.applyPattern("yyyy년 MM월 dd일 HH시 mm분");
		result.append("</span>");
		result.append("<br>");
		result.append("<span>");
		result.append(sdf.format(memo.getMemo_update())+"에 업데이트");
		result.append("</span>");
		result.append("<br>");
		result.append("<textarea id='notes_task' cols='30' rows='10' style='resize:none;' readonly>");
		result.append(memo.getMemo_con());
		result.append("</textarea>");
		result.append("<br>");
		result.append("<button type='button' onclick='copyTask(this)'>");
		result.append("복사하기</button>");
		result.append("<button type='button' onclick='noteList()'>");
		result.append("목록</button>");
		result.append("</div>");	
		
		return result.toString();
	}

	@Override
	public List<MemoVo> memoList(MemoVo memoVo) {
		return memoDao.memoList(memoVo);
	}

}
