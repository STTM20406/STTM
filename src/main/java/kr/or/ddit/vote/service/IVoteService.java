package kr.or.ddit.vote.service;

import java.util.Map;

public interface IVoteService {

	/**
	 * Method : getVoteList
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-12 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 : 프로젝트에 있는 투표함 목록을 Html 태그 형식으로 반환하는 메서드
	 */
	String getVoteList(Integer prj_id);
	
	/**
	 * Method : insertVote
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-12 최초 생성
	 * @param paramMap
	 * @return
	 * Method 설명 : 새로운 투표와 투표 항목을 생성하는 메서드
	 */
	int insertVote(Map<String, Object> paramMap);

	/**
	 * Method : voteDetail
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-12 최초 생성
	 * @param vote_id
	 * @return
	 * Method 설명 : 투표 아이디로 투표 내용을 HTML태그로 작성해 반환해주는 메서드
	 */
	Map<String, Object> voteDetail(Integer vote_id);
}
