package kr.or.ddit.filter.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.filter.model.FilterVo;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.work.model.WorkVo;

@Repository
public class FilterDao implements IFilterDao{
	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession;

	@Override
	public List<WorkVo> filterList(FilterVo filterVo) {
		return sqlSession.selectList("filter.filterList", filterVo);
	}

	@Override
	public List<ProjectVo> prjIdList(FilterVo filterVo) {
		return sqlSession.selectList("filter.prjIdList", filterVo);
	}

	@Override
	public List<UserVo> makerIdList(FilterVo filterVo) {
		return sqlSession.selectList("filter.makerIdList", filterVo);
	}

	@Override
	public List<UserVo> followerIdList(FilterVo filterVo) {
		return sqlSession.selectList("filter.followerIdList", filterVo);
	}

	@Override
	public WorkVo workDetail(int wrk_id) {
		return sqlSession.selectOne("filter.workDetail", wrk_id);
	}
}
