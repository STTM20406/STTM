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
	
	//프로젝트 채팅방
	@RequestMapping(path="/projectChat")
	public String projectChat(HttpServletRequest req, Model model/* int projectId*/) {
//		UserVo user = (UserVo) req.getSession().getAttribute("USER_INFO");
//		String user_email = user.getUser_email();
		
		
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
		
		model.addAttribute("realRoomMap",realRoomMap);
		model.addAttribute("roomlist", roomlist);
		//model.addAttribute("inviteFriendList",inviteFriendList);
		
		return "/chat/friendChatList.user.tiles";
	}
	
	@RequestMapping(path="/friendChat", method = RequestMethod.GET)
	public String friendChat(Model model, HttpSession session, String ct_id) {
		
		UserVo user = (UserVo) session.getAttribute("USER_INFO");    
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
		
		model.addAttribute("ct_id",ct_id);
		model.addAttribute("roomNm",roomNm);
		model.addAttribute("chatroomContentList",chatroomContentList);
		model.addAttribute("friendList", friendList);
		
		return "/chat/friendChat.user.tiles";
	}
	
	
	
	//채팅방 생성
	@RequestMapping(path="/createChatRoom")
	public String createChatRoom(Model model, HttpServletRequest req) {
		
		UserVo user = (UserVo) req.getSession().getAttribute("USER_INFO");
		String user_email = user.getUser_email();
		
		int newRoom = roomService.createRoom(user_email);
		
		if(newRoom == 1) {
			logger.debug("방이 새로 만들어짐");
		}
		
		return "/chat/friendChat.user.tiles";
	}
	
	//채팅방 나가기
	@RequestMapping(path="/outChatRoom")
	public String outChatRoom(Model model, String user_email, String ct_id, HttpSession session) {
		
		int ct_id1 = Integer.parseInt(ct_id);
		
		
		UserVo user = (UserVo) session.getAttribute("USER_INFO");    
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
		
		
		logger.debug("deleteContent: " + deleteContent + "outRoom : " + outRoom);
		
		//내가 갖고 있는 방목록 가져오기
		List<Chat_RoomVo> roomlist = roomService.getRoomList(user_email);
		logger.debug("*********roomlist: {}" , roomlist);
		
		// 채팅방별 친구들 리스트들을 리스트에 넣음
		// memlist : 방마다 갖고 있는 멤버 리스트를 가져옴(계속 새값으로 변경)
		// memlistlist[0] = {김두한, 박경림}, memlistlist[1] = {아이유, 유인나}
		List<String> memlist = null;
		List<List<String>> memlistlist = new ArrayList<List<String>>();
		
		//방 별로의 채팅멤버명
		for(int i=0; i< roomlist.size();i++) {
			
			ct_id1 = roomlist.get(i).getCt_id(); //방 아이디 가져옴
			memlist = memService.roomFriendList(ct_id1); //채팅방에 있는 친구들 정보 들어옴
			memlistlist.add(memlist);  
			
			logger.debug("memlistlist : {}", memlistlist);
			
		}
		
		model.addAttribute("roomlist", roomlist);
		model.addAttribute("memlistlist", memlistlist);
		
		
		return "/chat/friendChatList.user.tiles";
	}
}
