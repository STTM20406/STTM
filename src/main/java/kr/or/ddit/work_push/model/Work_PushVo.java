package kr.or.ddit.work_push.model;

import java.util.Date;

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
 *
 * </pre>
 */
public class Work_PushVo {

	private int wrk_rv_id;	// 업무 예약 알림 ID
	private int wrk_id;		// 업무 ID
	private int prj_id;		// 프로젝트 ID
	private Date wrk_dt;	// 알림 일시
	
	
	
	public Work_PushVo() {
		
	}
	
	@Override
	public String toString() {
		return "Work_PushVo [wrk_rv_id=" + wrk_rv_id + ", wrk_id=" + wrk_id + ", prj_id=" + prj_id + ", wrk_dt="
				+ wrk_dt + "]";
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
	
	
}
