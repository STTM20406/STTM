package kr.or.ddit.work_mem_flw.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.work_mem_flw.model.Work_Mem_FlwVo;

@Repository
public class Work_Mem_FlwDao implements IWork_Mem_FlwDao{
	
	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession;
	
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
		return sqlSession.insert("work.insertWorkMemFlw", work_mem_flwVo);
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
		return sqlSession.selectList("work.workMemFlwList", work_mem_flwVo);
	}
}
