package kr.or.ddit.minutes.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.minutes.model.MinutesVo;
import kr.or.ddit.minutes.model.Minutes_MemVo;
import kr.or.ddit.users.model.UserVo;

public interface IMinutesDao {

	/**
	 * Method 		: minutesList
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 	: 해당 프로젝트에 프로젝트 리스트를 받아오는 메서드
	 */
	List<MinutesVo> minutesList(int prj_id);
	
	
	/**
	 * Method 		: minutesPagination
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 	: minutes Pagination
	 */
	List<MinutesVo> minutesPagination(Map<String, Object> map);
	
	/**
	 * Method 		: minutesCnt
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 	: 회의록 게시판 갯수를가져오는
	 */
	int minutesCnt(int prj_id);
	
	/**
	 * Method 		: minutesDetail
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param mnu_id
	 * @return
	 * Method 설명 	: minutes 상세정보 받아오는 메서드
	 */
	MinutesVo minutesDetail(int mnu_id);
	
	/**
	 * Method 		: searchPagination
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param map
	 * @return
	 * Method 설명 	: 검색한 결과 pagination
	 */
	List<MinutesVo> searchPagination(Map<String, Object> map);
	
	/**
	 * Method 		: minutes_memVo
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param mnu_id
	 * @return
	 * Method 설명 	: 회의 참석자 리스트를 받아오는 메서드
	 */
	List<Minutes_MemVo> attenderList(int mnu_id);
	
	/**
	 * Method 		: upMinutes
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param mnu_id
	 * @return
	 * Method 설명 	: 회의록 상태를 Y로 바꾸는! (삭제!)
	 */
	int upMinutes(int mnu_id);
	
	/**
	 * Method 		: memberList
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-09 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 내가 속해 있는 프로젝트 멤버 리스트
	 */
	List<UserVo> memberList(String user_email);
	
	/**
	 * Method 		: recentMinutes
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-09 최초 생성
	 * @return
	 * Method 설명 	: 가장 최근에 등록된 회의록 Vo가져오는 메서드
	 */
	MinutesVo recentMinutes();
	
	/**
	 * Method 		: insertAttender
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-09 최초 생성
	 * @param memVo
	 * @return
	 * Method 설명 	: 회의록 멤버 등록하는 메서드
	 */
	int insertAttender(Minutes_MemVo memVo);
	
	/**
	 * Method 		: whoAreYou
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-09 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: email로 이름을 찾아온다!!
	 */
	UserVo whoAreYou(String user_email);
	
	/**
	 * Method 		: insertMinutes
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-09 최초 생성
	 * @param minutes
	 * @return
	 * Method 설명 	: 회의록 등록
	 */
	int insertMinutes(MinutesVo minutesVo);
}
