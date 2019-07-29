package kr.or.ddit.file_attch.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.file_attch.model.File_AttchVo;

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
	   * Method : DelFile
	   * 작성자 : 손영하
	   * 변경이력 :
	   * @param file_id
	   * @return
	   * Method 설명 : 파일 한개의 정보를 삭제 여부의 상태값을 바꾸는 메서드
	   */
	   int updateFile(int file_id);
	   
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
}
