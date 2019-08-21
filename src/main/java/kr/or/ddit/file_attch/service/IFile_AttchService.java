package kr.or.ddit.file_attch.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.file_attch.model.File_AttchVo;
import kr.or.ddit.link_attch.model.Link_attchVo;
import kr.or.ddit.project_mem.model.Project_MemVo;

public interface IFile_AttchService {
	
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
	   * Method 설명 : 해당 프로젝트의 File리스트를 받아옴
	   */
	   List<File_AttchVo> fileList(int prj_id);
	   
	   /**
	   * Method : filePagingList
	   * 작성자 : PC13
	   * 변경이력 :
	   * @param pageVo
	   * @return
	   * Method 설명 : filePagination
	   */
	   Map<String, Object> fPagination(Map<String, Object> map);
	   
	   /**
	    * Method : InsertFPagination
	    * 작성자 : PC13
	    * 변경이력 :
	    * @param pageVo
	    * @return
	    * Method 설명 : FPagination
	    */
	   Map<String, Object> insertFPagination(Map<String, Object> map);
	   
	   /**
	   * Method : insertFile
	   * 작성자 : PC13
	   * 변경이력 :
	   * @return
	   * Method 설명 : 파일 등록
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
	   
		//////////////////////////////////////////////////////////////////
		/**
		* Method 		: publicFilePagination
		* 작성자 			: 손영하
		* 변경이력 		: 2019-08-13 최초 생성
		* @param prj_id
		* @return
		* Method 설명 	: 공유함에서의 파일링크 pagination
		*/
	   Map<String, Object> publicFilePagination(Map<String, Object> map);
	   
	   /**
		 * Method 		: publicLinkPagination
		 * 작성자 			: 손영하
		 * 변경이력 		: 2019-08-13 최초 생성
		 * @param map
		 * @return
		 * Method 설명 	: 공유함에서의 링크 pagination
		 */
		Map<String, Object> publicLinkPagination(Map<String, Object> map);
		
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
	   
	   /**
		 * Method 		: individualPagination
		 * 작성자 			: 손영하
		 * 변경이력 		: 2019-08-16 최초 생성
		 * @param pageVo
		 * @return
		 * Method 설명 	: 개인보관함 Pagination
		 */
		Map<String, Object> individualPagination(Map<String, Object> map);

		/**
		 * Method 		: individualCnt
		 * 작성자 			: 손영하
		 * 변경이력 		: 2019-08-16 최초 생성
		 * @return
		 * Method 설명 	: 보관함에서 검색했을 떄 Pagination
		 */
		Map<String, Object> individualSearchPagination(Map<String, Object> map);	
		
		/**
		 * Method 		: selectLV
		 * 작성자 			: 손영하
		 * 변경이력 		: 2019-08-17 최초 생성
		 * @param project_MemVo
		 * @return
		 * Method 설명 	: PM, PL 권한 받아오기!
		 */
		Project_MemVo selectLV(Project_MemVo project_MemVo);
		
		/////////////////////////////////////////////////////////////////////work에서 file link pagination
		/**
		 * Method 		: workFilePagination
		 * 작성자 			: 손영하
		 * 변경이력 		: 2019-08-19 최초 생성
		 * @param map
		 * @return
		 * Method 설명 	: 해당업무에 관한 file pagination
		 */
		Map<String, Object> workFilePagination(Map<String, Object> map);
			
		/**
		 * Method 		: workLinkPagination
		 * 작성자 			: 손영하
		 * 변경이력 		: 2019-08-19 최초 생성
		 * @param map
		 * @return
		 * Method 설명 	: 해당업무에 관한 link pagination
		 */
		Map<String, Object> workLinkPagination(Map<String, Object> map);
		
		///////////////////////////////////////////////////////////////////////workPage
		/**
		* Method 		: insertFilePublic
		* 작성자 			: 손영하
		* 변경이력 		: 2019-08-20 최초 생성
		* @param file_attchVo
		* @return
		* Method 설명 	: work창에서 파일 공용으로 insert
		*/
		int insertFilePublic(File_AttchVo file_attchVo);
		
		/**
		* Method 		: insertFileindividual
		* 작성자 			: 손영하
		* 변경이력 		: 2019-08-20 최초 생성
		* @param file_attchVo
		* @return
		* Method 설명 	: work창에서 파일 개인으로 insert
		*/
		int insertFileindividual(File_AttchVo file_attchVo);
		
		/**
		* Method 		: insertFileboth
		* 작성자 			: 손영하
		* 변경이력 		: 2019-08-20 최초 생성
		* @param file_attchVo
		* @return
		* Method 설명 	: work창에서 파일 둘다 insert
		*/
		int insertFileboth(File_AttchVo file_attchVo);
		
		/**
		 * Method 		: insertLink
		 * 작성자 			: 손영하
		 * 변경이력 		: 2019-08-20 최초 생성
		 * @param link_attchVo
		 * @return
		 * Method 설명 	: work창에서 링크 insert
		 */
		int insertLink(Link_attchVo link_attchVo);
	   
}
