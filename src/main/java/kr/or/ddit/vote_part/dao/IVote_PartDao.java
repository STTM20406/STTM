package kr.or.ddit.vote_part.dao;

import java.util.List;
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
	
	List<Vote_PartVo> partList(Integer vote_id);

	/**
	 * Method : deleteVotePart
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-17 최초 생성
	 * @param del_item_list
	 * Method 설명 : 삭제된 투표 항목의 투표 내역을 삭제하는 메서드
	 */
	int deleteVotePart(List<Integer> del_item_list);
}
