package kr.or.ddit.minutes.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.minutes.model.MinutesVo;

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

}
