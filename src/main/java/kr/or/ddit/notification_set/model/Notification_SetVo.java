package kr.or.ddit.notification_set.model;


/**
 * 
 * Notification_SetVo.java
 *
 * @author 박서경
 * @version 1.0
 * @see
 * NOTIFICATION_SET - 알림 셋팅
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
public class Notification_SetVo {
	
	private int not_set_id;			//알림 설정 ID
	private String user_email;      //이메일
	private String not_cd;          //알림 코드
	private String not_chk_fl;      //알림 체크 유무
	
	//기본 생성자
	public Notification_SetVo() {

	}
	
	
	//생성자
	
	
	
	//getter, setter
	public int getNot_set_id() {
		return not_set_id;
	}

	public void setNot_set_id(int not_set_id) {
		this.not_set_id = not_set_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getNot_cd() {
		return not_cd;
	}

	public void setNot_cd(String not_cd) {
		this.not_cd = not_cd;
	}

	public String getNot_chk_fl() {
		return not_chk_fl;
	}

	public void setNot_chk_fl(String not_chk_fl) {
		this.not_chk_fl = not_chk_fl;
	}

	
	//toString
	@Override
	public String toString() {
		return "Notification_SetVo [not_set_id=" + not_set_id + ", user_email=" + user_email + ", not_cd=" + not_cd
				+ ", not_chk_fl=" + not_chk_fl + "]";
	}
	
	
}
