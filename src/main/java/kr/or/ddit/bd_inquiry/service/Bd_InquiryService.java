package kr.or.ddit.bd_inquiry.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.bd_inquiry.dao.IBd_InquiryDao;
import kr.or.ddit.bd_inquiry.model.Bd_InquiryVo;
import kr.or.ddit.paging.model.PageVo;

@Service
public class Bd_InquiryService implements IBd_InquiryService{
	@Resource(name="bd_InquiryDao")
	private IBd_InquiryDao bd_InquiryDao;
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
		return bd_InquiryDao.inquiryCnt(inq_cate);
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
		return bd_InquiryDao.inquiryInfo(inq_id);
	}
	
	
// 관리자
	
	/**
	 * Method 		: admGeneralList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @return
	 * Method 설명 	: 관리자 광고문의, 일반문의 게시글페이징 조회
	 */
	@Override
	public Map<String, Object> admInquiryList(PageVo pageVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//일반문의 리스트
		pageVo.setInq_cate("INQ01");
		resultMap.put("inquiryListOrigin", bd_InquiryDao.admInquiryList(pageVo));
		
		//광고문의 리스트
		pageVo.setInq_cate("INQ02");
		resultMap.put("inquiryListAd", bd_InquiryDao.admInquiryList(pageVo));
		
		
		
		int inquiryCntOrigin = bd_InquiryDao.inquiryCnt("INQ01");
		int inquiryCntAd = bd_InquiryDao.inquiryCnt("INQ02");
		int paginationSizeOrigin = (int) Math.ceil((double)inquiryCntOrigin/pageVo.getPageSize());
		int paginationSizeAd = (int) Math.ceil((double)inquiryCntAd/pageVo.getPageSize());
		resultMap.put("paginationSizeOrigin", paginationSizeOrigin);
		resultMap.put("paginationSizeAd", paginationSizeAd);
		
		return resultMap;
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
		return bd_InquiryDao.insertAdmPost(inquiryVo);
	}
	
	
	/**
	 * Method 		: admSearchSubList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param search
	 * @return
	 * Method 설명 	: 관리자 1:1문의 내용으로 제목페이징리스트
	 */
	@Override
	public Map<String, Object> admSearchSubList(Map<String, Object> search) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Bd_InquiryVo> admSearchSubList = bd_InquiryDao.admSearchSubList(search);
//		PageVo pageVo = (PageVo) search.get("pageVo");
		int pageSize = (int) search.get("pageSize");
		int searchCnt = bd_InquiryDao.admSearchSubCnt(search);
		
		int paginationSize = (int) Math.ceil((double)searchCnt/pageSize);
		
		resultMap.put("admSearchSubList",admSearchSubList);
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
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
	public Map<String, Object> admSearchConList(Map<String, Object> search) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Bd_InquiryVo> admSearchConList = bd_InquiryDao.admSearchConList(search);
//		PageVo pageVo = (PageVo) search.get("pageVo");
		int pageSize = (int) search.get("pageSize");
		int searchCnt = bd_InquiryDao.admSearchConCnt(search);
		
		int paginationSize = (int) Math.ceil((double)searchCnt/pageSize);
		
		resultMap.put("admSearchConList",admSearchConList);
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}
	
// 사용자
	
	/**
	 * Method 		: userGeneralList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @return
	 * Method 설명 	: 사용자 광고문의, 일반문의 게시글페이징 조회
	 */
	@Override
	public Map<String, Object> userInquiryList(PageVo pageVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//일반문의
		pageVo.setInq_cate("INQ01");
		resultMap.put("inquiryListOrigin", bd_InquiryDao.userList(pageVo));
		
		//광고문의
		pageVo.setInq_cate("INQ02");
		resultMap.put("inquiryListAd", bd_InquiryDao.userList(pageVo));
		
		//일반문의 게시글 개수
		int inquiryCntOrigin = bd_InquiryDao.inquiryCnt("INQ01");
		
		//광고문의 게시글 개수
		int inquiryCntAd = bd_InquiryDao.inquiryCnt("INQ02");
		
		int paginationSizeOrigin = (int) Math.ceil((double)inquiryCntOrigin/pageVo.getPageSize());
		int paginationSizeAd = (int) Math.ceil((double)inquiryCntAd/pageVo.getPageSize());
		resultMap.put("paginationSizeOrigin", paginationSizeOrigin);
		resultMap.put("paginationSizeAd", paginationSizeAd);
		
		return resultMap;
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
		return bd_InquiryDao.insertUserPost(inquiryVo);
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
		return bd_InquiryDao.deleteUserPost(inquiryVo);
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
		return bd_InquiryDao.updateUserPost(inquiryVo);
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
	public Map<String, Object> userSearchSubList(Map<String, Object> search) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Bd_InquiryVo> userSearchSubList = bd_InquiryDao.userSearchSubList(search);
//		PageVo pageVo = (PageVo) search.get("pageVo");
		int pageSize = (int) search.get("pageSize");
		int searchCnt = bd_InquiryDao.userSearchSubCnt(search);
		
		int paginationSize = (int) Math.ceil((double)searchCnt/pageSize);
		
		resultMap.put("userSearchSubList",userSearchSubList);
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
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
	public Map<String, Object> userSearchConList(Map<String, Object> search) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Bd_InquiryVo> userSearchConList = bd_InquiryDao.userSearchConList(search);
//		PageVo pageVo = (PageVo) search.get("pageVo");
		int pageSize = (int) search.get("pageSize");
		int searchCnt = bd_InquiryDao.userSearchConCnt(search);
		
		int paginationSize = (int) Math.ceil((double)searchCnt/pageSize);
		
		resultMap.put("userSearchConList",userSearchConList);
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}


	


}
