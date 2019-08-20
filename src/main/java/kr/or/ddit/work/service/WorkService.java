package kr.or.ddit.work.service;

import java.util.List;

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


	/**
	 * 
	 * Method 			: getWorkListCnt
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-15 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 해당 업무리스트의 완료된 업무와 진행중인 업무 카운트 조회
	 */
	@Override
	public WorkVo getWorkListCnt(WorkVo workVo) {
		return workDao.getWorkListCnt(workVo);
	}


	/**
	 * 
	 * Method 			: insertWork
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-15 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 해당 업무리스트에 업무 생성
	 */
	@Override
	public int insertWork(WorkVo workVo) {
		return workDao.insertWork(workVo);
	}


	/**
	 * 
	 * Method 			: updateWorkID
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-16 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 업무리스트 업무를 다른 업무리스트로 이동 시켰을 때 업무리스트 아이디 업데이트
	 */
	@Override
	public int updateWorkID(WorkVo workVo) {
		return workDao.updateWorkID(workVo);
	}

	
	/**
	 * 
	 * Method 			: getWorkInfo
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-17 최초 생성
	 * @param wrk_id
	 * @return
	 * Method 설명 		: 업무 아이디로 업무 정보 조회
	 */
	@Override
	public WorkVo getWorkInfo(int wrk_id) {
		return workDao.getWorkInfo(wrk_id);
	}


	/**
	 * 
	 * Method 			: updateWorkCmp
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-18 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 업무 아이디로 업무 완료 여부 업데이트
	 */
	@Override
	public int updateWorkCmp(WorkVo workVo) {
		return workDao.updateWorkCmp(workVo);
	}


	/**
	 * 
	 * Method 			: updateAllWork
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-19 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 		: 해당 업무 정보 전체 업데이트
	 */
	@Override
	public int updateAllWork(WorkVo workVo) {
		return workDao.updateAllWork(workVo);
	}

}
