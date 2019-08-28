package kr.or.ddit.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.geometry.spherical.oned.ArcsSet.Split;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.or.ddit.chat_content.model.Chat_ContentVo;
import kr.or.ddit.chat_content.service.Chat_ContentService;
import kr.or.ddit.chat_mem.service.Chat_MemService;
import kr.or.ddit.chat_room.service.Chat_RoomService;
import kr.or.ddit.friends.model.ChatFriendsVo;
import kr.or.ddit.friends.service.FriendsService;
import kr.or.ddit.note_info.service.INote_InfoService;
import kr.or.ddit.note_info.service.Note_InfoService;
import kr.or.ddit.notification.model.NotificationVo;
import kr.or.ddit.notification.service.NotificationService;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.project_mem.service.Project_MemService;
import kr.or.ddit.receiver.model.ReceiverVo;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.users.service.IUserService;

//작성순서 : afterConnectionEstablished(서버 접속 시) -> afterConnectionClosed(서버연결끊을 시)
//		  -> handleTextMessage(서버가 메세지를 받았을 때)
// 기타 필요한 method는 중간에 필요에 따라 작성
// 서버 연결에 필요한 wesocket변수들도 필요에 따라 추가.

//@EnableWebSocket
public class WebSocket extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(WebSocket.class);

	@Resource(name = "chat_RoomService")
	private Chat_RoomService roomService;

	@Resource(name = "chat_MemService")
	private Chat_MemService memService;

	@Resource(name = "chat_ContentService")
	private Chat_ContentService contentService;
	
	@Resource(name ="notificationService")
	private NotificationService notificationService;
	
	@Resource(name = "project_MemService")
	Project_MemService prjMemService;
	
	@Resource(name = "friendsService")
	FriendsService fndService;
	
	@Resource(name="userService")
	private IUserService userService;
	
	// 서버에 연결된 사용자들을 저장하기 위해 선언
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();// 메시지를 날려주기 위한 웹소켓전용 세션
	private Map<String, WebSocketSession> userList = new HashMap<String, WebSocketSession>();// 세션에 로그인한 모든 로그인 정보가 담김
	private Map<WebSocketSession, String> roomList = new HashMap<WebSocketSession, String>(); // 실제 session의 아이디정보,
																								// room정보

	private List<WebSocketSession> sessions = new ArrayList<>();

	// 연결되었을 때
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished :" + session);
		// session : 세션마다 주는 아이디

		sessions.add(session); // 세션에 있는 모든 정보를 담음
		String senderId = getId(session); // galbi@naver.com (내가 접속한 아이디)
		userList.put(senderId, session);  // ex: {galbi@naver.com, galbi[userVo]의 정보(이름,이메일 등등)}

	}

	private String getId(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes(); // httpSession의 로그인 정보들을 넣기
		UserVo loginUser = (UserVo) httpSession.get("USER_INFO"); // 로그인한 정보 가져옴

		return loginUser.getUser_email();
	}

	// 서버가 클라이언트로부터 메시지 받았을 때
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handlerTextMessage : " + session + " : " + message);

		String senderId = getId(session); // 유저의 이메일(아이디) 가져옴 galbi@naver.com
		System.out.println("senderId : " + senderId);

//		for (WebSocketSession sess : sessions) { // 메시지 받은 것을 접속되어 있는 모두에게 전송
//			sess.sendMessage(new TextMessage(senderId + ": " + message.getPayload())); // 보낼 메시지 가공 getPayload가 보내는 내용,
//																						// 이거 보내면jsp의 onmessage를 탐
//		}

		// protocol: chatting,채팅발신자명, 채팅 내용, 채팅발신자아이디, 채팅방아이디
		String msg = message.getPayload(); // 내가 받은 메시지 내용
		System.out.println("msg : " + msg);

		if (StringUtils.isNotEmpty(msg)) { // 메시지가 들어올 때만 처리
			String[] strs = msg.split(",");
			for(int i=0;i<strs.length;i++) {
				System.out.println(strs[i]);
			}
			if (strs != null && strs.length == 5 && strs[0].equals("chatting")) {
				String chatting = strs[0];
				String senderNm = strs[1];
				String content = strs[2];
				String senderId1 = strs[3];
				String ct_id = strs[4];

				//들어온 메시지 내용 저장
				Chat_ContentVo contentVo = new Chat_ContentVo(Integer.parseInt(ct_id), senderId1, content);
				contentService.insertChatContent(contentVo);
				
				//입력한 메시지의 정보 가져오기
				int maxContentId = contentService.maxChatContentId(Integer.parseInt(ct_id));
				Chat_ContentVo maxContentVo = contentService.getContent(maxContentId);
				
				Date from = new Date();
				SimpleDateFormat transFormat = new SimpleDateFormat("yy/MM/dd HH:mm");

				String to = transFormat.format(from);


				
				
				// 각 채팅방 멤버들 리스트 아이디
				List<String> chatMemList = memService.roomFriendListEmail(Integer.parseInt(ct_id));

				for (int i = 0; i < chatMemList.size(); i++) {
					System.out.println("chatMemList : " + i + " -- " + chatMemList.get(i));
				}

				System.out.println("배열sender : " + senderNm + " content : " + content + " senderId : " + senderId1
						+ "ct_id" + ct_id);

				// 각각 채팅방 멤버들 중에서 지금 로그인한 사람에게만 보내려고 지금 로그인한 사람들의 정보를 넣음
				Map<String, WebSocketSession> nowLoginList = new HashMap<String, WebSocketSession>();
				for (int i = 0; i < chatMemList.size(); i++) { // 들어온 채팅방 멤버아이디를 조회 해서
					if (userList.get(chatMemList.get(i)) != null) { // 채팅방 멤버 중에 로그인한 사람이 있으면
						nowLoginList.put(chatMemList.get(i), userList.get(chatMemList.get(i))); // 지금 로그인한 리스트에 로그인한
																								// 사람들의 세션 정보를 넣어줌
					}
				}

				if ("chatting".equals(chatting) && nowLoginList != null) { // 채팅메시지를 보냈고 채팅방에 해당하는 멤버가 로그인 되어있을 때
					
					for(String key : nowLoginList.keySet()) { // 지금 로그인한 멤버들의 사이즈만큼 돌려서
						TextMessage tmpMsg = new TextMessage(senderId1 + "," + senderNm + "," + content + "," + to);
						System.out.println("tmpMsg : " + tmpMsg);
						
						//if() { // 나에게는 보내지 않음.
							nowLoginList.get(key).sendMessage(tmpMsg); // 지금 로그인하고 있는 사용자에 메시지에서 가져온 senderId가 있는지 확인
						//}

					}
				}

			}else if(strs != null && strs.length == 3 && strs[0].equals("notify")) {
				String notify = strs[0];	// 알림
//				String notify_cd = strs[1]; // 알림코드 (N01 : 프로젝트, N02 : 업무알림, N03 : 채팅알림, N04 : 1:1답변)
				String userNm = strs[1];	// 사용자
				String not_con = strs[2]; // 내용
				
				NotificationVo notifyVo = new NotificationVo();
				notifyVo.setNot_con(msg); // message.getPayload() => 내가 실제로 받은 메세지 
				
				WebSocketSession writerSession = userList.get(userNm); // 게시글작성자
				if("notify".equals(notify) && writerSession != null) {
					logger.debug("!@# userList : {}",userList);
					
					Set set = userList.keySet();
					Iterator iterator = set.iterator();
					logger.debug("!@#set : {}",set);
					logger.debug("!@#iterator : {}",iterator);
					
					while(iterator.hasNext()){
						  String key = (String)iterator.next();
						  logger.debug("!@# keyset : {}",key);
						  
						  if(key.equals(userNm)) {
							  TextMessage tmpMsg = new TextMessage(userNm+"님에게"+not_con+"가 배정되었습니다.*" + userService.countCnt(userNm));
								writerSession.sendMessage(tmpMsg);
						  }
					}
					
				}

			} else if (strs != null && strs.length == 2 ) { // 접속체크
				String user_email = strs[1];
				if("prjMem".equals(strs[0])) {
					List<Project_MemVo> memList = prjMemService.getMyProjectMemList(user_email);
					String mem_str = "";
					
					Set<String> keys = userList.keySet();
					for(String key : keys) {
						if(user_email.equals(key))
							continue;
						for(Project_MemVo memVo : memList) {
							if(memVo.getUser_email().equals(key)) {
								if(!"".equals(mem_str)) {
									mem_str += ",";
								}
								mem_str += key;
							}
						}
					}
					WebSocketSession wrtSession = userList.get(user_email);
					TextMessage memMsg = new TextMessage("lst:"+mem_str);
					wrtSession.sendMessage(memMsg);
				} else if ("fnd".equals(strs[0])) {
					List<ChatFriendsVo> fndList = fndService.friendList(user_email);
					logger.debug("friendList : {}", fndList);
					String fnd_str = "";
					
					Set<String> keys = userList.keySet();
					for(String key : keys) {
						if(user_email.equals(key))
							continue;
						
						if(!fndList.isEmpty()) {
							for(ChatFriendsVo fndVo : fndList) {
								if(fndVo.getFrd_email().equals(key)) {
									if(!"".equals(fnd_str)) {
										fnd_str += ",";
									}
									fnd_str += key;
								}
							}
						}
					}
					WebSocketSession wrtSession = userList.get(user_email);
					TextMessage fndMsg = new TextMessage("lst:"+fnd_str);
					wrtSession.sendMessage(fndMsg);
				}
			}else if(strs != null && strs.length == 3 && strs[0].equals("wrk_comment")) { // 업무코멘트 알림보내기
				logger.debug("!@#업무코멘트 메세지 들어오거라@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				String wrk_comment = strs[0];	// 업무코멘트알림
//				String notify_cd = strs[1]; // 알림코드 (N01 : 프로젝트, N02 : 업무알림, N03 : 채팅알림, N04 : 1:1답변)
				String rcv_email = strs[1];	// 받는사람
				String wrk_subject = strs[2]; // 업무 제목
				
				String notifiMsg = rcv_email+"님,"+wrk_subject+"에 코멘트가 작성이 되었습니다."; // 알림메세지 내용
				
				int plusCount = userService.plusCount(rcv_email);
				
				NotificationVo notifyVo = new NotificationVo();
				notifyVo.setNot_con(msg); // message.getPayload() => 내가 실제로 받은 메세지 
				
				NotificationVo notiVo = new NotificationVo();
				notiVo.setNot_id(notiVo.getNot_id());
				notiVo.setNot_cd("N02");
				notiVo.setNot_con(notifiMsg);
				int insertNoti = notificationService.insertNotifi(notiVo);
				
				ReceiverVo receiverVo = new ReceiverVo();
				receiverVo.setNot_id(notiVo.getNot_id());
				receiverVo.setRcv_email(rcv_email);
				
				int insertRecei = notificationService.insertReceiver(receiverVo);
				
				WebSocketSession writerSession = userList.get(rcv_email); // 받는사람
				if("wrk_comment".equals(wrk_comment) && writerSession != null) {
					logger.debug("!@# userList : {}",userList);
					
					Set set = userList.keySet();
					Iterator iterator = set.iterator();
					logger.debug("!@#set : {}",set);
					logger.debug("!@#iterator : {}",iterator);
					
					while(iterator.hasNext()){
						  String key = (String)iterator.next();
						  logger.debug("!@# keyset : {}",key);
						  
						  if(key.equals(rcv_email)) {
							  TextMessage tmpMsg = new TextMessage(rcv_email+"님,"+wrk_subject+"에 코멘트가 작성이 되었습니다. *" + userService.countCnt(rcv_email));
								writerSession.sendMessage(tmpMsg);
								
						  }
					}
					
				}

			}else if(strs != null && strs.length == 3 && strs[0].equals("project_setItem")) { // 프로젝트 설정 알림보내기
				logger.debug("!@#프로젝트 알림 메세지 들어오거라@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				String project_setItem = strs[0];	// 업무코멘트알림
//				String notify_cd = strs[1]; // 알림코드 (N01 : 프로젝트, N02 : 업무알림, N03 : 채팅알림, N04 : 1:1답변)
				String rcv_email = strs[1];	// 받는사람
				String prj_nm = strs[2]; // 프로젝트 이름
				
				String notifiMsg = rcv_email+"님,"+prj_nm+"의 설정이 변경 되었습니다."; // 알림메세지 내용
				
				int plusCount = userService.plusCount(rcv_email);
				
				NotificationVo notifyVo = new NotificationVo();
				notifyVo.setNot_con(msg); // message.getPayload() => 내가 실제로 받은 메세지 
				
				NotificationVo notiVo = new NotificationVo();
				notiVo.setNot_id(notiVo.getNot_id());
				notiVo.setNot_cd("N01");
				notiVo.setNot_con(notifiMsg);
				int insertNoti = notificationService.insertNotifi(notiVo);
				
				ReceiverVo receiverVo = new ReceiverVo();
				receiverVo.setNot_id(notiVo.getNot_id());
				receiverVo.setRcv_email(rcv_email);
				
				int insertRecei = notificationService.insertReceiver(receiverVo);
				
				WebSocketSession writerSession = userList.get(rcv_email); // 받는사람
				if("project_setItem".equals(project_setItem) && writerSession != null) {
					logger.debug("!@# userList : {}",userList);
					
					Set set = userList.keySet();
					Iterator iterator = set.iterator();
					logger.debug("!@#set : {}",set);
					logger.debug("!@#iterator : {}",iterator);
					
					while(iterator.hasNext()){
						  String key = (String)iterator.next();
						  logger.debug("!@# keyset : {}",key);
						  
						  if(key.equals(rcv_email)) {
							  TextMessage tmpMsg = new TextMessage(rcv_email+"님,"+prj_nm+"의 설정이 변경 되었습니다. *" + userService.countCnt(rcv_email));
								writerSession.sendMessage(tmpMsg);
						  }
					}
					
				}

			} else if(strs != null && strs.length == 3 && strs[0].equals("file&link")) { // 파일&링크  알림보내기
				logger.debug("!@#파일업로드 알림 메세지 들어오거라@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				String filelink = strs[0];	// 업무코멘트알림
//				String notify_cd = strs[1]; // 알림코드 (N01 : 프로젝트, N02 : 업무알림, N03 : 채팅알림, N04 : 1:1답변)
				String rcv_email = strs[1];	// 받는사람
				String work_nm = strs[2]; // 프로젝트 이름
				
				String notifiMsg = rcv_email+"님,"+work_nm+"에 파일&링크가 등록 되었습니다."; // 알림메세지 내용
				
				int plusCount = userService.plusCount(rcv_email);
				
				NotificationVo notifyVo = new NotificationVo();
				notifyVo.setNot_con(msg); // message.getPayload() => 내가 실제로 받은 메세지 
				
				NotificationVo notiVo = new NotificationVo();
				notiVo.setNot_id(notiVo.getNot_id());
				notiVo.setNot_cd("N02");
				notiVo.setNot_con(notifiMsg);
				int insertNoti = notificationService.insertNotifi(notiVo);
				
				ReceiverVo receiverVo = new ReceiverVo();
				receiverVo.setNot_id(notiVo.getNot_id());
				receiverVo.setRcv_email(rcv_email);
				
				int insertRecei = notificationService.insertReceiver(receiverVo);
				
				WebSocketSession writerSession = userList.get(rcv_email); // 받는사람
				if("file&link".equals(filelink) && writerSession != null) {
					logger.debug("!@# userList : {}",userList);
					
					Set set = userList.keySet();
					Iterator iterator = set.iterator();
					logger.debug("!@#set : {}",set);
					logger.debug("!@#iterator : {}",iterator);
					
					while(iterator.hasNext()){
						  String key = (String)iterator.next();
						  logger.debug("!@# keyset : {}",key);
						  
						  if(key.equals(rcv_email)) {
							  TextMessage tmpMsg = new TextMessage(rcv_email+"님,"+work_nm+"에 파일&링크가 등록 되었습니다.*" + userService.countCnt(rcv_email));
								writerSession.sendMessage(tmpMsg);
						  }
					}
					
				}

			} else if(strs != null && strs.length == 3 && strs[0].equals("videoNotify")) { // 화상회의  알림보내기
				logger.debug("!@#화상회의 알림 메세지 들어오거라@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				String videoNotify = strs[0];	// 업무코멘트알림
//				String notify_cd = strs[1]; // 알림코드 (N01 : 프로젝트, N02 : 업무알림, N03 : 채팅알림, N04 : 1:1답변)
				String text = strs[1]; // 화상회의 알림 내용
				String rcv_email = strs[2];	// 받는사람
				
				String notifiMsg = rcv_email+"님,"+text; // 알림메세지 내용
				
				int plusCount = userService.plusCount(rcv_email);
				
				NotificationVo notifyVo = new NotificationVo();
				notifyVo.setNot_con(msg); // message.getPayload() => 내가 실제로 받은 메세지 
				
				NotificationVo notiVo = new NotificationVo();
				notiVo.setNot_id(notiVo.getNot_id());
				notiVo.setNot_cd("N03");
				notiVo.setNot_con(notifiMsg);
				int insertNoti = notificationService.insertNotifi(notiVo);
				
				ReceiverVo receiverVo = new ReceiverVo();
				receiverVo.setNot_id(notiVo.getNot_id());
				receiverVo.setRcv_email(rcv_email);
				
				int insertRecei = notificationService.insertReceiver(receiverVo);
				
				WebSocketSession writerSession = userList.get(rcv_email); // 받는사람
				if("videoNotify".equals(videoNotify) && writerSession != null) {
					logger.debug("!@# userList : {}",userList);
					
					Set set = userList.keySet();
					Iterator iterator = set.iterator();
					logger.debug("!@#set : {}",set);
					logger.debug("!@#iterator : {}",iterator);
					
					while(iterator.hasNext()){
						  String key = (String)iterator.next();
						  logger.debug("!@# keyset : {}",key);
						  
						  if(key.equals(rcv_email)) {
							  TextMessage tmpMsg = new TextMessage(rcv_email+"님,"+text+"*" + userService.countCnt(rcv_email));
								writerSession.sendMessage(tmpMsg);
						  }
					}
					
				}

			}    
			
			
				
			

		}

	}

	// 통신을 끊었을 때 실행
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("afterConnectionClosed : " + session + ", status : " + status);

		String senderId = getId(session); // 유저의 이메일(아이디) 가져옴
		System.out.println("afterConnectionClosed senderId : " + senderId);
		userList.remove(getId(session)); // 연결 끊김
	}



}
