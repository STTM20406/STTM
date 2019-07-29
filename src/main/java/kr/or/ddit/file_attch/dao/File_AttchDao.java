package kr.or.ddit.file_attch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.file_attch.model.File_AttchVo;

@Repository
public class File_AttchDao implements IFile_AttchDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<File_AttchVo> fileList(int prj_id) {
		return sqlSession.selectList("project.fileList",prj_id);
	}

	@Override
	public int updateFile(int file_id) {
		return sqlSession.update("project.updateFile",file_id);
	}

	@Override
	public List<File_AttchVo> fPagination(Map<String, Object> map) {
		return sqlSession.selectList("project.fPagination",map);
	}

	@Override
	public int fileCnt(int prj_id) {
		return sqlSession.selectOne("project.fileCnt",prj_id);
	}

	@Override
	public File_AttchVo getFile(int file_id) {
		return sqlSession.selectOne("project.getFile",file_id);
	}

	@Override
	public int insertFile(File_AttchVo file_attchVo) {
		return sqlSession.insert("project.insertFile",file_attchVo);
	}

	@Override
	public List<File_AttchVo> insertFPagination(Map<String, Object> map) {
		return sqlSession.selectList("project.insertFPagination",map);
	}

	@Override
	public int fCnt(int wrk_id) {
		return sqlSession.selectOne("project.fCnt", wrk_id);
	}

}
