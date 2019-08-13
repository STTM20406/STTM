package kr.or.ddit.vote_part.dao;

import java.util.Map;

import kr.or.ddit.vote_part.model.Vote_PartVo;

public interface IVote_PartDao {
	/**
	 * Method : checkVote
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-13 최초 생성
	 * @param paramMap
	 * @return
	 * Method 설명 : 사용자가 해당 투표에 참여헀는지 확인하는 메서드
	 */
	Vote_PartVo checkVote(Map<String, Object> paramMap);
	
	/**
	 * Method : vote
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-13 최초 생성
	 * @param vote_PartVo
	 * @return
	 * Method 설명 : 실제 투표내용을 삽입하는 메서드
	 */
	int vote(Vote_PartVo vote_PartVo);
}
