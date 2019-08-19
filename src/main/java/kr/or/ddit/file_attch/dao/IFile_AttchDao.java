package kr.or.ddit.file_attch.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.file_attch.model.File_AttchVo;
import kr.or.ddit.link_attch.model.Link_attchVo;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project_mem.model.Project_MemVo;

/**
 * IFile_AttchDao.java
 *
 * @author 손영하
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 박서경   최초 생성 2019-08-16
 *
 * </pre>
 */
public interface IFile_AttchDao {
	
	/**
	   * Method : getFile
	   * 작성자 : 손영하
	   * 변경이력 :
	   * @param file_id
	   * @return
	   * Method 설명 : 파일 한개의 정보를 가져오는 메서드
	   */
	   File_AttchVo getFile(int file_id);
	   
	   /**
	   * Method : FileList
	   * 작성자 : 손영하
	   * 변경이력 :
	   * @param prj_id
	   * @return
	   * Method 설명 : 해당 프로젝트의 file리스트를 받아옴
	   */
	   List<File_AttchVo> fileList(int prj_id);
	   
	   
	   /**
	   * Method : FPagination
	   * 작성자 : PC13
	   * 변경이력 :
	   * @param pageVo
	   * @return
	   * Method 설명 : FPagination
	   */
	   List<File_AttchVo> fPagination(Map<String, Object> map);
	   
	   /**
	    * Method : InsertFPagination
	    * 작성자 : PC13
	    * 변경이력 :
	    * @param pageVo
	    * @return
	    * Method 설명 : FPagination
	    */
	   List<File_AttchVo> insertFPagination(Map<String, Object> map);
	   
	   
	   /**
	    * Method : fileCnt
	    * 작성자 : PC13
	    * 변경이력 :
	    * @return
	    * Method 설명 : 해당 프로젝트의 전체 파일 수 조회
	    */
	   int fCnt(int wrk_id);
	   
	   /**
	   * Method : insertFile
	   * 작성자 : PC13
	   * 변경이력 :
	   * @return
	   * Method 설명 : 파일 등록 PU
	   */
	   int insertFile(File_AttchVo file_attchVo);
	   
	   /**
	    * Method : insertFile
	    * 작성자 : PC13
	    * 변경이력 :
	    * @return
	    * Method 설명 : 파일 등록 IN
	    */
	   int insertFileIN(File_AttchVo file_attchVo);
	
	 //공용보관함//공용보관함//공용보관함//공용보관함//공용보관함//공용보관함//공용보관함//공용보관함//공용보관함//공용보관함
	   /**
	 * Method 		: publicFilePagination
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-13 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 	: 공유함에서의 링크 pagination
	 */
	List<File_AttchVo> publicFilePagination(Map<String, Object> map);
	
		/**
	   * Method : fileCnt
	   * 작성자 : PC13
	   * 변경이력 :
	   * @return
	   * Method 설명 : 해당 프로젝트의 전체 파일 수 조회
	   */
	   int fileCnt(int prj_id);
	   
	   /**
	 * Method 		: publicLinkPagination
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-13 최초 생성
	 * @param map
	 * @return
	 * Method 설명 	: 공유함에서의 링크 pagination
	 */
	List<Link_attchVo> publicLinkPagination(Map<String, Object> map);
	   
	/**
	   * Method : fileCnt
	   * 작성자 : PC13
	   * 변경이력 :
	   * @return
	   * Method 설명 : 해당 프로젝트의 전체 링크 수 조회
	   */
	   int linkCnt(int prj_id);
	   
	   
	   /**
	   * Method : updateFile
	   * 작성자 : 손영하
	   * 변경이력 :
	   * @param file_id
	   * @return
	   * Method 설명 : 파일 한개의 정보를 삭제 여부의 상태값을 바꾸는 메서드
	   */
	   int updateFile(int file_id);
	   
	   /**
	   * Method : updateFile
	   * 작성자 : 손영하
	   * 변경이력 :
	   * @param file_id
	   * @return
	   * Method 설명 : 링크 한개의 정보를 삭제 여부의 상태값을 바꾸는 메서드
	   */
	   int updateLink(int link_id);
	
	 //개인보관함//개인보관함//개인보관함//개인보관함//개인보관함//개인보관함//개인보관함//개인보관함//개인보관함
	   
	   /**
	 * Method 		: individualPagination
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-16 최초 생성
	 * @param pageVo
	 * @return
	 * Method 설명 	: 개인보관함 Pagination
	 */
	List<File_AttchVo> individualPagination(Map<String, Object> map);
	
	/**
	 * Method 		: individualCnt
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-16 최초 생성
	 * @return
	 * Method 설명 	: 개인보관함 개수!
	 */
	int individualCnt(String user_email);
	    
	/**
	 * Method 		: individualCnt
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-16 최초 생성
	 * @return
	 * Method 설명 	: 보관함에서 검색했을 떄 Pagination
	 */
	List<File_AttchVo> individualSearchPagination(Map<String, Object> map);	
	
	/**
	 * Method 		: individualCnt
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-16 최초 생성
	 * @return
	 * Method 설명 	: 검색 수 개인보관함 개수!
	 */
	int searchIndividualCnt(String original_file_nm);
	
	/**
	 * Method 		: selectLV
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-17 최초 생성
	 * @param project_MemVo
	 * @return
	 * Method 설명 	: PM, PL 권한 받아오기!
	 */
	Project_MemVo selectLV(Project_MemVo project_MemVo);
}
