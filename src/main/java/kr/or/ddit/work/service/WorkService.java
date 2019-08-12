package kr.or.ddit.work.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.work.dao.IWorkDao;
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

@Service
public class WorkService implements IWorkService{
	@Resource(name="workDao")
	IWorkDao workDao;
	
	@Override
	public int updateWork(WorkVo workVo) {
		return workDao.updateWork(workVo);
	}

	
	/**
	 * 
	 * Method 			: getWork
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-10 최초 생성
	 * @param wrk_lst_id
	 * @return
	 * Method 설명 		: 해당 프로젝트의 업무리스트에 포함된 업무 조회
	 */
	@Override
	public List<WorkVo> getWork(int wrk_lst_id) {
		return workDao.getWork(wrk_lst_id);
	}

}
