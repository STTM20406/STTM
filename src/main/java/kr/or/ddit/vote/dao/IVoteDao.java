package kr.or.ddit.vote.dao;

import java.util.List;

import kr.or.ddit.vote.model.VoteVo;

public interface IVoteDao {
	/**
	 * Method : voteList
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-12 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 : 프로젝트에 존재하는 투표 목록 조회 메서드
	 */
	List<VoteVo> voteList(Integer prj_id);
	
	/**
	 * Method : insertVote
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-12 최초 생성
	 * @param voteVo
	 * @return
	 * Method 설명 : 새로운 투표 생성 메서드
	 */
	int insertVote(VoteVo voteVo);
}
