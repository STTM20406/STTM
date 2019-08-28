package kr.or.ddit.meeting.dao;

import java.util.List;

import kr.or.ddit.meeting.model.MeetingVo;

public interface IMeetingDao {
	List<MeetingVo> meetingList(int prj_id);
	
	int deleteMeeting(String mt_id);
	
	int insertMeeting(MeetingVo meetingVo);
}
