package kr.or.ddit.minutes.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.minutes.model.MinutesVo;
import kr.or.ddit.minutes.model.Minutes_MemVo;

@Repository
public class MinutesDao implements IMinutesDao{

	@Resource(name = "sqlSession")
	private SqlSessionTemplate sqlSession;
	
	/**
	 * Method 		: minutesList
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 	: 해당 프로젝트에 프로젝트 리스트를 받아오는 메서드
	 */
	@Override
	public List<MinutesVo> minutesList(int prj_id) {
		return sqlSession.selectList("project.minutesList",prj_id);
	}
	
	/**
	 * Method 		: minutesPagination
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 	: minutes Pagination
	 */
	@Override
	public List<MinutesVo> minutesPagination(Map<String, Object> map) {
		return sqlSession.selectList("project.minutesPagination",map);
	}

	/**
	 * Method 		: minutesCnt
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 	: 회의록 게시판 갯수를가져오는
	 */
	@Override
	public int minutesCnt(int prj_id) {
		return sqlSession.selectOne("project.minutesCnt",prj_id);
	}

	/**
	 * Method 		: minutesDetail
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param mnu_id
	 * @return
	 * Method 설명 	: minutes 상세정보 받아오는 메서드
	 */
	@Override
	public MinutesVo minutesDetail(int mnu_id) {
		return sqlSession.selectOne("project.minutesDetail",mnu_id);
	}

	/**
	* Method : searchPagination
	* 작성자 : melong2
	* 변경이력 :
	* @param map
	* @return
	* Method 설명 : 검색 한 결과 Pagination
	*/
	@Override
	public List<MinutesVo> searchPagination(Map<String, Object> map) {
		return sqlSession.selectOne("project.searchPagination",map);
	}

	@Override
	public List<Minutes_MemVo> attenderList(int mnu_id) {
		return sqlSession.selectList("project.attenderList",mnu_id);
	}

	@Override
	public int upMinutes(int mnu_id) {
		return sqlSession.update("project.upMinutes",mnu_id);
	}

}
