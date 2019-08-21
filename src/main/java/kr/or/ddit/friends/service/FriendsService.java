package kr.or.ddit.friends.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.friend_req.model.Friend_ReqVo;
import kr.or.ddit.friends.dao.IFriendsDao;
import kr.or.ddit.friends.model.ChatFriendsVo;
import kr.or.ddit.friends.model.FriendsVo;
import kr.or.ddit.users.model.UserVo;

@Service
public class FriendsService implements IFriendsService{
	
	private static final Logger logger = LoggerFactory.getLogger(FriendsService.class);

	@Resource(name="friendsDao")
	private IFriendsDao friendsDao;
	
	@Override
	public List<ChatFriendsVo> friendList(String user_email) {
		List<ChatFriendsVo> list = friendsDao.friendList(user_email);
		return list; 
	}
	
	/**
	 * 
	 * Method : insertFriends
	 * 작성자 : 김경호
	 * 변경이력 : 2019-08-08
	 * @param friendsVo
	 * @return
	 * Method 설명 : 친구 등록
	 */
	@Override
	public int insertFriends(FriendsVo friendsVo) {
		int insertFriends = 0;
		insertFriends += friendsDao.insertFriends(friendsVo);
		return insertFriends;
	}
	
	/**
	 * 
	* Method : accerptFriendRequest
	* 작성자 : 김경호
	* 변경이력 : 2019-08-20
	* @param friendsVo
	* @return
	* Method 설명 : 친구 요청 수락
	 */
	@Override
	public int accerptFriendRequest(FriendsVo friendsVo) {
		int accerptFriendRequest = 0;
		accerptFriendRequest += friendsDao.accerptFriendRequest(friendsVo);
		return accerptFriendRequest;
	}
	
	/**
	 * 
	* Method : friendPagingList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-07
	* @param searcj
	* @return
	* Method 설명 : 친구 페이징 리스트
	 */
	@Override
	public Map<String, Object> friendPagingList(Map<String, Object> map) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		logger.debug("map: {}", map);
		resultMap.put("userFriendsList", friendsDao.friendPagingList(map));
		
		int friendsCnt = friendsDao.friendPagingCnt(map);
		
		int paginationSize = (int) Math.ceil((double)friendsCnt/(int)map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}
	
	
	/**
	 * 
	* Method : friendPagingListByEmail
	* 작성자 : 김경호
	* 변경이력 : 2019-08-07
	* @param user_email
	* @return
	* Method 설명 : 일반 사용자가 자신의 친구 목록을  친구의 이메일로 검색
	 */
	@Override
	public Map<String, Object> friendSearchByEmail(Map<String, Object> frd_email) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<FriendsVo> friendsSearchList = friendsDao.friendSearchByEmail(frd_email);
		
		int pageSize = (int) frd_email.get("pageSize");
		int friendsCnt = friendsDao.friendSearchByEmailCnt(frd_email);
		int paginationSize = (int) Math.ceil((double)friendsCnt/(int)frd_email.get("pageSize"));
		
		resultMap.put("userFriendsList", friendsSearchList);
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}
	
	/**
	 * 
	* Method : deleteFriends
	* 작성자 : 김경호
	* 변경이력 : 2019-08-07
	* @param frd_email
	* @return
	* Method 설명 : 일반 사용자가 친구 삭제
	 */
	@Override
	public int deleteFriends(FriendsVo friendsVo) {
		return friendsDao.deleteFriends(friendsVo);
	}
	
	/**
	 * 
	* Method : deleteFriends2
	* 작성자 : 김경호
	* 변경이력 : 2019-08-21
	* @param friendsVo
	* @return
	* Method 설명 : 일반 사용자가 자신의 친구를 삭제하면 상대 방도 친구 삭제
	 */
	@Override
	public int deleteFriends2(FriendsVo friendsVo) {
		return friendsDao.deleteFriends(friendsVo);
	}
	
	/**
	 * 
	* Method : getFriend
	* 작성자 : 김경호
	* 변경이력 : 2019-08-20
	* @param user_email
	* @return
	* Method 설명 : 친구 정보 조회
	 */
	@Override
	public FriendsVo getFriend(String user_email) {
		return friendsDao.getFriend(user_email);
	}
	
	/**
	 * 
	* Method : friendsList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-21
	* @param user_email
	* @return
	* Method 설명 : 프로젝트 멤버에서 이미 친구인 사람의 친구 요청 버튼을 비활성화 시키기 위해서
	* 			     친구 목록을 조회하여 이미 친구 이면 비활성화
	 */
	@Override
	public List<FriendsVo> friendsList(String user_email) {
		return friendsDao.friendsList(user_email);
	}
	
}
