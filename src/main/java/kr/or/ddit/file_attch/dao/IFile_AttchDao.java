package kr.or.ddit.file_attch.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.file_attch.model.File_AttchVo;

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
	   * Method : updateFile
	   * 작성자 : 손영하
	   * 변경이력 :
	   * @param file_id
	   * @return
	   * Method 설명 : 파일 한개의 정보를 삭제 여부의 상태값을 바꾸는 메서드
	   */
	   int updateFile(int file_id);
	   
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
	   int fileCnt(int prj_id);
	   
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
	   * Method 설명 : 파일 등록
	   */
	   int insertFile(File_AttchVo file_attchVo);
	
}
