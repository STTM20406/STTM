package kr.or.ddit.project_mem.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.project_mem.dao.IProject_MemDao;
import kr.or.ddit.project_mem.model.Project_MemVo;

@Service
public class Project_MemService implements IProject_MemService{
	
	@Resource(name = "project_MemDao")
	private IProject_MemDao projectMemDao;
	
	@Override
	public List<Project_MemVo> projectMemList() {
		return null;
	}

	/**
	 * 
	 * Method 		: insertProjectMem
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @return
	 * Method 설명 	: 프로젝트 멤버 등록(초대)
	 */
	@Override
	public int insertProjectMem(Project_MemVo projectMemVo) {
		return projectMemDao.insertProjectMem(projectMemVo);
	}

	@Override
	public int updatePjojectMem(Project_MemVo projectMemVo) {
		return 0;
	}
	
}
