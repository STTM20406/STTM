package kr.or.ddit.link_attch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.link_attch.model.Link_attchVo;

@Repository
public class Link_attchDao implements ILink_attchDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<Link_attchVo> linkList(int prj_id) {
		return sqlSession.selectList("project.linkList",prj_id);
	}

	@Override
	public int updateLink(int link_id) {	
		return sqlSession.update("project.updateLink",link_id);
	}

	@Override
	public List<Link_attchVo> lPagination(Map<String, Object> map) {
		return sqlSession.selectList("project.lPagination",map);
	}

	@Override
	public int linkCnt(int prj_id) {
		return sqlSession.selectOne("project.linkCnt",prj_id);
	}

	@Override
	public int insertLink(Link_attchVo link_attchVo) {
		return sqlSession.insert("project.insertLink",link_attchVo);
	}

	@Override
	public List<Link_attchVo> insertLPagination(Map<String, Object> map) {
		return sqlSession.selectList("project.insertLPagination",map);
	}

	@Override
	public int lCnt(int wrk_id) {
		return sqlSession.selectOne("project.lCnt",wrk_id);
	}

}
