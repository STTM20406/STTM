package kr.or.ddit.minutes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.minutes.dao.IMinutesDao;
import kr.or.ddit.minutes.model.MinutesVo;
import kr.or.ddit.minutes.model.Minutes_MemVo;
import kr.or.ddit.users.model.UserVo;

@Service
public class MinutesService implements IMinutesService{
	
	private static final Logger logger = LoggerFactory.getLogger(MinutesService.class);
	
	@Resource(name = "minutesDao")
	private IMinutesDao minutesDao;
	
	/**
	 * Method 		: minutesList
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 	: 해당 프로젝트에 프로젝트 리스트를 받아오는 메서드
	 */
	@Override
	public List<MinutesVo> minutesList(int prj_id) {
		return minutesDao.minutesList(prj_id);
	}

	/**
	 * Method 		: minutesPagination
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 	: minutes Pagination
	 */
	@Override
	public Map<String, Object> minutesPagination(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("minutesList", minutesDao.minutesPagination(map));
		int cnt = minutesDao.minutesCnt((int)map.get("prj_id"));
		int paginationSize = (int) Math.ceil((double) cnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}
	/**
	 * Method : searchPagination
	 * 작성자 : melong2
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 검색 후 페이지 네이션 아직 미완료
	 */
	@Override
	public Map<String, Object> searchPagination(Map<String, Object> map) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("searchList", minutesDao.searchPagination(map));
		logger.debug("♬♩♪ pagination logger");
		
		int cnt = minutesDao.searchCnt((String)map.get("user_nm"));
		
		int paginationSize = (int)Math.ceil((double) cnt/ (int) map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}
	
	/**
	 * Method 		: minutesDetail
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-08 최초 생성
	 * @param mnu_id
	 * @return
	 * Method 설명 	: minutes 상세정보 받아오는 메서드
	 */
	@Override
	public MinutesVo minutesDetail(int mnu_id) {
		return minutesDao.minutesDetail(mnu_id);
	}


	/**
	* Method : attenderList
	* 작성자 : melong2
	* 변경이력 :
	* @param mnu_id
	* @return
	* Method 설명 : 회의 참석자 리스트를 받아오는 메서드
	*/
	@Override
	public List<Minutes_MemVo> attenderList(int mnu_id) {
		return minutesDao.attenderList(mnu_id);
	}

	@Override
	public int upMinutes(int mnu_id) {
		return minutesDao.upMinutes(mnu_id);
	}

	@Override
	public List<UserVo> memberList(String user_email) {
		return minutesDao.memberList(user_email);
	}

	@Override
	public MinutesVo recentMinutes() {
		return minutesDao.recentMinutes();
	}

	@Override
	public int insertAttender(Minutes_MemVo memVo) {
		return minutesDao.insertAttender(memVo);
	}

	@Override
	public UserVo whoAreYou(String user_email) {
		return minutesDao.whoAreYou(user_email);
	}

	@Override
	public int insertMinutes(MinutesVo minutesVo) {
		return minutesDao.insertMinutes(minutesVo);
	}

	@Override
	public int updateMinutes(MinutesVo minutesVo) {
		return minutesDao.updateMinutes(minutesVo);
	}

	@Override
	public int searchCnt(String name_nm) {
		// TODO Auto-generated method stub
		return 0;
	}


}
