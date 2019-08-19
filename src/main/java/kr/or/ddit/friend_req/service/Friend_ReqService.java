package kr.or.ddit.friend_req.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.friend_req.dao.IFriend_ReqDao;
import kr.or.ddit.friend_req.model.Friend_ReqVo;

@Service
public class Friend_ReqService implements IFriend_ReqService{
	
	@Resource(name="friend_ReqDao")
	private IFriend_ReqDao friend_ReqDao;
	
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
		int friendsRequest = 0;
		friendsRequest += friend_ReqDao.firendsRequest(friendsReqVo);
		return friendsRequest;
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
		return friend_ReqDao.friendsRequestList(req_email);
	}

}
