package kr.or.ddit.friend_req.model;

import java.util.Date;

/**
 * 
 * Friend_ReqVo.java
 *
 * @author 박서경
 * @version 1.0
 * @see
 * FRIEND_REQ - 친구 요청
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

public class Friend_ReqVo {
	
	private int req_id;				//요청 ID
	private String user_email;      //요청 보낸 사람
	private String req_email;       //요청 받은 사람
	private Date req_dt;          	//요청 일시
	private String req_st;          //요청 상태
	private Date prc_dt;          	//처리 일시
	
	private String user_nm;			// 친구 요청한 친구 이름
	
	//기본 생성자
	public Friend_ReqVo() {
	}
	
	
    //생성자
	public Friend_ReqVo(String user_email, String req_email) {
		super();
		this.user_email = user_email;
		this.req_email = req_email;
	}
	
	//toString
	@Override
	public String toString() {
		return "Friend_ReqVo [req_id=" + req_id + ", user_email=" + user_email + ", req_email=" + req_email
				+ ", req_dt=" + req_dt + ", req_st=" + req_st + ", prc_dt=" + prc_dt + "]";
	}

	//getter, setter
	public int getReq_id() {
		return req_id;
	}


	public void setReq_id(int req_id) {
		this.req_id = req_id;
	}


	public String getUser_email() {
		return user_email;
	}


	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}


	public String getReq_email() {
		return req_email;
	}


	public void setReq_email(String req_email) {
		this.req_email = req_email;
	}


	public Date getReq_dt() {
		return req_dt;
	}


	public void setReq_dt(Date req_dt) {
		this.req_dt = req_dt;
	}


	public String getReq_st() {
		return req_st;
	}


	public void setReq_st(String req_st) {
		this.req_st = req_st;
	}


	public Date getPrc_dt() {
		return prc_dt;
	}


	public void setPrc_dt(Date prc_dt) {
		this.prc_dt = prc_dt;
	}


	public String getUser_nm() {
		return user_nm;
	}


	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	
}
