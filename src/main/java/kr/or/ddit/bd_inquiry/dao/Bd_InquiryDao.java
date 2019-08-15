package kr.or.ddit.bd_inquiry.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.bd_inquiry.model.Bd_InquiryVo;
import kr.or.ddit.paging.model.PageVo;

@Repository
public class Bd_InquiryDao implements IBd_InquiryDao{
	
	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;

// 공통
	
	/**
	 * Method 		: inquiryCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @return
	 * Method 설명 	: 1:1문의 게시글 갯수 
	 */
	@Override
	public int inquiryCnt(String inq_cate) {
		return sqlSession.selectOne("bd_inquiry.inquiryCnt", inq_cate);
	}
	
	
	/**
	 * Method 		: inquiryInfo
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param inq_id
	 * @return
	 * Method 설명 	: 1:1문의 게시글 상세조회
	 */
	@Override
	public Bd_InquiryVo inquiryInfo(int inq_id) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("bd_inquiry.inquiryInfo",inq_id);
	}
// 관리자*************************************************************************************************
	
	/**
	 * Method 		: admGeneralList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @return
	 * Method 설명 	: 관리자 일반문의, 광고문의 게시글페이징 조회
	 */

	@Override
	public List<Bd_InquiryVo> admInquiryList(PageVo pageVo) {
		return sqlSession.selectList("bd_inquiry.admList",pageVo);
	}


	/**
	 * Method 		: insertAdmPost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @param inquiryVo
	 * @return
	 * Method 설명 	: 관리자가 사용자 문의게시글에 답글해준다.
	 */
	@Override
	public int insertAdmPost(Bd_InquiryVo inquiryVo) {
		return sqlSession.update("bd_inquiry.updateInquiry",inquiryVo);
	}
	
	/**
	 * Method 		: admSearchSubList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 관리자 1:1문의 제목으로 검색페이징리스트
	 */
	@Override
	public List<Bd_InquiryVo> admSearchSubList(Map<String,Object> search) {
		return sqlSession.selectList("bd_inquiry.admSearchSub", search);
	}

	/**
	 * Method 		: admSearchSubCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 관리자1:1문의 제목으로 검색페이징 개수
	 */
	@Override
	public int admSearchSubCnt(Map<String, Object> search) {
		return sqlSession.selectOne("bd_inquiry.admSearchSubCnt",search);
	}

	/**
	 * Method 		: admSearchConList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 관리자 1:1문의 내용으로 검색페이징리스트
	 */
	@Override
	public List<Bd_InquiryVo> admSearchConList(Map<String,Object> search) {
		return sqlSession.selectList("bd_inquiry.admSearchCon",search);
	}

	/**
	 * Method 		: admSearchConCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 관리자 1:1문의 내용으로 검색페이징 개수
	 */
	@Override
	public int admSearchConCnt(Map<String, Object> search) {
		return sqlSession.selectOne("bd_inquiry.admSearchConCnt",search);
	}
	
// 사용자****************************************************************************************

	/**
	 * Method 		: userList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 사용자 일반문의,광고문의 게시글페이징 조회
	 */
	@Override
	public List<Bd_InquiryVo> userList(PageVo pageVo) {
		return sqlSession.selectList("bd_inquiry.userList",pageVo);
	}

	
	/**
	 * Method 		: insertUserPost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @param inquiryVo
	 * @return
	 * Method 설명 	: 사용자 문의게시글 작성
	 */
	@Override
	public int insertUserPost(Bd_InquiryVo inquiryVo) {
		return sqlSession.insert("bd_inquiry.userInsertInquiry",inquiryVo);
	}

	/**
	 * Method 		: deleteUserPost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-23 최초 생성
	 * @param inquiryVo
	 * @return
	 * Method 설명 	: 사용자 문의게시글 삭제
	 */
	@Override
	public int deleteUserPost(Bd_InquiryVo inquiryVo) {
		return sqlSession.update("bd_inquiry.userDeleteInquiry",inquiryVo);
	}

	/**
	 * Method 		: updateUserPost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-23 최초 생성
	 * @param inquiryVo
	 * @return
	 * Method 설명 	: 사용자 문의게시글 수정
	 */
	@Override
	public int updateUserPost(Bd_InquiryVo inquiryVo) {
		return sqlSession.update("bd_inquiry.userUpdateInquiry",inquiryVo);
	}

	/**
	 * Method 		: userSearchSubList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 사용자 제목으로 검색페이징리스트
	 */
	@Override
	public List<Bd_InquiryVo> userSearchSubList(Map<String, Object> search) {
		return sqlSession.selectList("bd_inquiry.userSearchSubList",search);
	}

	/**
	 * Method 		: userSearchSubCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 사용자 제목으로 검색페이징 개수
	 */
	@Override
	public int userSearchSubCnt(Map<String, Object> search) {
		return sqlSession.selectOne("bd_inquiry.userSearchSubCnt",search);
	}

	/**
	 * Method 		: userSearchConList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 사용자 내용으로 검색페이징리스트
	 */
	@Override
	public List<Bd_InquiryVo> userSearchConList(Map<String, Object> search) {
		return sqlSession.selectList("bd_inquiry.userSearchConList",search);
	}

	/**
	 * Method 		: userSearchConCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 사용자 내용으로 검색페이징 개수
	 */
	@Override
	public int userSearchConCnt(Map<String, Object> search) {
		return sqlSession.selectOne("bd_inquiry.userSearchConCnt",search);
	}









}
