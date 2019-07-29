package kr.or.ddit.chat_mem.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.chat_mem.dao.IChat_MemDao;
import kr.or.ddit.chat_mem.model.Chat_MemVo;

@Service
public class Chat_MemService implements IChat_MemService{

	@Resource(name="chat_MemDao")
	private IChat_MemDao memDao;
	
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
	 * Method 		: insertChatMem
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 채팅방 멤버 추가
	 */
	@Override
	public int insertChatMem(Chat_MemVo vo) {
		int cnt = memDao.deleteChatMem(vo);
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

}
