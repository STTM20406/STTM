package kr.or.ddit.chat_mem.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.chat_content.model.ChatParticipateUserVo;
import kr.or.ddit.chat_mem.model.Chat_MemVo;
import kr.or.ddit.project.model.ProjectVo;

public interface IChat_MemService {
	
	/**
	 * .
	 * Method 		: roomFriendList
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param ct_id
	 * @return
	 * Method 설명 	: 채팅방에 참여한 친구 리스트.
	 */
	public List<String> roomFriendList(int ct_id);
	
	
	/**
	 * 
	 * Method 		: allRoomFriendList
	 * 작성자 			: 유다연 
	 * 변경이력 		: 2019-08-01 최초 생성
	 * @return
	 * Method 설명 	: 채팅방에 참여한 친구 리스트 전체
	 */
	public Map<Integer, Object> allRoomFriendList();
	
	/**
	 * 
	 * Method 		: insertChatMem
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 채팅방 멤버 추가
	 */
	public int insertChatMem(Chat_MemVo vo);
	
	/**
	 * 
	 * Method 		: insertChatMemProject
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 채팅방 멤버 추가 프로젝트
	 */
	public int insertChatMemProject(ProjectVo vo);
	/**
	 * 
	 * Method 		: deleteChatMem
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 채팅방 멤버 탈퇴
	 */
	public int deleteChatMem(Chat_MemVo vo);
	
	/**
	 * 
	 * Method 		: deleteChatMem
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 채팅방 멤버 탈퇴 프로젝트
	 */
	public int outChatMemProject(ProjectVo vo);
	
	/**
	 * 
	 * Method 		: deleteChatMem
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 채팅방의 프로젝트 멤버 모두 삭제
	 */
	public int deleteChatMemProject(int prj_id);
	
	/**
	 * 
	 * Method 		: roomFriendListEmail
	 * 작성자 			: 유다연 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @param ct_id
	 * @return
	 * Method 설명 	: 각 채팅방의 멤버 아이디 리스트
	 */
	public List<String> roomFriendListEmail(int ct_id);
	
	/**
	 * 
	 * Method 		: countChatMem
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-28 최초 생성
	 * @param ct_id
	 * @return
	 * Method 설명 	: 채팅방 멤버 수
	 */
	public int countChatMem(int ct_id);
	
	/**
	 * 
	 * Method 		: inviteFriend
	 * 작성자 			: 유다연 
	 * 변경이력 		: 2019-07-29 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 채팅방 초대할 친구 리스트 (이미 채팅방에 있는 친구는 제외하고 보여주는 리스트)
	 */
	public List<ChatParticipateUserVo> inviteFriend(ChatParticipateUserVo vo);
}
