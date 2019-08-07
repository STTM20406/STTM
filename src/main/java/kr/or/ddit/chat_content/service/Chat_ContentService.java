package kr.or.ddit.chat_content.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.chat_content.dao.IChat_ContentDao;
import kr.or.ddit.chat_content.model.ChatParticipateUserVo;
import kr.or.ddit.chat_content.model.Chat_ContentVo;

@Service
public class Chat_ContentService implements IChat_ContentService{

	@Resource(name="chat_ContentDao")
	private IChat_ContentDao contentDao;
	
	/**
	 * .
	 * Method 		: roomFriendList
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param ct_id
	 * @return
	 * Method 설명 	: 채팅방에 참여한 친구 리스트
	 */
	@Override
	public List<ChatParticipateUserVo> chatroomContentList(int ct_id) {
		List<ChatParticipateUserVo> list = contentDao.chatroomContentList(ct_id);
		return list;
	}

	/**
	 * 
	 * Method 		: insertChatContent
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 채팅방 대화 추가
	 */
	@Override
	public int insertChatContent(Chat_ContentVo vo) {
		int cnt = contentDao.insertChatContent(vo);
		return cnt;
	}

	
	/**
	 * 
	 * Method 		: deleteChatContent
	 * 작성자 			: 유다연 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 각 대화방에서의 각 사용자 대화 내역 삭제
	 */
	@Override
	public int deleteChatContent(Chat_ContentVo vo) {
		int cnt = contentDao.deleteChatContent(vo);
		return cnt;
	}

}
