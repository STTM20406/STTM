package kr.or.ddit.chat_mem.model;

/**
 * 
* Chat_MemVo.java
*
* @author 김경호
* @version 1.0
* @see
* Chat_MemVo - 채팅 사용자 정보
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
public class Chat_MemVo {
	
	private int ct_id; // 채팅방 아이디
	private String user_email; // 이메일
	
	public Chat_MemVo() {

	}

	//채팅방 멤버 탈퇴
	public Chat_MemVo(int ct_id, String user_email) {
		super();
		this.ct_id = ct_id;
		this.user_email = user_email;
	}

	@Override
	public String toString() {
		return "Chat_MemVo [ct_id=" + ct_id + ", user_email=" + user_email + "]";
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
	
}
