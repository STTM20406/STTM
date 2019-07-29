package kr.or.ddit.link_attch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.link_attch.dao.ILink_attchDao;
import kr.or.ddit.link_attch.model.Link_attchVo;

@Service
public class Link_attchService implements ILink_attchService{

	@Resource(name = "link_attchDao")
	private ILink_attchDao link_attchDao;
	
	@Override
	public List<Link_attchVo> linkList(int prj_id) {
		return link_attchDao.linkList(prj_id);
	}

	@Override
	public int updateLink(int link_id) {
		return link_attchDao.updateLink(link_id);
	}

	@Override
	public Map<String, Object> lPagination(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("linkList", link_attchDao.lPagination(map));
		
		int linkCnt = link_attchDao.linkCnt((int)map.get("prj_id"));
		
		int paginationSize = (int) Math.ceil((double) linkCnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}

	@Override
	public Map<String, Object> insertLPagination(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("linkList", link_attchDao.insertLPagination(map));
		
		int linkCnt = link_attchDao.lCnt((int)map.get("wrk_id"));
		
		int paginationSize = (int) Math.ceil((double) linkCnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}
	
	@Override
	public int insertLink(Link_attchVo link_attchVo) {
		return link_attchDao.insertLink(link_attchVo);
	}


}
