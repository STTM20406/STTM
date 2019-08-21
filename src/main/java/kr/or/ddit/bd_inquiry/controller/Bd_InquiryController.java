package kr.or.ddit.bd_inquiry.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.bd_inquiry.model.Bd_InquiryVo;
import kr.or.ddit.bd_inquiry.service.IBd_InquiryService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.users.model.UserVo;

@Controller
public class Bd_InquiryController {
	
	private static final Logger logger = LoggerFactory.getLogger(Bd_InquiryController.class);
	
	@Resource(name="bd_InquiryService")
	private IBd_InquiryService bd_InquiryService;
	
// 관리자
	
	/**
	 * Method 		: admInquiry
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param page
	 * @param pageSize
	 * @param model
	 * @return
	 * Method 설명 	: 관리자 1:1문의(일반문의, 광고문의)페이징리스트 조회
	 */
	@RequestMapping("/admInquiry")
	public String admInquiry(String page, String pageSize,Model model) {
		
		int pageStr = page == null ? 1 : Integer.parseInt(page);
		int pageSizeStr =  pageSize == null ? 10 : Integer.parseInt(pageSize);
		
		PageVo pageVo = new PageVo(pageStr,pageSizeStr);
		
		Map<String, Object> resultMap = bd_InquiryService.admInquiryList(pageVo);
		
		
		List<Bd_InquiryVo> inquiryListOrigin = (List<Bd_InquiryVo>) resultMap.get("inquiryListOrigin");
		List<Bd_InquiryVo> inquiryListAd = (List<Bd_InquiryVo>) resultMap.get("inquiryListAd");
		int paginationSizeOrigin = (Integer) resultMap.get("paginationSizeOrigin");
		int paginationSizeAd = (Integer) resultMap.get("paginationSizeAd");
		
		
		model.addAttribute("inquiryListOrigin", inquiryListOrigin);
		model.addAttribute("inquiryListAd", inquiryListAd);
		model.addAttribute("paginationSizeOrigin", paginationSizeOrigin);
		model.addAttribute("paginationSizeAd", paginationSizeAd);
		model.addAttribute("pageVo", pageVo);
		
		return "/board/inquiry/inquiryList.adm.tiles";
	}
	
	/**
	 * Method 		: inquiryView
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param inq_id
	 * @param model
	 * @return
	 * Method 설명 	: 관리자 1:1문의 게시글 상세조회
	 */
	@RequestMapping(path = "/admInquiryView", method = RequestMethod.GET)
	public String admInquiryView(int inq_id, Model model) {
		
		Bd_InquiryVo inquiryInfo = bd_InquiryService.inquiryInfo(inq_id);
		model.addAttribute("inquiryInfo", inquiryInfo);
		
		return "/board/inquiry/inquiryView.adm.tiles";
	}
	
	/**
	 * Method 		: inquiryView
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param inq_id
	 * @param iq_content
	 * @param model
	 * @return
	 * Method 설명 	: 관리자 1:1문의 게시글 답변작성
	 */
	@RequestMapping("/admInquiryView1")
	public String admInquiryView(int inq_id, String iq_content, Model model,HttpSession session) {
		UserVo userVO = (UserVo) session.getAttribute("USER_INFO");
		
		logger.debug("!@# inq_id : {} ",inq_id);
		logger.debug("!@# iq_content : {} ",iq_content);
		Bd_InquiryVo insertInquiry = new Bd_InquiryVo(inq_id, iq_content);
		int inquiryCnt = bd_InquiryService.insertAdmPost(insertInquiry);
		
		Bd_InquiryVo inquiryInfo = bd_InquiryService.inquiryInfo(inq_id);
		logger.debug("!@# inquiryInfo : {} ",inquiryInfo);
		
//		if(inquiryCnt == 1) {
//			viewName = "redirect:/admInquiryView?inq_id="+ inq_id;
//		}else {
//			viewName = "redirect:/admInquiry";
//		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("userId", userVO);
		resultMap.put("inquiryInfo",inquiryInfo);
		
		if(inquiryCnt ==1 ) {
			model.addAttribute("data", resultMap);
		}
		return "jsonView";
	}
	
	@RequestMapping(path = "/admInquirySearch",method= RequestMethod.GET)
	public String admInquirySearch(Model model,String searchText, String scText,String inq_cate,@RequestParam(name = "page", defaultValue = "1")int page,@RequestParam(name = "pageSize", defaultValue = "10")int pageSize) {
		PageVo pageVo = new PageVo(page, pageSize);
		
		logger.debug("!@# inq_cate : {}",inq_cate);
		Map<String, Object> search = new HashMap<String, Object>();
//		search.put("pageVo",pageVo);
		search.put("subject",searchText);
		search.put("inq_cate",inq_cate);
		search.put("page", page);
		search.put("pageSize", pageSize);
		logger.debug("!@# search : {}",search);
		
		if(inq_cate.equals("INQ01") && scText.equals("title") || inq_cate.equals("INQ02") && scText.equals("title")) {
			Map<String, Object> resultMap = bd_InquiryService.admSearchSubList(search);
			List<Bd_InquiryVo> inquiryList = (List<Bd_InquiryVo>) resultMap.get("admSearchSubList");
			int paginationSize = (Integer) resultMap.get("paginationSize");
			
			model.addAttribute("inquiryList",inquiryList);
			model.addAttribute("paginationSize", paginationSize);
			model.addAttribute("pageVo", pageVo);
			
			
		}else if(inq_cate.equals("INQ01") && scText.equals("content") || inq_cate.equals("INQ02") && scText.equals("content")){
			Map<String, Object> resultMap = bd_InquiryService.admSearchConList(search);
			List<Bd_InquiryVo> inquiryList = (List<Bd_InquiryVo>) resultMap.get("admSearchConList");
			int paginationSize = (Integer) resultMap.get("paginationSize");
			
			model.addAttribute("inquiryList",inquiryList);
			model.addAttribute("paginationSize", paginationSize);
			model.addAttribute("pageVo", pageVo);
		}
		
		
		
		return "/board/inquiry/inquiryList.adm.tiles";
	}
	
// 사용자
	/**
	 * Method 		: userInquirt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-23 최초 생성
	 * @param page
	 * @param pageSize
	 * @param model
	 * @param session
	 * @return
	 * Method 설명 	: 사용자 1:1문의 게시글리스트 조회(자신이 작성한 게시글만 조회가 된다.)
	 */
	@RequestMapping("/userInquiry")
	public String userInquirt(String page, String pageSize,Model model,HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		
		int pageStr = page == null ? 1 : Integer.parseInt(page);
		int pageSizeStr =  pageSize == null ? 10 : Integer.parseInt(pageSize);

		PageVo pageVo = new PageVo(pageStr,pageSizeStr,user.getUser_email());
		
		Map<String, Object> resultMap = bd_InquiryService.userInquiryList(pageVo);
		logger.debug("!@#resultMap : {}",resultMap);
		
		List<Bd_InquiryVo> inquiryListOrigin = (List<Bd_InquiryVo>) resultMap.get("inquiryListOrigin");
		List<Bd_InquiryVo> inquiryListAd = (List<Bd_InquiryVo>) resultMap.get("inquiryListAd");
		int paginationSizeOrigin = (Integer) resultMap.get("paginationSizeOrigin");
		int paginationSizeAd = (Integer) resultMap.get("paginationSizeAd");
		
		model.addAttribute("inquiryListOrigin", inquiryListOrigin);
		model.addAttribute("inquiryListAd", inquiryListAd);
		
		model.addAttribute("paginationSizeOrigin", paginationSizeOrigin);
		model.addAttribute("paginationSizeAd", paginationSizeAd);
		model.addAttribute("pageVo", pageVo);
		
		return "/board/inquiry/inquiryList.user.tiles";
	}
	
	/**
	 * Method 		: userInquiryView
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-23 최초 생성
	 * @param inq_id
	 * @param model
	 * @return
	 * Method 설명 	: 사용자 1:1문의 게시글 상세조회
	 */
	@RequestMapping(path = "/userInquiryView", method = RequestMethod.GET)
	public String userInquiryView(int inq_id, Model model) {
		
		Bd_InquiryVo inquiryInfo = bd_InquiryService.inquiryInfo(inq_id);
		model.addAttribute("inquiryInfo", inquiryInfo);
		
		return "/board/inquiry/inquiryView.user.tiles";
	}

	/**
	 * Method 		: userInsertInquiry
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-23 최초 생성
	 * @param inqCate
	 * @param model
	 * @return
	 * Method 설명 	: 사용자가 1:1문의 구분(일반문의, 광고문의).
	 */
	@RequestMapping(path = "/userInquiryPost", method = RequestMethod.GET)
	public String userInsertInquiry(String inqCate, Model model) {
		
		model.addAttribute("generalCate",inqCate);
		logger.debug("!@# generalCate : {}",inqCate);
		
		return "/board/inquiry/inquiryWrite.user.tiles";
	}
	
	/**
	 * Method 		: userInsertInquiry
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-23 최초 생성
	 * @param model
	 * @param inq_cate
	 * @param user_email
	 * @param smarteditor
	 * @param subject
	 * @return
	 * Method 설명 	:사용자 1:1문의 작성
	 */
	@RequestMapping(path = "/userInquiryPost", method = RequestMethod.POST)
	public String userInsertInquiry(Model model,String inq_cate, String user_email,String smarteditor,String subject) {
		
		String viewName = "";
		
		Bd_InquiryVo bd_InquiryVo = new Bd_InquiryVo(user_email, subject, inq_cate, smarteditor);
		logger.debug("!@# VO : {}",bd_InquiryVo);
		int postCnt = bd_InquiryService.insertUserPost(bd_InquiryVo);
		
		if(postCnt == 1) {
			viewName = "redirect:/userInquiry";
		}else {
			viewName = "redirect:/userInquiry";
		}
		
		return viewName;
	}
	
	/**
	 * Method 		: userDeleteInquiry
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-24 최초 생성
	 * @param inq_id
	 * @param model
	 * @return
	 * Method 설명 	: 사용자 게시글 삭제
	 */
	@RequestMapping("/userInquiryDelete")
	public String userDeleteInquiry(int inq_id,Model model) {
		String viewName = "";
		
		Bd_InquiryVo bd_InquiryVo = new Bd_InquiryVo();
		bd_InquiryVo.setInq_id(inq_id);
		
		int deleteCnt = bd_InquiryService.deleteUserPost(bd_InquiryVo);
		if(deleteCnt == 1) {
			viewName = "redirect:/userInquiry";
		}else {
			viewName = "redirect:/userInquiry?inq_id="+inq_id;
		}
		
		return viewName;
	}
	
	/**
	 * Method 		: userInquirtModify
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-23 최초 생성
	 * @return
	 * Method 설명 	: 사용자 게시글 수정하기 위해서 해당 게시글번호 가져오기
	 */
	@RequestMapping(path = "/userInquiryModify",method = RequestMethod.GET)
	public String userInquirtModify(Model model,int inq_id) {
		
		Bd_InquiryVo inquiryInfo = bd_InquiryService.inquiryInfo(inq_id);
		model.addAttribute("inquiryInfo", inquiryInfo);
		
		return "/board/inquiry/inquiryUpdate.user.tiles";
	}
	
	
	/**
	 * Method 		: userInquirtModify
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-24 최초 생성
	 * @param model
	 * @param subject
	 * @param smarteditor
	 * @param inq_id
	 * @return
	 * Method 설명 	: 사용자 게시글 수정
	 */
	@RequestMapping(path = "/userInquiryModify",method = RequestMethod.POST)
	public String userInquirtModify(Model model,String subject, String smarteditor,int inq_id) {
		String viewName = "";
		
		Bd_InquiryVo inquiryInfo = new Bd_InquiryVo(inq_id, subject, smarteditor);
		int modifyCnt = bd_InquiryService.updateUserPost(inquiryInfo);
		
		if(modifyCnt == 1) {
			viewName = "redirect:/userInquiryView?inq_id="+inq_id;
		}else {
			viewName = "redirect:/userInquiryModify?inq_id="+inq_id;
		}
		
		return viewName;
	}
	
	/**
	 * Method 		: userInquirySearch
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param model
	 * @param searchText
	 * @param scText
	 * @param inq_cate
	 * @param page
	 * @param pageSize
	 * @param session
	 * @return
	 * Method 설명 	: 사용자 1:1문의 게시글 검색
	 */
	@RequestMapping(path = "/userInquirySearch",method= RequestMethod.GET)
	public String userInquirySearch(Model model,String searchText, String scText,String inq_cate,
			@RequestParam(name = "page", defaultValue = "1")int page,@RequestParam(name = "pageSize", defaultValue = "10")int pageSize,
			HttpSession session) {
		PageVo pageVo = new PageVo(page, pageSize);
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		
		logger.debug("!@# inq_cate : {}",inq_cate);
		Map<String, Object> search = new HashMap<String, Object>();
//		search.put("pageVo",pageVo);
		search.put("subject",searchText);
		search.put("inq_cate",inq_cate);
		search.put("page", page);
		search.put("pageSize", pageSize);
		search.put("user_email",userVo.getUser_email());
		logger.debug("!@# search : {}",search);
		
		if(inq_cate.equals("INQ01") && scText.equals("title") || inq_cate.equals("INQ02") && scText.equals("title")) {
			Map<String, Object> resultMap = bd_InquiryService.userSearchSubList(search);
			List<Bd_InquiryVo> inquiryList = (List<Bd_InquiryVo>) resultMap.get("userSearchSubList");
			int paginationSize = (Integer) resultMap.get("paginationSize");
			
			model.addAttribute("inquiryList",inquiryList);
			model.addAttribute("paginationSize", paginationSize);
			model.addAttribute("pageVo", pageVo);
			
			
		}else if(inq_cate.equals("INQ01") && scText.equals("content") || inq_cate.equals("INQ02") && scText.equals("content")){
			Map<String, Object> resultMap = bd_InquiryService.userSearchConList(search);
			List<Bd_InquiryVo> inquiryList = (List<Bd_InquiryVo>) resultMap.get("userSearchConList");
			int paginationSize = (Integer) resultMap.get("paginationSize");
			
			model.addAttribute("inquiryList",inquiryList);
			model.addAttribute("paginationSize", paginationSize);
			model.addAttribute("pageVo", pageVo);
		}
		
		
		
		return "/board/inquiry/inquiryList.user.tiles";
	}
	
	
}
