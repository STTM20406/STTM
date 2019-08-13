package kr.or.ddit.work_list.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.work_list.model.Work_ListVo;

@Repository
public class Work_ListDao implements IWork_ListDao{
	
	@Resource(name = "sqlSession")
	private SqlSessionTemplate sqlSession;

	/**
	 * 
	 * Method 			: workList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-09 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 		: 해당 프로젝트의 업무 리스트 및 업무 조회
	 */
	@Override
	public List<Work_ListVo> workList(int prj_id) {
		return sqlSession.selectList("work.workList", prj_id);
	}

	/**
	 * 
	 * Method 			: insertWorkList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-11 최초 생성
	 * @param workListVo
	 * @return
	 * Method 설명 		: 해당 프로젝트에 업무리스트 생성
	 */
	@Override
	public int insertWorkList(Work_ListVo workListVo) {
		return sqlSession.insert("work.insertWorkList", workListVo);
	}

	/**
	 * 
	 * Method 			: updateWorkList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-12 최초 생성
	 * @param wrk_lst_id
	 * @return
	 * Method 설명 		: 해당 프로젝트 업무리스트 이름 수정
	 */
	@Override
	public int updateWorkList(int wrk_lst_id) {
		return sqlSession.update("work.updateWorkList", wrk_lst_id);
	}

	/**
	 * 
	 * Method 			: deleteWorkList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-12 최초 생성
	 * @param wrk_lst_id
	 * @return
	 * Method 설명 		: 해당 프로젝트 업무리스트 삭제 (플래그 업데이트)
	 */
	@Override
	public int deleteWorkList(int wrk_lst_id) {
		return sqlSession.delete("work.deleteWorkList", wrk_lst_id);
	}

	/**
	 * 
	* Method : workListPagingList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-13
	* @param pageVo
	* @return
	* Method 설명 : 타이머 - 프로젝트에 세션정보를 받아와 해당 프로젝트의 업무리스트 조회
	 */
	@Override
	public List<Work_ListVo> timerWorkListPagingList(PageVo pageVo) {
		return sqlSession.selectList("work.timerWorkListPagingList", pageVo);
	}
	
	/**
	 * 
	* Method : workListCnt
	* 작성자 : 김경호
	* 변경이력 : 2019-08-13
	* @return
	* Method 설명 : 업무 리스트 전체수 조회
	 */
	@Override
	public int timerWorkListCnt() {
		return sqlSession.selectOne("work.timerWorkListCnt");
	}

}
