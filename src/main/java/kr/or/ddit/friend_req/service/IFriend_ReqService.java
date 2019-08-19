package kr.or.ddit.friend_req.service;

import java.util.List;

import kr.or.ddit.friend_req.model.Friend_ReqVo;

public interface IFriend_ReqService {
	
	/**
	 * 
	* Method : firendsRequest
	* 작성자 : 김경호
	* 변경이력 : 2019-08-09
	* @param friendsReqVo
	* @return
	* Method 설명 : 친구 요청
	 */
	int firendsRequest(Friend_ReqVo friendsReqVo);
	
	/**
	 * 
	* Method : friendsRequestList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-19
	* @return
	* Method 설명 : 친구 요청 받은 목록
	 */
	List<Friend_ReqVo> friendsRequestList(String req_email);
}
