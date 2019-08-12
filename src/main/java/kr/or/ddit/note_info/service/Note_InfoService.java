package kr.or.ddit.note_info.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.note_info.dao.INote_InfoDao;
import kr.or.ddit.note_info.model.Note_InfoVo;
import kr.or.ddit.paging.model.PageVo;

@Service
public class Note_InfoService implements INote_InfoService{
	
	@Resource(name="note_InfoDao")
	private INote_InfoDao noteDao;

	@Override
	public Map<String, Object> rcvList(PageVo pageVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rcvList",noteDao.rcvList(pageVo));
		
		int rcvCnt = noteDao.rcvCnt();
		int paginationSize = (int) Math.ceil((double)rcvCnt/pageVo.getPageSize());
		resultMap.put("rcvPaginationSize", paginationSize);
		
		return resultMap;
	}

	@Override
	public int rcvCnt() {
		return noteDao.rcvCnt();
	}

	@Override
	public Map<String, Object> sendList(PageVo pageVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sendList",noteDao.sendList(pageVo));
		
		int sendCnt = noteDao.sendCnt();
		int paginationSize = (int) Math.ceil((double)sendCnt/pageVo.getPageSize());
		resultMap.put("sendPaginationSize", paginationSize);
		
		return resultMap;
	}

	@Override
	public int sendCnt() {
		return noteDao.sendCnt();
	}

	@Override
	public int insertNoteContent(String content) {
		return noteDao.insertNoteContent(content);
	}

	@Override
	public int insertNoteInfo(Note_InfoVo noteInfo) {
		return noteDao.insertNoteInfo(noteInfo);
	}

}
