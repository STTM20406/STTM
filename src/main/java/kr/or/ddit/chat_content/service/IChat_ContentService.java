package kr.or.ddit.chat_content.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.chat_content.model.ChatParticipateUserVo;
import kr.or.ddit.chat_content.model.Chat_ContentVo;
import kr.or.ddit.project.model.ProjectVo;

public interface IChat_ContentService {
	
	
	/**
	 * .
	 * Method 		: chatroomContentList
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param ct_id
	 * @return
	 * Method 설명 	: 채팅방 대화 내용 리스트 (채팅방별 대화 리스트)
	 */
	public List<ChatParticipateUserVo> chatroomContentList(int ct_id);
	
	/**
	 * 
	 * Method 		: insertChatContent
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 채팅방 대화 추가
	 */
	public int insertChatContent(Chat_ContentVo vo);
	
	/**
	 * 
	 * Method 		: deleteChatContent
	 * 작성자 			: 유다연 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 각 대화방에서의 각 사용자 대화 내역 삭제
	 */
	public int deleteChatContent(Chat_ContentVo vo);
	
	/**
	 * 
	 * Method 		: deleteChatContent
	 * 작성자 			: 유다연 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 각 대화방에서의 각 사용자 대화 내역 삭제 프로젝트
	 */
	public int outChatContentProject(ProjectVo vo);
	
	/**
	 * 
	 * Method 		: deleteChatContentProject
	 * 작성자 			: 유다연 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 각 대화방에서의 각 사용자 대화 내역 삭제, 프로젝트
	 */
	public int deleteChatContentProject(int prj_id);
	
	/**
	 * 
	 * Method 		: maxChatContentId
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-20 최초 생성
	 * @param ct_id
	 * @return
	 * Method 설명 	: 채팅방의 최대값 대화 아이디
	 */
	public int maxChatContentId(int ct_id);
	
	/**
	 * 
	 * Method 		: getContent
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-20 최초 생성
	 * @param ct_con_id
	 * @return
	 * Method 설명 	: 대화내역 정보 가져오기
	 */
	public Chat_ContentVo getContent(int ct_con_id);
}
