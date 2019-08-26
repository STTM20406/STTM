package kr.or.ddit.work_push.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Work_PushVo.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 * WORK_PUSH - 업무예약 알림
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-19
 * 박서경 	수정 내역 : 2019-08-23
 *
 * </pre>
 */
public class Work_PushVo {

	private int wrk_rv_id;		// 업무 예약 알림 ID
	private int wrk_id;			// 업무 ID
	private int prj_id;			// 프로젝트 ID
	
	@DateTimeFormat(pattern = "yyyy-MM-dd kk:mm")
	private Date wrk_dt;		// 알림 일시
	private String user_email; 	// 예약 업무 생성자
	private String push_del_fl; 	// 알림 삭제 여부
	
	private String memType;	// 알림 보낼 멤버 타입(나, 전체, 팔로워, 배정)
	
	
	public String getWrkDtStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm");
		if(wrk_dt == null) {
			return "";
		}
		return sdf.format(wrk_dt);
	}
	
	
	public Work_PushVo() {
		
	}
	
	


	@Override
	public String toString() {
		return "Work_PushVo [wrk_rv_id=" + wrk_rv_id + ", wrk_id=" + wrk_id + ", prj_id=" + prj_id + ", wrk_dt="
				+ wrk_dt + ", user_eamil=" + user_email + ", push_del_fl=" + push_del_fl + ", memType="
				+ memType + "]";
	}


	public int getWrk_rv_id() {
		return wrk_rv_id;
	}
	public void setWrk_rv_id(int wrk_rv_id) {
		this.wrk_rv_id = wrk_rv_id;
	}
	public int getWrk_id() {
		return wrk_id;
	}
	public void setWrk_id(int wrk_id) {
		this.wrk_id = wrk_id;
	}
	public int getPrj_id() {
		return prj_id;
	}
	public void setPrj_id(int prj_id) {
		this.prj_id = prj_id;
	}
	public Date getWrk_dt() {
		return wrk_dt;
	}
	public void setWrk_dt(Date wrk_dt) {
		this.wrk_dt = wrk_dt;
	}

	public String getPush_del_fl() {
		return push_del_fl;
	}

	public void setPush_del_fl(String push_del_fl) {
		this.push_del_fl = push_del_fl;
	}

	public String getUser_eamil() {
		return user_email;
	}

	public void setUser_eamil(String user_eamil) {
		this.user_email = user_eamil;
	}

	public String getMemType() {
		return memType;
	}


	public void setMemType(String memType) {
		this.memType = memType;
	}
	
	
	
	
}
