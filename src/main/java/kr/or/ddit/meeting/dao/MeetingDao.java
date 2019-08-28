package kr.or.ddit.meeting.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.meeting.model.MeetingVo;

@Repository
public class MeetingDao implements IMeetingDao{
	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession;

	@Override
	public List<MeetingVo> meetingList(int prj_id) {
		return sqlSession.selectList("project.meetingList", prj_id);
	}

	@Override
	public int deleteMeeting(String mt_id) {
		return sqlSession.delete("project.deleteMeeting", mt_id);
	}

	@Override
	public int insertMeeting(MeetingVo meetingVo) {
		return sqlSession.insert("project.insertMeeting", meetingVo);
	}
}
