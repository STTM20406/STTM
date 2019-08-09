package kr.or.ddit.friend_req.dao;

import kr.or.ddit.friend_req.model.Friend_ReqVo;

public interface IFriend_ReqDao {

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
	
}
