package kr.or.ddit.file_attch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.file_attch.dao.IFile_AttchDao;
import kr.or.ddit.file_attch.model.File_AttchVo;
import kr.or.ddit.link_attch.model.Link_attchVo;
import kr.or.ddit.project_mem.model.Project_MemVo;

@Service
public class File_AttchService implements IFile_AttchService{
	
	private static final Logger logger = LoggerFactory.getLogger(File_AttchService.class);

	@Resource(name = "file_AttchDao")
	private IFile_AttchDao file_AttchDao;

	@Override
	public List<File_AttchVo> fileList(int prj_id) {
		return file_AttchDao.fileList(prj_id);
	}

	@Override
	public Map<String, Object> fPagination(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("fileList", file_AttchDao.fPagination(map));
		
		int fileCnt = file_AttchDao.fileCnt((int)map.get("prj_id"));
		
		int paginationSize = (int) Math.ceil((double) fileCnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}

	@Override
	public Map<String, Object> insertFPagination(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("fileList", file_AttchDao.insertFPagination(map));
		
		int fileCnt = file_AttchDao.fCnt((int)map.get("wrk_id"));
		
		int paginationSize = (int) Math.ceil((double) fileCnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}
	
	@Override
	public File_AttchVo getFile(int file_id) {
		return file_AttchDao.getFile(file_id);
	}

	@Override
	public int insertFile(File_AttchVo file_attchVo) {
		return file_AttchDao.insertFile(file_attchVo);
	}

	/////////////////////////////////////////////////////////////////////////////////
	@Override
	public Map<String, Object> publicFilePagination(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("publicFileList", file_AttchDao.publicFilePagination(map));
		
		int fCnt = file_AttchDao.fileCnt((int)map.get("prj_id"));
		logger.debug("♬♩♪  fCnt: {}", fCnt);
		int paginationSize = (int) Math.ceil((double) fCnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}

	@Override
	public Map<String, Object> publicLinkPagination(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("publicLinkList", file_AttchDao.publicLinkPagination(map));
		
		int lCnt = file_AttchDao.linkCnt((int)map.get("prj_id"));
		logger.debug("♬♩♪  lCnt: {}", lCnt);
		int paginationSize = (int) Math.ceil((double) lCnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> individualPagination(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("individualList", file_AttchDao.individualPagination(map));
		
		int Cnt = file_AttchDao.individualCnt((String)map.get("user_email"));
		logger.debug("♬♩♪  individualPagination  CNT: {}", Cnt);
		
		int paginationSize = (int)Math.ceil((double)Cnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}

	@Override
	public int updateFile(int file_id) {
		return file_AttchDao.updateFile(file_id);
	}
	
	@Override
	public int updateLink(int link_id) {
		return file_AttchDao.updateLink(link_id);
	}

	@Override
	public int insertFileIN(File_AttchVo file_attchVo) {
		return file_AttchDao.insertFile(file_attchVo);
	}

	@Override
	public Map<String, Object> individualSearchPagination(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("searchIndividualList", file_AttchDao.individualSearchPagination(map));
		
		int cnt = file_AttchDao.searchIndividualCnt((String)map.get("original_file_nm"));
		logger.debug("♬♩♪   individualSearchPagination cnt: {}", cnt);
		int paginationSize = (int)Math.ceil((double) cnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}

	@Override
	public Project_MemVo selectLV(Project_MemVo project_MemVo) {
		return file_AttchDao.selectLV(project_MemVo);
	}

	@Override
	public Map<String, Object> workFilePagination(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("workFileList", file_AttchDao.workFilePagination(map));
		
		int cnt = file_AttchDao.workFileCnt((int)map.get("wrk_id"));
		
		logger.debug("♬♩♪  workFilePagination cnt: {}", cnt);
		int paginationSize = (int)Math.ceil((double) cnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}

	@Override
	public Map<String, Object> workLinkPagination(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("workLinkList", file_AttchDao.workLinkPagination(map));
		
		int cnt = file_AttchDao.workLinkCnt((int)map.get("wrk_id"));
		int paginationSize = (int)Math.ceil((double) cnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}

	@Override
	public int insertFilePublic(File_AttchVo file_attchVo) {
		return file_AttchDao.insertFilePublic(file_attchVo);
	}

	@Override
	public int insertFileindividual(File_AttchVo file_attchVo) {
		return file_AttchDao.insertFileindividual(file_attchVo);
	}

	@Override
	public int insertFileboth(File_AttchVo file_attchVo) {
		return file_AttchDao.insertFileboth(file_attchVo);
	}

	@Override
	public int insertLink(Link_attchVo link_attchVo) {
		return file_AttchDao.insertLink(link_attchVo);
	}




}
