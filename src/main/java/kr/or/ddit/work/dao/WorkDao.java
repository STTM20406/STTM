package kr.or.ddit.work.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

@Repository
public class WorkDao implements IWorkDao{
	
	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession;
	
	@Override
	public int updateWork(WorkVo workVo) {
		return sqlSession.update("work.updateWork", workVo);
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
		return sqlSession.selectList("work.getWork", wrk_lst_id);
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
		return sqlSession.selectOne("work.getWorkListCnt", workVo);
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
		return sqlSession.insert("work.insertWork", workVo);
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
		return sqlSession.update("work.updateWorkID", workVo);
	}
	
	

}
