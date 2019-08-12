package kr.or.ddit.chat_room.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
* Chat_RoomVo.java
*
* @author 김경호
* @version 1.0
* @see
* Chat_Room - 채팅방
*
* <pre>
* << 개정이력(Modification Information) >>
*
* 수정자 수정내용
* ------ ------------------------
* 김경호 최초 생성
*
* </pre>
 */
public class Chat_RoomVo {
	
	private int ct_id; // 채팅방 아이디
	private String ct_nm; // 채팅방 이름
	private Date ct_dt; // 채팅방 생성일
	private int prj_id; // 프로젝트 아이디
	private int rn;
	
	public String getBirthStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(ct_dt == null) {
			return "";
		}
		return sdf.format(ct_dt);
	}
	
	
	public Chat_RoomVo() {

	}
	
	
	
	//프로젝트 멤버 채팅방 생성
	public Chat_RoomVo( String ct_nm, int prj_id) {
		super();
		this.ct_nm = ct_nm;
		this.prj_id = prj_id;
	}

	//일반채팅방 생성
	public Chat_RoomVo(String ct_nm) {
		super();
		this.ct_nm = ct_nm;
	}


	@Override
	public String toString() {
		return "Chat_RoomVo [ct_id=" + ct_id + ", ct_nm=" + ct_nm + ", ct_dt=" + ct_dt + "]";
	}
	
	

	public int getPrj_id() {
		return prj_id;
	}


	public void setPrj_id(int prj_id) {
		this.prj_id = prj_id;
	}


	public int getRn() {
		return rn;
	}


	public void setRn(int rn) {
		this.rn = rn;
	}


	public int getCt_id() {
		return ct_id;
	}

	public void setCt_id(int ct_id) {
		this.ct_id = ct_id;
	}

	public String getCt_nm() {
		return ct_nm;
	}

	public void setCt_nm(String ct_nm) {
		this.ct_nm = ct_nm;
	}

	public Date getCt_dt() {
		return ct_dt;
	}

	public void setCt_dt(Date ct_dt) {
		this.ct_dt = ct_dt;
	}
	
}
