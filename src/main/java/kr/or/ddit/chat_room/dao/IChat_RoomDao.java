package kr.or.ddit.chat_room.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.chat_mem.model.Chat_MemVo;
import kr.or.ddit.chat_room.model.Chat_RoomVo;
import kr.or.ddit.paging.model.PageVo;

public interface IChat_RoomDao {
		
	/**
	 * .
	 * Method 		: createRoom
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-20 최초 생성.
	 * @param roomNM
	 * @return
	 * Method 설명 	: 채팅방 생성 (insertChatMem을 같이 실행해서 채팅방 생성한 회원을 채팅 멤버에 추가)
	 */
	public int createRoom(String chatNm);
	
	/**
	 * .
	 * Method 		: createRoom
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-20 최초 생성.
	 * @param roomNM
	 * @return
	 * Method 설명 	: 채팅방 생성 (insertChatMem을 같이 실행해서 채팅방 생성한 회원을 채팅 멤버에 추가), 프로젝트 채팅방 생성
	 */
	public int createRoomProject(Chat_RoomVo vo);
	
	
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
	 * Method 		: getRoomListProject
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 자신이 참여되어 있는 채팅방 리스트 프로젝트
	 */
	public List<Chat_RoomVo> getRoomListProject(String user_email);
	
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
	
	
	/**
	 * 
	 * Method 		: deleteChatRoomProject
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-28 최초 생성
	 * @param ct_id
	 * @return
	 * Method 설명 	: 방 삭제 프로젝트
	 */
	public int deleteChatRoomProject(int prj_id);
	
	/**
	 * 
	 * Method 		: selectRoomId
	 * 작성자 			: 유다연 
	 * 변경이력 		: 2019-08-01 최초 생성
	 * @return
	 * Method 설명 	: 채팅방 아이디 조회
	 */
	public List<Integer> selectRoomId();
	
	/**
	 * 
	 * Method 		: maxRoomId
	 * 작성자 			: 유다연 
	 * 변경이력 		: 2019-08-02 최초 생성
	 * @return
	 * Method 설명 	: 채팅방 max id
	 */
	public int maxRoomId();
	
	/**
	 * 
	 * Method 		: updateChatTitle
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-05 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 채팅방 이름 수정
	 */
	public int updateChatTitle(Chat_RoomVo vo);
	
	/**
	 * 
	 * Method 		: pagingChatRoomList
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-14 최초 생성
	 * @param page
	 * @return
	 * Method 설명 	: 페이징 처리한 내가 속한 채팅방 리스트
	 */
	public List<Chat_RoomVo> pagingChatRoomList(PageVo page);
	
	/**
	 * 
	 * Method 		: pagingChatRoomListProject
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-14 최초 생성
	 * @param page
	 * @return
	 * Method 설명 	: 페이징 처리한 내가 속한 채팅방 리스트 프로제트
	 */
	public List<Chat_RoomVo> pagingChatRoomListProject(PageVo page);
	
	/**
	 * 
	 * Method 		: chatRoomCnt
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-14 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 내가 속한 채팅방 개수
	 */
	public int chatRoomCnt(String user_email);
	
	/**
	 * 
	 * Method 		: chatRoomCntProject
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-14 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 내가 속한 채팅방 개수 프로젝트
	 */
	public int chatRoomCntProject(String user_email);
	
	
}
