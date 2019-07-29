package kr.or.ddit.bd_inquiry.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.bd_inquiry.model.Bd_InquiryVo;
import kr.or.ddit.paging.model.PageVo;

/**
 * IBd_InquiryDao.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 *	관리자 : (일반문의, 광고문의) DAO
 *	사용자 : (일반문의, 광고문의) DAO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-21
 *
 * </pre>
 */
/**
 * IBd_InquiryDao.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-25
 *
 * </pre>
 */
public interface IBd_InquiryDao {
// 공통	
	/**
	 * Method 		: inquiryCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @return
	 * Method 설명 	: 1:1문의 게시글 갯수
	 */
	int inquiryCnt();
	
	/**
	 * Method 		: inquiryInfo
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param inq_id
	 * @return
	 * Method 설명 	: 1:1문의 게시글 상세조회
	 */
	Bd_InquiryVo inquiryInfo(int inq_id);
	
// *************************************************************************관리자****************************************************************************************
	
	/**
	 * Method 		: admGeneralList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @return
	 * Method 설명 	: 관리자 광고문의, 일반문의 게시글페이징 조회
	 */
	List<Bd_InquiryVo> admInquiryList(PageVo pageVo);

	/**
	 * Method 		: insertAdmPost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @param inquiryVo
	 * @return
	 * Method 설명 	: 관리자가 사용자 문의게시글에 답글해준다.
	 */
	int insertAdmPost(Bd_InquiryVo inquiryVo);
	
	/**
	 * Method 		: admSearchSubList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 관리자 1:1문의 제목으로 검색페이징리스트
	 */
	List<Bd_InquiryVo> admSearchSubList(Map<String,Object> search);

	/**
	 * Method 		: admSearchSubCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 관리자1:1문의 제목으로 검색페이징 개수
	 */
	int admSearchSubCnt(Map<String,Object> search);
	
	/**
	 * Method 		: admSearchConList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 관리자 1:1문의 내용으로 검색페이징리스트
	 */
	List<Bd_InquiryVo> admSearchConList(Map<String,Object> search);
	
	/**
	 * Method 		: admSearchConCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 관리자 1:1문의 내용으로 검색페이징 개수
	 */
	int admSearchConCnt(Map<String,Object> search);
	
// *************************************************************************사용자***********************************************************************************

	/**
	 * Method 		: userGeneralList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @return
	 * Method 설명 	: 사용자 광고문의, 일반문의 게시글페이징 조회
	 */
	List<Bd_InquiryVo> userInquiryList(PageVo pageVo);
	/**
	 * Method 		: insertUserPost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @param inquiryVo
	 * @return
	 * Method 설명 	: 사용자 문의게시글 작성
	 */
	int insertUserPost(Bd_InquiryVo inquiryVo);
	
	/**
	 * Method 		: deleteUserPost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-23 최초 생성
	 * @param inquiryVo
	 * @return
	 * Method 설명 	: 사용자 문의게시글 삭제
	 */
	int deleteUserPost(Bd_InquiryVo inquiryVo);
	
	/**
	 * Method 		: updateUserPost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-23 최초 생성
	 * @param inquiryVo
	 * @return
	 * Method 설명 	: 사용자 문의게시글 수정
	 */
	int updateUserPost(Bd_InquiryVo inquiryVo);
	
	/**
	 * Method 		: userSearchSubList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 사용자 1:1문의 제목으로 검색페이징리스트
	 */
	List<Bd_InquiryVo> userSearchSubList(Map<String,Object> search);

	/**
	 * Method 		: userSearchSubCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 사용자 1:1문의 제목으로 검색페이징 개수
	 */
	int userSearchSubCnt(Map<String,Object> search);
	
	/**
	 * Method 		: userSearchConList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 사용자 1:1문의 내용으로 검색페이징리스트
	 */
	List<Bd_InquiryVo> userSearchConList(Map<String,Object> search);
	
	/**
	 * Method 		: userSearchConCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 사용자 1:1문의 내용으로 검색페이징 개수
	 */
	int userSearchConCnt(Map<String,Object> search);
}
