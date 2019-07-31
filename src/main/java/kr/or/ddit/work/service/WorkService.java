package kr.or.ddit.work.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.work.dao.IWorkDao;
import kr.or.ddit.work.model.WorkVo;

@Service
public class WorkService implements IWorkService{
	@Resource(name="workDao")
	IWorkDao workDao;
	
	@Override
	public int updateWork(WorkVo workVo) {
		return workDao.updateWork(workVo);
	}

}
