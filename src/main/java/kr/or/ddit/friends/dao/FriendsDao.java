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
	* Method : friendPagingList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-07
	* @param searcj
	* @return
	* Method 설명 : 일반 사용자가 자신의 친구 목록을  자신의 이메일로 페이징 리스트 조회
	 */
	@Override
	public List<FriendsVo> friendPagingList(Map<String, Object> user_email) {
		return sqlSession.selectList("friend.friendPagingList", user_email);
	}
	
	/**
	 * 
	* Method : friendPagingCnt
	* 작성자 : 김경호
	* 변경이력 : 2019-08-07
	* @param search
	* @return
	* Method 설명 : 일반 사용자가 자신의 친구 목록을 자신의 이메일로 검색한 갯수
	 */
	@Override
	public int friendPagingCnt(Map<String, Object> user_email) {
		return sqlSession.selectOne("friend.friendPagingCnt",user_email);
	}
}
