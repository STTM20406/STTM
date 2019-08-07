package kr.or.ddit.friends.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.friends.dao.IFriendsDao;
import kr.or.ddit.friends.model.ChatFriendsVo;

@Service
public class FriendsService implements IFriendsService{

	@Resource(name="friendsDao")
	private IFriendsDao friendsDao;
	
	@Override
	public List<ChatFriendsVo> friendList(String user_email) {
		List<ChatFriendsVo> list = friendsDao.friendList(user_email);
		return list; 
	}
	
	/**
	 * 
	* Method : friendPagingList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-07
	* @param searcj
	* @return
	* Method 설명 : 일반 사용자가 자신의 친구 목록을  자신의 이메일로 페이징 리스트 조회
	 */
	@Override
	public Map<String, Object> friendPagingList(Map<String, Object> user_email) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("userFriendsList", friendsDao.friendPagingList(user_email));
		
		int friendsCnt = friendsDao.friendPagingCnt(user_email);
		
		int paginationSize = (int) Math.ceil((double)friendsCnt/(int)user_email.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}

}
