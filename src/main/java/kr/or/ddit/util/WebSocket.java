package kr.or.ddit.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.or.ddit.chat_content.model.Chat_ContentVo;
import kr.or.ddit.chat_content.service.Chat_ContentService;
import kr.or.ddit.chat_mem.model.Chat_MemVo;
import kr.or.ddit.chat_mem.service.Chat_MemService;
import kr.or.ddit.chat_room.model.Chat_RoomVo;
import kr.or.ddit.chat_room.service.Chat_RoomService;
import kr.or.ddit.users.model.UserVo;

//작성순서 : afterConnectionEstablished(서버 접속 시) -> afterConnectionClosed(서버연결끊을 시)
//		  -> handleTextMessage(서버가 메세지를 받았을 때)
// 기타 필요한 method는 중간에 필요에 따라 작성
// 서버 연결에 필요한 wesocket변수들도 필요에 따라 추가

//@EnableWebSocket
public class WebSocket extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(WebSocket.class);

	@Resource(name = "chat_RoomService")
	private Chat_RoomService roomService;

	@Resource(name = "chat_MemService")
	private Chat_MemService memService;

	@Resource(name = "chat_ContentService")
	private Chat_ContentService contentService;

	// 서버에 연결된 사용자들을 저장하기 위해 선언
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();// 메시지를 날려주기 위한 웹소켓전용 세션
	private Map<String, WebSocketSession> userList = new HashMap<String, WebSocketSession>();// 세션에 로그인한 모든 로그인 정보가 담김
	private Map<WebSocketSession, String> roomList = new HashMap<WebSocketSession, String>(); // 실제 session의 아이디정보,
																								// room정보

	private List<WebSocketSession> sessions = new ArrayList<>();
//	private Map<String, WebSocketSession> userSessions = new HashMap<>();

	// 연결되었을 때
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished :" + session);
		// session : 세션마다 주는 아이디

		sessions.add(session); // 세션에 있는 모든 정보를 담음
		String senderId = getId(session); // galbi@naver.com
		userList.put(senderId, session);

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
			if (strs != null && strs.length == 5) {
				String chatting = strs[0];
				String senderNm = strs[1];
				String content = strs[2];
				String senderId1 = strs[3];
				String ct_id = strs[4];

				//들어온 메시지 내용 저장
				Chat_ContentVo contentVo = new Chat_ContentVo(Integer.parseInt(ct_id), senderId1, content);
				contentService.insertChatContent(contentVo);
				
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
						TextMessage tmpMsg = new TextMessage(senderId1 + "," + senderNm + "," + content);
						System.out.println("tmpMsg : " + tmpMsg);
						
						//if() { // 나에게는 보내지 않음.
							nowLoginList.get(key).sendMessage(tmpMsg); // 지금 로그인하고 있는 사용자에 메시지에서 가져온 senderId가 있는지 확인
						//}

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
		userList.remove(session); // 연결 끊김

	}

	// json형태로 메시지 변환(일반 메시지 보낼 때)
	public String JsonData(String name, Object msg) {
		JsonObject jsonObject = Json.createObjectBuilder()
				.add("message", "<dl class='chat_other'>" + "<dt>" + name + "</dt> <dd>" + msg + "</dd></dl>").build();

		StringWriter write = new StringWriter();

		try (JsonWriter jsonWriter = Json.createWriter(write)) {
			jsonWriter.write(jsonObject);
		}
		;

		return write.toString();
	}

	// json형태로 메시지 변환 (접속했음을 알릴 때)
	public String JsonDataOpen(String id) {
		JsonObject jsonObject = Json.createObjectBuilder().add("message", "<a href='#none' onclick=\"insertWisper('"
				+ id + "')\">" + "<b>[" + id + "]</b> 님이 <b style='color:blue'>접속</b>하셨습니다.</a>").build();

		StringWriter write = new StringWriter();

		try (JsonWriter jsonWriter = Json.createWriter(write)) {
			jsonWriter.write(jsonObject);
		}
		;

		return write.toString();
	}

	// json형태로 유저 정보 날리기
	public String JsonUser(String id) {

		JsonObject jsonObject = Json.createObjectBuilder().add("list", id).build();
		StringWriter write = new StringWriter();

		try (JsonWriter jsonWriter = Json.createWriter(write)) {
			jsonWriter.write(jsonObject);
		}
		;

		return write.toString();
	}

	// json형태로 방 정보 날리기
	public String JsonRoom(String roomNames) {
		JsonObject jsonObject = Json.createObjectBuilder().add("room", roomNames).build();
		StringWriter write = new StringWriter();

		try (JsonWriter jsonWriter = Json.createWriter(write)) {
			jsonWriter.write(jsonObject);
		}
		;

		return write.toString();
	}

	// 유저리스트
	private List<String> informUser(Map<WebSocketSession, String> maplist, String room) {

		// 맵을 이용해서 세션을 통해 아이디값을 value로 가져와서 list에 담기

		// 1.담을 리스트 껍데기 선언
		List<String> list = new ArrayList<String>();

		// 2.존재하는 웹소켓 아이디, 로그인 아이디만큼 while문을 돌려준다
		Iterator<WebSocketSession> sessionIds = maplist.keySet().iterator();
		while (sessionIds.hasNext()) {
			WebSocketSession sessionId = sessionIds.next();
			String value = maplist.get(sessionId); // 실제 아이디값

			// 3.해당 번지의 key값에 해당하는 방의 이름정보를 가져옴
			String userRoom = roomList.get(sessionId);

			// 4.지금 돌고있는 while문에서 추출한 방이름과 들어온 방의 이름이 같을 경우 리스트에 저장하도록 함
			if (userRoom.equals(room)) {
				System.out.println("아이디:" + value + ", 방이름: " + userRoom);
				list.add(value);
			}
		}
		return list;
	}

	// DB로부터 존재하는 방정보 String 형태로 가져오기
	public String getRoomName(String user_email) {
		List<Chat_RoomVo> roomList = roomService.getRoomList(user_email);
		String room = sessionList.size() + "";

		for (int i = 0; i < roomList.size(); i++) {
			room += ",";
			room += roomList.get(i).getCt_nm() + "/";
			room += roomList.get(i).getCt_id() + "/";
			room += roomList.get(i).getCt_dt() + "/";

		}
		return room;
	}

}
