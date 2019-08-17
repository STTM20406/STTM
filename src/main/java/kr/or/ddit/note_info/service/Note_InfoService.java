package kr.or.ddit.note_info.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import kr.or.ddit.note_content.model.Note_ContentVo;
import kr.or.ddit.note_info.dao.INote_InfoDao;
import kr.or.ddit.note_info.model.Note_InfoVo;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.users.model.UserVo;

@Service
public class Note_InfoService implements INote_InfoService{
	
	@Resource(name="note_InfoDao")
	private INote_InfoDao noteDao;
	

	@Override
	public Map<String, Object> rcvList(PageVo pageVo) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rcvList",noteDao.rcvList(pageVo));
		int rcvCnt = noteDao.rcvCnt(pageVo.getRcv_email());
		int paginationSize = (int) Math.ceil((double)rcvCnt/pageVo.getPageSize());
		resultMap.put("rcvPaginationSize", paginationSize);
		
		return resultMap;
	}

	@Override
	public int rcvCnt(String user_email) {
		return noteDao.rcvCnt(user_email);
	}

	@Override
	public Map<String, Object> sendList(PageVo pageVo) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sendList",noteDao.sendList(pageVo));
		
		int sendCnt = noteDao.sendCnt(pageVo.getSend_email());
		int paginationSize = (int) Math.ceil((double)sendCnt/pageVo.getPageSize());
		resultMap.put("sendPaginationSize", paginationSize);
		
		return resultMap;
	}

	@Override
	public int sendCnt(String user_email) {
		return noteDao.sendCnt(user_email);
	}

	@Override
	public int insertNoteContent(Note_ContentVo conVo) {
		return noteDao.insertNoteContent(conVo);
	}

	@Override
	public int insertNoteInfo(Note_InfoVo noteInfo) {
		return noteDao.insertNoteInfo(noteInfo);
	}

	@Override
	public int rcvDel(int note_con_id) {
		return noteDao.rcvDel(note_con_id);
	}

}
