package kr.or.ddit.chat_room.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.chat_mem.model.Chat_MemVo;
import kr.or.ddit.chat_room.model.Chat_RoomVo;
import kr.or.ddit.paging.model.PageVo;

@Repository
public class Chat_RoomDao implements IChat_RoomDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	

	@Override
	public int createRoom(String roomNm) {
		return sqlSession.insert("chat.createRoom",roomNm);
	}
	
	@Override
	public int createRoomProject(Chat_RoomVo vo) {
		return sqlSession.insert("chat.createRoomProject",vo);
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
		return sqlSession.delete("chat.deleteChatRoom",ct_id);
	}

	@Override
	public List<Integer> selectRoomId() {
		return sqlSession.selectList("chat.selectRoomId");
	}

	@Override
	public int maxRoomId() {
		return sqlSession.selectOne("chat.maxRoomId");
	}

	@Override
	public int updateChatTitle(Chat_RoomVo vo) {
		return  sqlSession.update("chat.updateChatTitle",vo);
	}

	@Override
	public List<Chat_RoomVo> getRoomListProject(String user_email) {
		return sqlSession.selectList("chat.getRoomListProject",user_email);
	}

	@Override
	public int deleteChatRoomProject(int prj_id) {
		return sqlSession.delete("chat.deleteChatRoomProject",prj_id);
	}

	@Override
	public List<Chat_RoomVo> pagingChatRoomList(PageVo page) {
		return sqlSession.selectList("chat.pagingChatRoomList",page);
	}

	@Override
	public List<Chat_RoomVo> pagingChatRoomListProject(PageVo page) {
		return sqlSession.selectList("chat.pagingChatRoomListProject",page);
	}

	@Override
	public int chatRoomCnt(String user_email) {
		return sqlSession.selectOne("chat.chatRoomCnt",user_email);
	}

	@Override
	public int chatRoomCntProject(String user_email) {
		return sqlSession.selectOne("chat.chatRoomCntProject",user_email);
	}



//

	
	

}
