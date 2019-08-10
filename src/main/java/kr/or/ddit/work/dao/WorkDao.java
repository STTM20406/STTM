package kr.or.ddit.work.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

@Repository
public class WorkDao implements IWorkDao{
	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession;
	
	private static final Logger logger = LoggerFactory.getLogger(WorkDao.class);
	
	@Override
	public int updateWork(WorkVo workVo) {
		return sqlSession.update("work.updateWork", workVo);
	}

	/**
	 * 
	 * Method 			: getWork
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-10 최초 생성
	 * @param wrk_lst_id
	 * @return
	 * Method 설명 		: 해당 프로젝트의 업무리스트에 포함된 업무 조회
	 */
	@Override
	public List<WorkVo> getWork(int wrk_lst_id) {
		logger.debug("wrk_lst_id :::::::::::dao {}", wrk_lst_id);
		
		return sqlSession.selectList("work.getWork", wrk_lst_id);
	}

}
