package kr.or.ddit.link_attch.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.link_attch.model.Link_attchVo;


public interface ILink_attchDao {

	 /**
	   * Method : linkList
	   * 작성자 : 손영하
	   * 변경이력 :
	   * @param prj_id
	   * @return
	   * Method 설명 : 해당 프로젝트의 link리스트를 받아옴
	   */
	   List<Link_attchVo> linkList(int prj_id);
	   
	   /**
	   * Method : updateLink
	   * 작성자 : 손영하
	   * 변경이력 :
	   * @param file_id
	   * @return
	   * Method 설명 : 링크 한개의 정보를 삭제 여부의 상태값을 바꾸는 메서드
	   */
	   int updateLink(int link_id);
	   
	   /**
	   * Method : linkPagingList
	   * 작성자 : PC13
	   * 변경이력 :
	   * @param pageVo
	   * @return
	   * Method 설명 : linkPagingList
	   */
	   List<Link_attchVo> lPagination(Map<String, Object> map);
	   
	   /**
	    * Method : linkPagingList
	    * 작성자 : PC13
	    * 변경이력 :
	    * @param pageVo
	    * @return
	    * Method 설명 : linkPagingList
	    */
	   List<Link_attchVo> insertLPagination(Map<String, Object> map);
	   
	   /**
	   * Method : linkCnt
	   * 작성자 : PC13
	   * 변경이력 :
	   * @return
	   * Method 설명 : 해당 프로젝트의 전체 링크수 조회
	   */
	   int linkCnt(int prj_id);
	   
	   /**
	    * Method : linkCnt
	    * 작성자 : PC13
	    * 변경이력 :
	    * @return
	    * Method 설명 : 해당 프로젝트의 전체 링크수 조회
	    */
	   int lCnt(int wrk_id);
	   
	   /**
	   * Method : insertLink
	   * 작성자 : PC13
	   * 변경이력 :
	   * @return
	   * Method 설명 : insertLink 링크 추가
	   */
	   int insertLink(Link_attchVo link_attchVo);
	
}
