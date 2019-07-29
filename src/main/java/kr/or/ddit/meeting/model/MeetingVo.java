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
	private String user_email;      //이메일
	private int prj_id;             //프로젝트 ID
	private String mt_lc;           //미팅 장소
	private String mt_exp;          //미팅 설명
	private Date mt_date;           //미팅 일시
	private String mt_lat;          //위치 위도
	private String mt_lng;          //위치 경도
	private String mt_cmp_fl;       //미팅 완료 여부
	
	//기본생성자
	public MeetingVo() {
		
	}
	
	
	//생성자


	
	//getter, setter
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


	public String getMt_cmp_fl() {
		return mt_cmp_fl;
	}


	public void setMt_cmp_fl(String mt_cmp_fl) {
		this.mt_cmp_fl = mt_cmp_fl;
	}


	//toString
	@Override
	public String toString() {
		return "MeetingVo [mt_id=" + mt_id + ", user_email=" + user_email + ", prj_id=" + prj_id + ", mt_lc=" + mt_lc
				+ ", mt_exp=" + mt_exp + ", mt_date=" + mt_date + ", mt_lat=" + mt_lat + ", mt_lng=" + mt_lng
				+ ", mt_cmp_fl=" + mt_cmp_fl + "]";
	}
	
	
}
