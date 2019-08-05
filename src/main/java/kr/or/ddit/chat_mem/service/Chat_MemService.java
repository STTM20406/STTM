package kr.or.ddit.chat_mem.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.chat_content.model.ChatParticipateUserVo;
import kr.or.ddit.chat_mem.dao.IChat_MemDao;
import kr.or.ddit.chat_mem.model.Chat_MemVo;
import kr.or.ddit.chat_room.dao.IChat_RoomDao;

@Service
public class Chat_MemService implements IChat_MemService{

	@Resource(name="chat_MemDao")
	private IChat_MemDao memDao;
	
	@Resource(name="chat_RoomDao")
	private IChat_RoomDao roomDao;
	
	private static final Logger logger = LoggerFactory.getLogger(Chat_MemService.class);
	
	/**
	 * 
	 * Method 		: roomFriendList
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param ct_id
	 * @return
	 * Method 설명 	: 채팅방에 참여한 친구 리스트
	 */
	@Override
	public List<String> roomFriendList(int ct_id) {
		
		List<String> list = memDao.roomFriendList(ct_id);
		return list;
	}
	
	/**
	 * 
	 * Method 		: roomFriendList
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param ct_id
	 * @return
	 * Method 설명 	: 채팅방에 참여한 친구 리스트 전체 .
	 */
	@Override
	public Map<Integer, Object> allRoomFriendList() {
		
		List<Integer> roomIdList = roomDao.selectRoomId();
		
		Map<Integer, Object> roomMap = new HashMap<Integer, Object>();
		
		// key : ct_id , value = 방 멤버
		for(int i=0;i<roomIdList.size();i++) {
			roomMap.put(roomIdList.get(i), memDao.roomFriendList(roomIdList.get(i)));
		}
		return roomMap;
	}

	/**
	 * 
	 * Method 		: insertChatMem
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 채팅방 멤버 추가
	 */
	@Override
	public int insertChatMem(Chat_MemVo vo) {
		int cnt = memDao.insertChatMem(vo);
		return cnt;
	}

	/**
	 * 
	 * Method 		: deleteChatMem
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 채팅방 멤버 탈퇴(이거 지우기 전에 대화내용 모두 지운 후에 탈퇴 가능,"deleteChatContent")
	 */
	@Override
	public int deleteChatMem(Chat_MemVo vo) {
		int cnt = memDao.deleteChatMem(vo);
		return cnt;
	}

	/**
	 * 
	 * Method 		: roomFriendListEmail
	 * 작성자 			: 유다연 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @param ct_id
	 * @return
	 * Method 설명 	: 각 채팅방의 멤버 아이디 리스트
	 */
	@Override
	public List<String> roomFriendListEmail(int ct_id) {
		List<String> list = memDao.roomFriendListEmail(ct_id);
		return list;
	}

	@Override
	public int countChatMem(int ct_id) {
		int cnt = memDao.countChatMem(ct_id);
		return cnt;
	}

	@Override
	public List<ChatParticipateUserVo> inviteFriend(ChatParticipateUserVo vo) {
		List<ChatParticipateUserVo> list = memDao.inviteFriend(vo);
		return list;
	}

}
