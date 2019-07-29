package kr.or.ddit.chat_content.model;

public class ChatParticipateUserVo {
	
	private String user_nm; //사용자 이름
	private String ch_msg; // 메세지 내용
	private String user_email;//사용자 아이디
	
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getCh_msg() {
		return ch_msg;
	}
	public void setCh_msg(String ch_msg) {
		this.ch_msg = ch_msg;
	}
	
	
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	@Override
	public String toString() {
		return "ChatParticipateUserVo [user_nm=" + user_nm + ", ch_msg=" + ch_msg + ", user_email=" + user_email + "]";
	}

	
	
	
	
}
