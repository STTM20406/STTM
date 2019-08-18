package kr.or.ddit.vote.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import kr.or.ddit.vote.model.VoteVo;
import kr.or.ddit.vote_item.model.Vote_ItemVo;

public interface IVoteService {

	/**
	 * Method : getVoteList
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-12 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 : 프로젝트에 있는 투표함 목록을 Html 태그 형식으로 반환하는 메서드
	 */
	String getVoteList(Map<String, Object>paramMap);
	
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
	Map<String, Object> voteDetail(Map<String, Object> paramMap);
	
	/**
	 * Method : deleteVote
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-14 최초 생성
	 * @param vote_id
	 * @return
	 * Method 설명 : 투표 아이디를 이용해 투표를 삭제처리(= 검색 리스트에서 제외)하는 메서드
	 */
	int deleteVote(Integer vote_id);

	/**
	 * Method : voteDetailMdf
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-16 최초 생성
	 * @param vote_id
	 * @return
	 * Method 설명 : 투표 아이디를 이용해 수정할 투표 내용을 HTML태그로 작성해 반환해주는 메서드
	 */
	Map<String, Object> voteDetailMdf(Integer vote_id);

	/**
	 * Method : voteModify
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-16 최초 생성
	 * @param params
	 * @return
	 * Method 설명 : 투표 내용 수정 메서드
	 * @throws ParseException 
	 */
	int voteModify(VoteVo voteVo);

	/**
	 * Method : deleteItems
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-17 최초 생성
	 * @param del_item_id
	 * Method 설명 : 투표 수정중 삭제된 투표 항목 삭제 메서드
	 */
	void deleteItems(List<String> del_item_id);

	/**
	 * Method : insertItems
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-17 최초 생성
	 * @param vote_item
	 * Method 설명 : 투표 수정중 새로 생성된 투표 항목 / 기존 투표항목 수정 메서드 
	 */
	void insertItems(List<Vote_ItemVo> vote_item);

	/**
	 * Method : checkDt
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-18 최초 생성
	 * @param paramMap
	 * @return
	 * Method 설명 : 투표 내용 수정 시 수정할 마감일이 시작일보다 24시간 이후인지 확인하는 메서드
	 */
	boolean checkDt(Map<String, Object> paramMap);
}
