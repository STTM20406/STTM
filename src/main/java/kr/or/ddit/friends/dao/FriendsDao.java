package kr.or.ddit.friends.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.friends.model.ChatFriendsVo;
import kr.or.ddit.friends.model.FriendsVo;

@Repository
public class FriendsDao implements IFriendsDao{
	
	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;

	@Override
	public List<ChatFriendsVo> friendList(String user_email) {
		return sqlSession.selectList("friend.allFriendList",user_email);
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
		return sqlSession.insert("friend.insertFriends", friendsVo);
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
	public List<FriendsVo> friendPagingList(Map<String, Object> map) {
		return sqlSession.selectList("friend.friendPagingList", map);
	}
	
	/**
	 * 
	* Method : friendPagingCnt
	* 작성자 : 김경호
	* 변경이력 : 2019-08-07
	* @param search
	* @return
	* Method 설명 : 친구 페이징 리스트 수
	 */
	@Override
	public int friendPagingCnt(Map<String, Object> map) {
		return sqlSession.selectOne("friend.friendPagingCnt", map);
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
	public List<FriendsVo> friendSearchByEmail(Map<String, Object> frd_email) {
		return sqlSession.selectList("friend.friendSearchByEmail", frd_email);
	}
	
	/**
	 * 
	* Method : friendPagingByEmailCnt
	* 작성자 : 김경호
	* 변경이력 : 2019-08-07
	* @param user_email
	* @return
	* Method 설명 : 일반 사용자가 자신의 친구 목록을 친구의 이메일로 검색한 갯수
	 */
	@Override
	public int friendSearchByEmailCnt(Map<String, Object> frd_email) {
		return sqlSession.selectOne("friend.friendSearchByEmailCnt",frd_email);
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
	public int deleteFriends(String frd_email) {
		return sqlSession.delete("friend.deleteFriends",frd_email);
	}

}
