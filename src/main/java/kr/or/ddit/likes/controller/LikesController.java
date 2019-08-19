package kr.or.ddit.likes.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.chat_content.service.IChat_ContentService;
import kr.or.ddit.likes.service.ILikesService;
import kr.or.ddit.project.model.ProjectVo;

@Controller
public class LikesController {

	
	@Resource(name="likesService")
	private ILikesService likeService;
	
	
	@RequestMapping("/addLikeAjax")
	public String addlikeAjax(String write_id, Model model) {

		int writeId = Integer.parseInt(write_id);
		
		//게시글 좋아요 증가
		likeService.likeAdd(writeId);
		
		//좋아요 현재 값
		int cnt = likeService.likeCnt(writeId);
		
		model.addAttribute("data",cnt);
		return "jsonView";
	}
	
	@RequestMapping("/minusLikeAjax")
	public String minusLikeAjax(String write_id, Model model) {

		int writeId = Integer.parseInt(write_id);
		
		//게시글 좋아요 감소
		likeService.likeDown(writeId);
		
		//좋아요 현재 값
		int cnt = likeService.likeCnt(writeId);
		
		model.addAttribute("data",cnt);
		
		return "jsonView";
	}
	
	
}
