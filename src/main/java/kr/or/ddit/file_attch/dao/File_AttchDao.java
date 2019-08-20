package kr.or.ddit.file_attch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.file_attch.model.File_AttchVo;
import kr.or.ddit.link_attch.model.Link_attchVo;
import kr.or.ddit.project_mem.model.Project_MemVo;

@Repository
public class File_AttchDao implements IFile_AttchDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<File_AttchVo> fileList(int prj_id) {
		return sqlSession.selectList("project.fileList",prj_id);
	}

	@Override
	public List<File_AttchVo> fPagination(Map<String, Object> map) {
		return sqlSession.selectList("project.fPagination",map);
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

	/////////////////////////////////////////////////////////////////////////
	@Override
	public List<File_AttchVo> publicFilePagination(Map<String, Object> map) {
		return sqlSession.selectList("project.publicFilePagination",map);
	}
	
	@Override
	public int fileCnt(int prj_id) {
		return sqlSession.selectOne("project.fileCnt",prj_id);
	}

	@Override
	public List<Link_attchVo> publicLinkPagination(Map<String, Object> map) {
		return sqlSession.selectList("project.publicLinkPagination",map);
	}

	@Override
	public int linkCnt(int prj_id) {
		return sqlSession.selectOne("project.linkCnt",prj_id);
	}

	@Override
	public int updateFile(int file_id) {
		return sqlSession.update("project.updateFile",file_id);
	}
	
	@Override
	public int updateLink(int link_id) {
		return sqlSession.update("project.updateLink",link_id);
	}

	@Override
	public int insertFileIN(File_AttchVo file_attchVo) {
		return sqlSession.insert("project.insertFileIN",file_attchVo);
	}

	@Override
	public List<File_AttchVo> individualPagination(Map<String, Object> map) {
		return sqlSession.selectList("project.individualPagination",map);
	}

	@Override
	public int individualCnt(String user_email) {
		return sqlSession.selectOne("project.individualCnt",user_email);
	}

	@Override
	public List<File_AttchVo> individualSearchPagination(Map<String, Object> map) {
		return sqlSession.selectList("project.individualSearchPagination",map);
	}

	@Override
	public int searchIndividualCnt(String original_file_nm) {
		return sqlSession.selectOne("project.searchIndividualCnt",original_file_nm);
	}

	@Override
	public Project_MemVo selectLV(Project_MemVo project_MemVo) {
		return sqlSession.selectOne("project.selectLV",project_MemVo);
	}

	//work 업무 file, link pagination
	
	@Override
	public List<File_AttchVo> workFilePagination(Map<String, Object> map) {
		return sqlSession.selectList("project.workFilePagination",map);
	}

	@Override
	public List<Link_attchVo> workLinkPagination(Map<String, Object> map) {
		return sqlSession.selectList("project.workLinkPagination",map);
	}

	@Override
	public int workFileCnt(int wrk_id) {
		return sqlSession.selectOne("project.workFileCnt",wrk_id);
	}

	@Override
	public int workLinkCnt(int wrk_id) {
		return sqlSession.selectOne("project.workLinkCnt",wrk_id);
	}


}
