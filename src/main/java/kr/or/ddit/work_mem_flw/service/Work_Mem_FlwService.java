package kr.or.ddit.work_mem_flw.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.work_mem_flw.dao.IWork_Mem_FlwDao;
import kr.or.ddit.work_mem_flw.model.Work_Mem_FlwVo;

@Service
public class Work_Mem_FlwService implements IWork_Mem_FlwService{
	
	@Resource(name="work_Mem_FlwDao")
	IWork_Mem_FlwDao workMemFlwDao;
	
	/**
	 * 
	 * Method 			: insertWorkMemFlw
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-21 최초 생성
	 * @param work_mem_flwVo
	 * @return
	 * Method 설명 		: 해당 업무에 배정된 멤버 추가
	 */
	@Override
	public int insertWorkMemFlw(Work_Mem_FlwVo work_mem_flwVo) {
		return workMemFlwDao.insertWorkMemFlw(work_mem_flwVo);
	}

	
	/**
	 * 
	 * Method 			: workMemFlwList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-21 최초 생성
	 * @param work_mem_flwVo
	 * @return
	 * Method 설명 		: 업무에 해당하는 멤버 / 팔로워 조회
	 */
	@Override
	public List<Work_Mem_FlwVo> workMemFlwList(Work_Mem_FlwVo work_mem_flwVo) {
		return workMemFlwDao.workMemFlwList(work_mem_flwVo);
	}


	/**
	 * 
	 * Method 			: deleteWorkMemFlw
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-21 최초 생성
	 * @param work_mem_flwVo
	 * @return
	 * Method 설명 		: 업무에 해당하는 멤버 / 팔로워 삭제
	 */
	@Override
	public int deleteWorkMemFlw(Work_Mem_FlwVo work_mem_flwVo) {
		return workMemFlwDao.deleteWorkMemFlw(work_mem_flwVo);
	}


	/**
	 * 
	 * Method 			: getWorkMemFlw
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-22 최초 생성
	 * @param work_mem_flwVo
	 * @return
	 * Method 설명 		: 업무 팔로워 멤버가 있는지 조회
	 */
	@Override
	public Work_Mem_FlwVo getWorkMemFlw(Work_Mem_FlwVo work_mem_flwVo) {
		return workMemFlwDao.getWorkMemFlw(work_mem_flwVo);
	}


	/**
	 * 
	 * Method 			: updateWorkMemFlw
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-22 최초 생성
	 * @param work_mem_flwVo
	 * @return
	 * Method 설명 		: 업무 멤버 / 팔로워 JN_FL 업데이트 (멤버인지 팔로워 인지)
	 */
	@Override
	public int updateWorkMemFlw(Work_Mem_FlwVo work_mem_flwVo) {
		return workMemFlwDao.updateWorkMemFlw(work_mem_flwVo);
	}

	/**
	 * 
	 * Method 			: workAllMemFlwList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-23 최초 생성
	 * @param work_mem_flwVo
	 * @return
	 * Method 설명 		: 업무 멤버, 팔로워 조건없이 모두 조회
	 */
	@Override
	public List<Work_Mem_FlwVo> workAllMemFlwList(int wrk_id) {
		return workMemFlwDao.workAllMemFlwList(wrk_id);
	}
}
