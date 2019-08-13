package kr.or.ddit.file_dw_his.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.file_dw_his.dao.IFile_Dw_HisDao;

@Service 
public class File_Dw_HisService implements IFile_Dw_HisService{

	private static final Logger logger = LoggerFactory.getLogger(File_Dw_HisService.class);
	
	@Resource(name = "file_Dw_HisDao")
	private IFile_Dw_HisDao file_Dw_HisDao;

	@Override
	public Map<String, Object> historyPagination(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("historyList", file_Dw_HisDao.historyPagination(map));
		
		logger.debug("♬♩♪  여기는 service: {}", resultMap.put("historyList", file_Dw_HisDao.historyPagination(map)));
		
		int historyCnt = file_Dw_HisDao.historyCnt((int)map.get("prj_id"));
		
		int paginationSize = (int) Math.ceil((double) historyCnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}
	
}
