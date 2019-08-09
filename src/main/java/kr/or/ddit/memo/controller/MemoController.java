package kr.or.ddit.memo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.memo.dao.IMemoDao;
import kr.or.ddit.memo.model.MemoVo;
import kr.or.ddit.memo.service.IMemoService;

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
		logger.debug("notesVo : {}", memoVo);
		List<MemoVo> resultMemo = memoService.getYdTdCon(memoVo);
		String today_str = sdf.format(new Date());
//		model.addAttribute("yd_con", "");
		
		for(MemoVo memo : resultMemo) {
			if(memo.getMemo_date().equals(today_str)) {
				model.addAttribute("td_con", memo);
			}else {
				model.addAttribute("yd_con", memo);
			}
			
//			if(!memo.getMemo_date().equals(today_str)) {
//				model.addAttribute("yd_con", memo);
//			}
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
	@RequestMapping("/memoList")
	public String noteList(Model model, MemoVo memoVo) {
		model.addAttribute("memoList", memoService.memoList(memoVo));
		return "jsonView";
	}
	@RequestMapping("/getMemo")
	public String getNotes(Model model, MemoVo memoVo) {
		logger.debug("getNotes() - notesVo : {}", memoVo);
		model.addAttribute("memo", memoService.getMemo(memoVo));
		return "jsonView";
	}
	
	
}
