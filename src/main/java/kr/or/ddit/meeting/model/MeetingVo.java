package kr.or.ddit.meeting.model;

import java.util.Date;

/**
 * 
 * MeetingVo.java
 *
 * @author 박서경
 * @version 1.0
 * @see
 * MEETING - 미팅
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정자  수정내용
 * ------ ------------------------
 *  박서경  최초 생성 2019-07-19
 *
 * </pre>
 */
public class MeetingVo {
	
	private int mt_id;				//미팅 ID				
	private String user_email;      	//이메일
	private int prj_id;             		//프로젝트 ID
	private String mt_lc;           		//미팅 장소
	private String mt_exp;         	 	//미팅 설명
	private Date mt_date;          	 	//미팅 일시
	private String mt_lat;          		//위치 위도
	private String mt_lng;          		//위치 경도
	
	public MeetingVo() {}
	
	public MeetingVo(String user_email, int prj_id, String mt_lc, String mt_exp, Date mt_date, String mt_lat,
			String mt_lng) {
		super();
		this.user_email = user_email;
		this.prj_id = prj_id;
		this.mt_lc = mt_lc;
		this.mt_exp = mt_exp;
		this.mt_date = mt_date;
		this.mt_lat = mt_lat;
		this.mt_lng = mt_lng;
	}

	public MeetingVo(int mt_id, String user_email, int prj_id, String mt_lc, String mt_exp, Date mt_date, String mt_lat,
			String mt_lng) {
		super();
		this.mt_id = mt_id;
		this.user_email = user_email;
		this.prj_id = prj_id;
		this.mt_lc = mt_lc;
		this.mt_exp = mt_exp;
		this.mt_date = mt_date;
		this.mt_lat = mt_lat;
		this.mt_lng = mt_lng;
	}
	
	
	
	public int getMt_id() {
		return mt_id;
	}

	public void setMt_id(int mt_id) {
		this.mt_id = mt_id;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public int getPrj_id() {
		return prj_id;
	}
	public void setPrj_id(int prj_id) {
		this.prj_id = prj_id;
	}
	public String getMt_lc() {
		return mt_lc;
	}
	public void setMt_lc(String mt_lc) {
		this.mt_lc = mt_lc;
	}
	public String getMt_exp() {
		return mt_exp;
	}
	public void setMt_exp(String mt_exp) {
		this.mt_exp = mt_exp;
	}
	public Date getMt_date() {
		return mt_date;
	}
	public void setMt_date(Date mt_date) {
		this.mt_date = mt_date;
	}
	public String getMt_lat() {
		return mt_lat;
	}
	public void setMt_lat(String mt_lat) {
		this.mt_lat = mt_lat;
	}
	public String getMt_lng() {
		return mt_lng;
	}
	public void setMt_lng(String mt_lng) {
		this.mt_lng = mt_lng;
	}
	
	@Override
	public String toString() {
		return "MeetingVo [mt_id=" + mt_id + ", user_email=" + user_email + ", prj_id=" + prj_id + ", mt_lc=" + mt_lc
				+ ", mt_exp=" + mt_exp + ", mt_date=" + mt_date + ", mt_lat=" + mt_lat + ", mt_lng=" + mt_lng + "]";
	}
	
	
}
