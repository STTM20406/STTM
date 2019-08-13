package kr.or.ddit.file_dw_his.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.file_dw_his.model.File_Dw_HisVo;

@Repository
public class File_Dw_HisDao implements IFile_Dw_HisDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<File_Dw_HisVo> historyPagination(Map<String, Object> map) {
		return sqlSession.selectList("project.historyPagination",map);
	}

	@Override
	public int historyCnt(int prj_id) {
		return sqlSession.selectOne("project.historyCnt",prj_id);
	}

}
