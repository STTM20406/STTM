package kr.or.ddit.friend_req.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.friend_req.model.Friend_ReqVo;

@Repository
public class Friend_ReqDao implements IFriend_ReqDao{
	
	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
	/**
	 * 
	* Method : firendsRequest
	* 작성자 : 김경호
	* 변경이력 : 2019-08-09
	* @param friendsReqVo
	* @return
	* Method 설명 : 친구 요청
	 */
	@Override
	public int firendsRequest(Friend_ReqVo friendsReqVo) {
		return sqlSession.insert("friend.firendsRequest",friendsReqVo);
	}
	
	/**
	 * 
	* Method : friendsRequestList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-19
	* @return
	* Method 설명 : 친구 요청 받은 목록
	 */
	@Override
	public List<Friend_ReqVo> friendsRequestList(String req_email) {
		return sqlSession.selectList("friend.friendsRequestList",req_email);
	}

}
