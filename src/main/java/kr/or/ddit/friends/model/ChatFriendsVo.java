package kr.or.ddit.friends.model;

public class ChatFriendsVo {

	private String user_email;
	private String frd_email;
	private String user_nm;
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getFrd_email() {
		return frd_email;
	}
	public void setFrd_email(String frd_email) {
		this.frd_email = frd_email;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	@Override
	public String toString() {
		return "ChatFriendsVo [user_email=" + user_email + ", frd_email=" + frd_email + ", user_nm=" + user_nm + "]";
	}
	
	
	
	
}
