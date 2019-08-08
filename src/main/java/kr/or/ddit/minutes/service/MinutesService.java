package kr.or.ddit.minutes.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.minutes.dao.IMinutesDao;
import kr.or.ddit.minutes.model.MinutesVo;

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

}
