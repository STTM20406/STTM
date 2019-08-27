package kr.or.ddit.meeting.service;

import kr.or.ddit.meeting.model.MeetingVo;

public interface IMeetingService {
	
	String meetingList(int prj_id);
	
	int deleteMeeting(String mt_id);
	
	int insertMeeting(MeetingVo meetingVo);
}
