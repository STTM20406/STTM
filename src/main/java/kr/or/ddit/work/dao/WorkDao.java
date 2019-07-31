package kr.or.ddit.work.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.work.model.WorkVo;

@Repository
public class WorkDao implements IWorkDao{
	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession;
	
	@Override
	public int updateWork(WorkVo workVo) {
		return sqlSession.update("work.updateWork", workVo);
	}

}
