package kr.or.ddit.vote.dao;

import java.util.List;
import java.util.Map;

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
	List<VoteVo> voteList(Map<String, Object>paramMap);
	
	/**
	 * Method : insertVote
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-12 최초 생성
	 * @param voteVo
	 * @return
	 * Method 설명 : 새로운 투표 생성 메서드
	 */
	int insertVote(VoteVo voteVo);
	
	/**
	 * Method : getVote
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-13 최초 생성
	 * @param vote_id
	 * @return
	 * Method 설명 : 투표 정보 조회 메서드
	 */
	VoteVo getVote(Integer vote_id);

	/**
	 * Method : deleteVote
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-14 최초 생성
	 * @param vote_id
	 * @return
	 * Method 설명 : 투표 아이디를 이용하여 투표를 삭제처리(= 검색에서 제외)하는 메서드
	 */
	int deleteVote(Integer vote_id);

	/**
	 * Method : getVoteCnt
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-15 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 : 프로젝트 아이디를 이용하여 검색 가능한 투표의 개수를 확인하는 메서드
	 */
	int getVoteCnt(Integer prj_id);
}
