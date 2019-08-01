package kr.or.ddit.chat_room.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.chat_room.model.Chat_RoomVo;

@Repository
public class Chat_RoomDao implements IChat_RoomDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	

	@Override
	public int createRoom(String roomNM) {
		return sqlSession.insert("chat.createRoom",roomNM);
	}

	@Override
	public List<Chat_RoomVo> getRoomList(String user_email) {
		return sqlSession.selectList("chat.getRoomList", user_email);
	}

	@Override
	public Chat_RoomVo nowWhereRoom(int ct_id) {
		return sqlSession.selectOne("chat.nowWhereRoom",ct_id);
	}

	@Override
	public int deleteChatRoom(int ct_id) {
		return sqlSession.selectOne("chat.deleteChatRoom",ct_id);
	}

	@Override
	public List<Integer> selectRoomId() {
		return sqlSession.selectList("chat.selectRoomId");
	}



	
	

}
