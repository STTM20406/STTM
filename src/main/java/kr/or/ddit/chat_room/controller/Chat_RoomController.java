package kr.or.ddit.chat_room.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.NestedServletException;

import kr.or.ddit.chat_content.model.ChatParticipateUserVo;
import kr.or.ddit.chat_content.model.Chat_ContentVo;
import kr.or.ddit.chat_content.service.IChat_ContentService;
import kr.or.ddit.chat_mem.model.Chat_MemVo;
import kr.or.ddit.chat_mem.service.IChat_MemService;
import kr.or.ddit.chat_room.model.Chat_RoomVo;
import kr.or.ddit.chat_room.service.IChat_RoomService;
import kr.or.ddit.friends.model.ChatFriendsVo;
import kr.or.ddit.friends.model.FriendsVo;
import kr.or.ddit.friends.service.IFriendsService;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.project_mem.service.IProject_MemService;
import kr.or.ddit.users.model.UserVo;

@Controller
public class Chat_RoomController {
	
	private static final Logger logger = LoggerFactory.getLogger(Chat_RoomController.class);
	
	@Resource(name="chat_RoomService")
	private IChat_RoomService roomService;
	
	@Resource(name="chat_MemService")
	private IChat_MemService memService;
	
	@Resource(name="chat_ContentService")
	private IChat_ContentService contentService;
	
	@Resource(name="friendsService")
	private IFriendsService friendsService;
	
	@Resource(name="project_MemService")
	private IProject_MemService projectService;
	
	//프로젝트 채팅방
	@RequestMapping(path="/projectChat")
	public String projectChat(HttpServletRequest req, Model model, int prj_id) {
//		UserVo user = (UserVo) req.getSession().getAttribute("USER_INFO");
//		String user_email = user.getUser_email();
		//프로젝트 멤버리스트.
		List<Project_MemVo> projectMemList =projectService.getMyProjectMemList(prj_id);
		
		//채팅방 대화 내용 리스트
		List<ChatParticipateUserVo> chatroomContentList = contentService.chatroomContentList(0);
		
		//프로젝트 이름
		String roomNm = null; //= projectService.projectName(prj_id);
		
		
		model.addAttribute("ct_id",0);
		model.addAttribute("roomNm",roomNm);
		model.addAttribute("chatroomContentList",chatroomContentList);
		model.addAttribute("friendList", projectMemList);
		
		return "/chat/projectChat.user.tiles";
	}
	
	
	
	
	//채팅방 리스트 화면으로 이동
	@RequestMapping(path="/friendChatList")
	public String myChatRoomList(Model model,HttpSession session) throws NestedServletException{
		
		UserVo user = (UserVo) session.getAttribute("USER_INFO");    
		logger.debug("********friendChatList UserVo : {}",user);
		String user_email = user.getUser_email();
		
		
		//내가 갖고 있는 방목록 가져오기
		List<Chat_RoomVo> roomlist = roomService.getRoomList(user_email);
		logger.debug("*********roomlist: {}" , roomlist);
		
		//방마다의 친구 리스트를 가져옴
		Map<Integer, Object> realRoomMap = memService.allRoomFriendList();
		logger.debug("realRoomMap : {}", realRoomMap);
		
		//전체 친구 리스트를 가져옴
		List<ChatFriendsVo> allFriendList = friendsService.friendList(user_email);
		
		
		model.addAttribute("allFriendList",allFriendList);
		model.addAttribute("realRoomMap",realRoomMap);
		model.addAttribute("roomlist", roomlist);
		//model.addAttribute("inviteFriendList",inviteFriendList);
		
		return "/chat/friendChatList.user.tiles";
	}
	
	
	
	
	@RequestMapping(path="/friendChat", method = RequestMethod.GET)
	public String friendChat(Model model, HttpServletRequest req, String ct_id) {
		
		UserVo user = (UserVo) req.getSession().getAttribute("USER_INFO");
		String user_email = user.getUser_email();
		
		logger.debug("********friendChat UserVo : {}",user);
		int Ict_id = Integer.parseInt(ct_id);
		
		// 현재 어느방에 들어가 있는지 확인
		Chat_RoomVo nowWhereRoom = roomService.nowWhereRoom(Ict_id);
		String roomNm = nowWhereRoom.getCt_nm();
		
		// 채팅방 대화 내용 리스트 (채팅방별 대화 리스트)
		List<ChatParticipateUserVo> chatroomContentList = contentService.chatroomContentList(Ict_id);
		logger.debug("************nowWhereRoom : {}",nowWhereRoom);
		
		//채팅방에 참여한 친구 리스트
		List<String> friendList = memService.roomFriendList(Ict_id);
		logger.debug("************friendList : {}",friendList);
		
		//초대할 친구 리스트
		ChatParticipateUserVo inviteVo = new ChatParticipateUserVo(user_email, Ict_id);
		List<ChatParticipateUserVo> inviteList = memService.inviteFriend(inviteVo);
		logger.debug("inviteList : {}",inviteList);
		
		model.addAttribute("ct_id",ct_id);
		model.addAttribute("roomNm",roomNm);
		model.addAttribute("chatroomContentList",chatroomContentList);
		model.addAttribute("friendList", friendList);
		model.addAttribute("inviteList",inviteList);
		
		return "/chat/friendChat.user.tiles";
	}
	
	
	
	//채팅방 생성Post
	@RequestMapping(path="/createChatRoom" , method = RequestMethod.POST)
	public String createChatRoomPost(Model model, HttpServletRequest req , String[] array, String room_nm) {
		
		UserVo user = (UserVo) req.getSession().getAttribute("USER_INFO");
		String user_email = user.getUser_email();
		
		//새 채팅방 생성
		int newRoom = roomService.createRoom(room_nm);
		
		int roomId = roomService.maxRoomId();
		
		//방에다가 멤버 추가해야지
		//방에다가 로그인한 사용자 추가
		Chat_MemVo memVo = new Chat_MemVo(roomId, user_email);
		memService.insertChatMem(memVo);
		
		//체크박스로 받은 사용자아이디 배열값을 가져와서 배열 돌려서 방 멤버에 추가 
		for(int i=0;i<array.length;i++) {
			memVo = new Chat_MemVo(roomId, array[i]);
			memService.insertChatMem(memVo);
		}
		
		return "redirect:/friendChatList";
	}
	
	
	//친구 추가
	@RequestMapping(path="/addFriend")
	public String addFriend(Model model, String[] array,String ct_id, HttpServletRequest req) {
		
		UserVo user = (UserVo) req.getSession().getAttribute("USER_INFO");
		String user_email = user.getUser_email();
		
		int ctid = Integer.parseInt(ct_id);
		
		Chat_MemVo insertVo = new Chat_MemVo();
		for(int i=0;i<array.length;i++) {
			insertVo = new Chat_MemVo(ctid, array[i]);
			int insertMem = memService.insertChatMem(insertVo);
			
		}
		return "redirect:/friendChat?ct_id=" + ctid;
	}
	
	
	//채팅방 이름 변경
	@RequestMapping(path="/updateChatRoomTitle" , method = RequestMethod.POST)
	public String updateChatRoomTitle(Model model, String room_nmup, String upct_id, HttpSession session) {
		Chat_RoomVo vo = new Chat_RoomVo();
		
		vo.setCt_id(Integer.parseInt(upct_id));
		vo.setCt_nm(room_nmup);
		int updateCnt = roomService.updateChatTitle(vo);
		
		return "redirect:/friendChatList";
	}
	
	//채팅방 나가기
	@RequestMapping(path="/outChatRoom")
	public String outChatRoom(Model model, String ct_id, HttpSession session) {
		
		int ct_id1 = Integer.parseInt(ct_id);
		
		
		UserVo user = (UserVo) session.getAttribute("USER_INFO");    
		String user_email = user.getUser_email();
		logger.debug("********friendChatList UserVo : {}",user);
		
		
		Chat_MemVo memVo = new Chat_MemVo(ct_id1, user_email);
		Chat_ContentVo contentVo = new Chat_ContentVo(ct_id1, user_email);
		
		//채팅방 내용 삭제
		int deleteContent = contentService.deleteChatContent(contentVo);
		//채팅방에서 나가기
		int outRoom = memService.deleteChatMem(memVo);
		
		//채팅방에 한명도 없으면 채팅방 삭제
		int chatMemCount = memService.countChatMem(ct_id1);
		if(chatMemCount == 0) {
			roomService.deleteChatRoom(ct_id1);
		}
		
		return "redirect:/friendChatList";
	}
	
	//화상회의 방 생성 페이지
	@RequestMapping(path="/faceChat")
	public String faceChatMain(Model model, HttpSession session) {
	
		
		return "redirect:/RTCMulticonnection/index.html";
	}
	
	
	@RequestMapping(path="/projectChatList")
	public String projectChatList(Model model, HttpSession session) {
		
		UserVo user = (UserVo) session.getAttribute("USER_INFO");    
		String user_email = user.getUser_email();
		
		
		
		
		return "/chat/projectChatList.user.tiles";
	}
	
	
}
