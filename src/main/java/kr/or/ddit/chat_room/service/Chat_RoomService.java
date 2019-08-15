package kr.or.ddit.chat_room.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.chat_mem.model.Chat_MemVo;
import kr.or.ddit.chat_room.dao.IChat_RoomDao;
import kr.or.ddit.chat_room.model.Chat_RoomVo;
import kr.or.ddit.paging.model.PageVo;


@Service
public class Chat_RoomService implements IChat_RoomService{
	
	@Resource(name="chat_RoomDao")
	private IChat_RoomDao roomDao;

//
	@Override
	public int createRoom(String roomNm) {
		int cnt = roomDao.createRoom(roomNm);
		return cnt;
	}

	@Override
	public int createRoomProject(Chat_RoomVo vo) {
		int cnt = roomDao.createRoomProject(vo);
		return cnt;
	}
	
	
	
	@Override
	public List<Chat_RoomVo> getRoomList(String user_email) {
		List<Chat_RoomVo> list = roomDao.getRoomList(user_email);
		return list;
	}

	@Override
	public Chat_RoomVo nowWhereRoom(int ct_id) {
		Chat_RoomVo vo = roomDao.nowWhereRoom(ct_id);
		return vo;
	}

	@Override
	public int deleteChatRoom(int ct_id) {
		int cnt = roomDao.deleteChatRoom(ct_id);
		return cnt;
	}

	@Override
	public int maxRoomId() {
		int cnt = roomDao.maxRoomId();
		return cnt;
	}

	@Override
	public int updateChatTitle(Chat_RoomVo vo) {
		int cnt = roomDao.updateChatTitle(vo);
		return cnt;
	}

	@Override
	public List<Chat_RoomVo> getRoomListProject(String user_email) {
		List<Chat_RoomVo> list = roomDao.getRoomListProject(user_email);
		return list;
	}

	@Override
	public int deleteChatRoomProject(int prj_id) {
		int cnt = roomDao.deleteChatRoomProject(prj_id);
		return cnt;
	}

	@Override
	public Map<String, Object> pagingChatRoomList(PageVo page) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pagingChatRoomList", roomDao.pagingChatRoomList(page));
		
		int roomCnt = roomDao.chatRoomCnt(page.getUser_email());
		int paginationSize = (int) Math.ceil((double)roomCnt/page.getPageSize());
		
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}

	@Override
	public Map<String, Object> pagingChatRoomListProject(PageVo page) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pagingChatRoomList", roomDao.pagingChatRoomListProject(page));
		
		int roomCnt = roomDao.chatRoomCntProject(page.getUser_email());
		int paginationSize = (int) Math.ceil((double)roomCnt/page.getPageSize());
		
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}

	@Override
	public int chatRoomCnt(String user_email) {
		int cnt = roomDao.chatRoomCnt(user_email);
		return cnt;
	}

	@Override
	public int chatRoomCntProject(String user_email) {
		int cnt = roomDao.chatRoomCntProject(user_email);
		return cnt;
	}















	
	
}
