package kr.or.ddit.minutes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.minutes.dao.IMinutesDao;
import kr.or.ddit.minutes.model.MinutesVo;
import kr.or.ddit.minutes.model.Minutes_MemVo;

@Service
public class MinutesService implements IMinutesService{

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
	* Method : searchPagination
	* 작성자 : melong2
	* 변경이력 :
	* @param map
	* @return
	* Method 설명 : 검색 후 페이지 네이션 아직 미완료
	*/
	@Override
	public Map<String, Object> searchPagination(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
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

}
