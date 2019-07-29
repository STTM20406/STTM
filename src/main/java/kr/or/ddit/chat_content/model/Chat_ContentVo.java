package kr.or.ddit.chat_content.model;

import java.util.Date;

import com.google.gson.Gson;

/**
 * 
* Chat_ContentVo.java
*
* @author 김경호
* @version 1.0
* @see
* Chat_Content - 채팅 내용
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
public class Chat_ContentVo {
	
	private int ct_con_id; // 채팅 내용 아이디
	private int ct_id; // 채팅방 아이디
	private String user_email; // 이메일
	private String ch_msg; // 메세지 내용
	private Date ch_msg_dt; // 메세지 보낸 시간
	
	public Chat_ContentVo() {

	}

	
	//대화내용 삭제할 때 사용
	public Chat_ContentVo(int ct_id, String user_email) {
		super();
		this.ct_id = ct_id;
		this.user_email = user_email;
	}



	public Chat_ContentVo(int ct_id, String user_email, String ch_msg) {
		super();
		this.ct_id = ct_id;
		this.user_email = user_email;
		this.ch_msg = ch_msg;
	}

	@Override
	public String toString() {
		return "Chat_ContentVo [ct_con_id=" + ct_con_id + ", ct_id=" + ct_id + ", user_email=" + user_email
				+ ", ch_msg=" + ch_msg + ", ch_msg_dt=" + ch_msg_dt + "]";
	}

	public int getCt_con_id() {
		return ct_con_id;
	}

	public void setCt_con_id(int ct_con_id) {
		this.ct_con_id = ct_con_id;
	}

	public int getCt_id() {
		return ct_id;
	}

	public void setCt_id(int ct_id) {
		this.ct_id = ct_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getCh_msg() {
		return ch_msg;
	}

	public void setCh_msg(String ch_msg) {
		this.ch_msg = ch_msg;
	}

	public Date getCh_msg_dt() {
		return ch_msg_dt;
	}

	public void setCh_msg_dt(Date ch_msg_dt) {
		this.ch_msg_dt = ch_msg_dt;
	}

	
	public static Chat_ContentVo convertMessage(String source) {
		Chat_ContentVo message = new Chat_ContentVo();
		Gson gson = new Gson();
		message = gson.fromJson(source,  Chat_ContentVo.class);
		return message;
	}
	
}
