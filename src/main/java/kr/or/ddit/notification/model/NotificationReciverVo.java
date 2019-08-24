package kr.or.ddit.notification.model;

import java.util.Date;

/**
 * 
 * NotificationVo.java
 *
 * @author 박서경
 * @version 1.0
 * @see
 * NOTIFICATION - 알림
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
public class NotificationReciverVo {
	
	private int not_id;			//알림 ID
	private String not_cd;      //알림 코드
	private String not_con;     //알림 내용
	private Date not_dt;        //알림 발생 일시
	
	private String rcv_email;	// 수신자 이메일
	
	//기본생성자
	public NotificationReciverVo() {

	}
	
	//생성자
	
	
	//getter, setter
	public int getNot_id() {
		return not_id;
	}

	public void setNot_id(int not_id) {
		this.not_id = not_id;
	}

	public String getNot_cd() {
		return not_cd;
	}

	public void setNot_cd(String not_cd) {
		this.not_cd = not_cd;
	}

	public String getNot_con() {
		return not_con;
	}

	public void setNot_con(String not_con) {
		this.not_con = not_con;
	}

	public Date getNot_dt() {
		return not_dt;
	}

	public void setNot_dt(Date not_dt) {
		this.not_dt = not_dt;
	}
	
	
	//toString
		@Override
		public String toString() {
			return "NotificationVo [not_id=" + not_id + ", not_cd=" + not_cd + ", not_con=" + not_con + ", not_dt=" + not_dt
					+ ", rcv_email=" + rcv_email + "]";
		}
		
	
	
}
