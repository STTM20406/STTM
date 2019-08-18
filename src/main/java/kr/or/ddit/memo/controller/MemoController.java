package kr.or.ddit.memo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.memo.dao.IMemoDao;
import kr.or.ddit.memo.model.MemoVo;
import kr.or.ddit.memo.service.IMemoService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.users.model.UserVo;

@Controller
public class MemoController {
	
	@Resource(name="memoService")
	private IMemoService memoService;
	
	private static final Logger logger = LoggerFactory.getLogger(MemoController.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	
	@RequestMapping("/memo")
	public String memoView() {
		return "/memo/memoMain.user.tiles";
	}
	
	@RequestMapping("/yd_con")
	public String getYesterdayTask(Model model, MemoVo memoVo) {
		String memoYd = memoService.mergeMemoYd(memoVo);
		logger.debug("!@#notesVo : {}", memoVo);
		logger.debug("!@#memoYd : {}", memoYd);
		List<MemoVo> resultMemo = memoService.getYdTdCon(memoVo);
		String today_str = sdf.format(new Date());
		
		
		
		for(MemoVo memo : resultMemo) {
			if(memo.getMemo_date().equals(today_str) && memo.getMemo_con() != null) {
				model.addAttribute("td_con", memo);
			}
			if(!memo.getMemo_date().equals(today_str) && memo.getMemo_con() != null)  {
				model.addAttribute("yd_con", memo);
			}
			if(memo.getMemo_date().equals(today_str) && memo.getMemo_con() == null) {
				String a = "오늘 작성한 내용이 없습니다.";
				memo.setMemo_con(a);
				model.addAttribute("td_con", memo);
			}
			if(!memo.getMemo_date().equals(today_str) && memo.getMemo_con() == null) {
				String a = "어제 작성한 내용이 없습니다.";
				memo.setMemo_con(a);
				model.addAttribute("yd_con", memo);
			}

		}
		return "jsonView";
	}
	
	@RequestMapping("/merge")
	public String updateNote(Model model, MemoVo memoVo) {
		memoVo.setMemo_update(new Date());
		String mergeResult = memoService.mergeMemo(memoVo);
		model.addAttribute("result", mergeResult);
		return "jsonView";
	}
	
	@RequestMapping(path="/memoList",method=RequestMethod.GET)
	public String noteList(Model model, int prj_id, HttpSession session, PageVo pageVo) {
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		
		pageVo.setPrj_id(prj_id);
		pageVo.setMemo_email(userVo.getUser_email());
//		MemoVo memoVo = new MemoVo();
//		memoVo.setPrj_id(prj_id);
//		memoVo.setMemo_email(userVo.getUser_email());
		
		Map<String, Object> resultMap = memoService.memoList(pageVo);
		List<MemoVo>memoList = (List<MemoVo>) resultMap.get("memoList");
		int memoPaginationSize = (int) resultMap.get("memoPaginationSize");
		
		
		model.addAttribute("memoList", memoList);
		model.addAttribute("memoPaginationSize", memoPaginationSize);
		model.addAttribute("pageVo", pageVo);
		
		return "/memo/memoList.user.tiles";
	}
	
	@RequestMapping("/getMemo")
	public String getNotes(Model model, MemoVo memoVo) {
		logger.debug("getNotes() - notesVo : {}", memoVo);
		model.addAttribute("memo", memoService.getMemo(memoVo));
		return "jsonView";
	}
	
	
}
