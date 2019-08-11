package kr.or.ddit.work_list.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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

}
