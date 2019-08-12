package kr.or.ddit.chat_content.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.chat_content.model.ChatParticipateUserVo;
import kr.or.ddit.chat_content.model.Chat_ContentVo;

@Repository
public class Chat_ContentDao implements IChat_ContentDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
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
		return sqlSession.selectList("chat.chatroomContentList",ct_id);
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
		return sqlSession.insert("chat.insertChatContent",vo);
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
		return sqlSession.delete("chat.deleteChatContent",vo);
	}

	@Override
	public int deleteChatContentProject(int prj_id) {
		return sqlSession.delete("chat.deleteChatContentProject",prj_id);
	}

	@Override
	public int outChatContentProject(Map<String, Object> map) {
		return sqlSession.delete("chat.outChatContentProject",map);
	}

}
