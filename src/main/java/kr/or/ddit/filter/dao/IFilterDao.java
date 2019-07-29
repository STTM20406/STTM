package kr.or.ddit.filter.dao;

import java.util.List;

import kr.or.ddit.filter.model.FilterVo;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.work.model.WorkVo;

public interface IFilterDao {
	/**
	 * Method : filterList
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-23 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 검색 조건과 일치하는 업무 리스트 반환 메서드
	 */
	List<WorkVo> filterList(FilterVo filterVo);
	
	/**
	 * Method : prjIdList
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-23 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 사용자가 참가중인 프로젝트 아이디 / 이름 리스트 반환 메서드 
	 */
	List<ProjectVo> prjIdList(FilterVo filterVo);
	
	/**
	 * Method : makerIdList
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-23 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 사용자가 참가중인 프로젝트의 업무 생성자 아이디 / 이름 리스트 반환 메서드
	 */
	List<UserVo> makerIdList(FilterVo filterVo);
	
	/**
	 * Method : followerIdList
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-23 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 사용자가 참가중인 프로젝트의 업무 팔로워 아이디 / 이름 리스트 반환 메서드
	 */
	List<UserVo> followerIdList(FilterVo filterVo);
	
	/**
	 * Method : workDetail
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-24 최초 생성
	 * @param wrk_id
	 * @return
	 * Method 설명 : 임시 생성한 특정 업무 상세정보 조회 메서드
	 */
	WorkVo workDetail(int wrk_id);
}
