package kr.or.ddit.vote_part.service;

import java.util.Map;

import kr.or.ddit.vote_part.model.Vote_PartVo;

public interface IVote_PartService {
	/**
	 * Method : checkVote
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-13 최초 생성
	 * @param paramMap
	 * @return
	 * Method 설명 : 사용자가 해당 투표에 참가했는지를 확인하는 메서드
	 */
	Vote_PartVo checkVote(Map<String, Object> paramMap);
	
	/**
	 * Method : vote
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-13 최초 생성
	 * @param vote_PartVo
	 * @return
	 * Method 설명 : 투표 내용을 삽입하는 메서드
	 */
	int vote(Vote_PartVo vote_PartVo);
}
