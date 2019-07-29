package kr.or.ddit.chat_room.service;

import java.util.List;

import kr.or.ddit.chat_room.model.Chat_RoomVo;

public interface IChat_RoomService {

	/**
	 * 
	 * Method 		: createRoom
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-20 최초 생성
	 * @param roomNM
	 * @return
	 * Method 설명 	: 채팅방 생성 (insertChatMem을 같이 실행해서 채팅방 생성한 회원을 채팅 멤버에 추가)
	 */
	public int createRoom(String roomNM);
	
	/**
	 * 
	 * Method 		: getRoomList
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 자신이 참여되어 있는 채팅방 리스트 
	 */
	public List<Chat_RoomVo> getRoomList(String user_email);
	
	
	/**
	 * 
	 * Method 		: nowWhereRoom
	 * 작성자 			: 유다연 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param ct_id
	 * @return
	 * Method 설명 	: 현재 어느방에 들어가 있는지 확인
	 */
	public Chat_RoomVo nowWhereRoom(int ct_id);
	
	/**
	 * 
	 * Method 		: deleteChatRoom
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-28 최초 생성
	 * @param ct_id
	 * @return
	 * Method 설명 	: 방 삭제
	 */
	public int deleteChatRoom(int ct_id);
}
