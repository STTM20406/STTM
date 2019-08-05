package kr.or.ddit.chat_mem.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.chat_content.model.ChatParticipateUserVo;
import kr.or.ddit.chat_mem.model.Chat_MemVo;

@Repository
public class Chat_MemDao implements IChat_MemDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
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
		return sqlSession.selectList("chat.roomFriendList",ct_id);
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
		return sqlSession.insert("chat.insertChatMem",vo);
	}

	
	/**
	 * 
	 * Method 		: deleteChatMem
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-19 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 채팅방 멤버 탈퇴
	 */
	@Override
	public int deleteChatMem(Chat_MemVo vo) {
		return sqlSession.delete("chat.deleteChatMem",vo);
	}

	@Override
	public List<String> roomFriendListEmail(int ct_id) {
		return sqlSession.selectList("chat.roomFriendListEmail",ct_id);
	}

	@Override
	public int countChatMem(int ct_id) {
		return sqlSession.selectOne("chat.countChatMem",ct_id);
	}

	@Override
	public List<ChatParticipateUserVo> inviteFriend(ChatParticipateUserVo vo) {
		return sqlSession.selectList("chat.inviteFriend",vo);
	}

}
