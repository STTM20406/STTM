package kr.or.ddit.file_attch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.file_attch.dao.IFile_AttchDao;
import kr.or.ddit.file_attch.model.File_AttchVo;

@Service
public class File_AttchService implements IFile_AttchService{

	@Resource(name = "file_AttchDao")
	private IFile_AttchDao file_AttchDao;

	@Override
	public List<File_AttchVo> fileList(int prj_id) {
		return file_AttchDao.fileList(prj_id);
	}

	@Override
	public int updateFile(int file_id) {
		return file_AttchDao.updateFile(file_id);
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

	@Override
	public Map<String, Object> publicFilePagination(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}


}
