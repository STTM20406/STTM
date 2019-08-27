package kr.or.ddit.meeting.service;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.meeting.dao.IMeetingDao;
import kr.or.ddit.meeting.model.MeetingVo;

@Service
public class MeetingService implements IMeetingService{
	
	@Resource(name="meetingDao")
	IMeetingDao meetingDao;
	
	@Override
	public String meetingList() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		List<MeetingVo> meetingList = meetingDao.meetingList();
		String htmlCode = "";
		for(MeetingVo meet : meetingList) {
			htmlCode += "<div class='meetingItem'>"
					+ "<b>"
					+ meet.getMt_exp()
					+ "</b>"
					+ "<br>"
					+ "<span>"
					+ meet.getMt_lc() + "&nbsp;" + meet.getUser_email()
					+ "</span>"
					+ "<br>"
					+ "<span>"
					+ sdf.format(meet.getMt_date())
					+ "<input type='hidden' id='mt_id' value='" + meet.getMt_id() + "'/>"
					+ "<input type='hidden' id='lat' value='" + meet.getMt_lat() + "'/>"
					+ "<input type='hidden' id='lng' value='" + meet.getMt_lng() + "'/>"
					+ "</div>";
		}
		return htmlCode;
	}
	
	@Override
	public int deleteMeeting(String mt_id) {
		return meetingDao.deleteMeeting(mt_id);
	}

	@Override
	public int insertMeeting(MeetingVo meetingVo) {
		return meetingDao.insertMeeting(meetingVo);
	}

}
