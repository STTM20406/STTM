package kr.or.ddit.work_list.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.work_list.dao.IWork_ListDao;
import kr.or.ddit.work_list.model.Work_ListVo;

@Service
public class Work_ListService implements IWork_ListService{
	
	@Resource(name = "work_ListDao")
	private IWork_ListDao workListDao;

	/**
	 * 
	 * Method 			: workList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-09 최초 생성
	 * @param prj_id
	 * @return
	 * Method 설명 		: 해당 프로젝트의 업무 리스트 및 업무 조회
	 */
	@Override
	public List<Work_ListVo> workList(int prj_id) {
		return workListDao.workList(prj_id);
	}

}
