package kr.or.ddit.friends.model;

import java.util.Date;

/**
 * 
 * FriendsVo.java
 *
 * @author 박서경
 * @version 1.0
 * @see
 * FRIENDS - 친구목록
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
public class FriendsVo {
	
	private String user_email;		//이메일
	private String frd_email;       //친구 이메일
	private Date frd_dt;          //처리 날짜
	
	private String user_nm; // 친구 이름
	
	//기본 생성자
	public FriendsVo() {

	}
	
	//생성자
		public FriendsVo(String user_email, String frd_email) {
			this.user_email = user_email;
			this.frd_email = frd_email;
		}
	
	//생성자
	public FriendsVo(String user_email, String frd_email, Date frd_dt, String user_nm) {
		super();
		this.user_email = user_email;
		this.frd_email = frd_email;
		this.frd_dt = frd_dt;
		this.user_nm = user_nm;
	}
	
	//getter, setter
	public String getUser_email() {
		return user_email;
	}

	//toString
	@Override
	public String toString() {
		return "FriendsVo [user_email=" + user_email + ", frd_email=" + frd_email + ", frd_dt=" + frd_dt + ", user_nm="
				+ user_nm + "]";
	}

	public String getFrd_email() {
		return frd_email;
	}

	public void setFrd_email(String frd_email) {
		this.frd_email = frd_email;
	}

	public Date getFrd_dt() {
		return frd_dt;
	}

	public void setFrd_dt(Date frd_dt) {
		this.frd_dt = frd_dt;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	
}
