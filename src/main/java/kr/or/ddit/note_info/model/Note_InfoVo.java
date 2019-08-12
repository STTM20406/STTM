package kr.or.ddit.note_info.model;

import java.util.Date;

/**
 * 
* Note_InfoVo.java
*
* @author 김경호
* @version 1.0
* @see
* Note_Info - 쪽지 정보
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
public class Note_InfoVo {
	
	private int note_id; // 쪽지 아이디
	private String send_email; // 쪽지 발신자
	private String rcv_email; // 쪽지 수신자
	private Date send_date; // 쪽지 발신 일시
	private Date rcv_date; // 쪽지 수신 일시
	private String read_fl; // 쪽지 읽음 여부
	private String send_del_fl; // 발신인 삭제 여부
	private String rcv_del_fl; // 수신인 삭제 여부
	
	public Note_InfoVo() {

	}

	public Note_InfoVo(int note_id, String send_email, String rcv_email, Date send_date, Date rcv_fl, String read_fl,
			String send_del_fl, String rcv_del_fl) {
		super();
		this.note_id = note_id;
		this.send_email = send_email;
		this.rcv_email = rcv_email;
		this.send_date = send_date;
		this.rcv_fl = rcv_fl;
		this.read_fl = read_fl;
		this.send_del_fl = send_del_fl;
		this.rcv_del_fl = rcv_del_fl;
	}

	@Override
	public String toString() {
		return "Note_InfoVo [note_id=" + note_id + ", send_email=" + send_email + ", rcv_email=" + rcv_email
				+ ", send_date=" + send_date + ", rcv_fl=" + rcv_fl + ", read_fl=" + read_fl + ", send_del_fl="
				+ send_del_fl + ", rcv_del_fl=" + rcv_del_fl + "]";
	}

	public int getNote_id() {
		return note_id;
	}

	public void setNote_id(int note_id) {
		this.note_id = note_id;
	}

	public String getSend_email() {
		return send_email;
	}

	public void setSend_email(String send_email) {
		this.send_email = send_email;
	}

	public String getRcv_email() {
		return rcv_email;
	}

	public void setRcv_email(String rcv_email) {
		this.rcv_email = rcv_email;
	}

	public Date getSend_date() {
		return send_date;
	}

	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}

	public Date getRcv_fl() {
		return rcv_fl;
	}

	public void setRcv_fl(Date rcv_fl) {
		this.rcv_fl = rcv_fl;
	}

	public String getRead_fl() {
		return read_fl;
	}

	public void setRead_fl(String read_fl) {
		this.read_fl = read_fl;
	}

	public String getSend_del_fl() {
		return send_del_fl;
	}

	public void setSend_del_fl(String send_del_fl) {
		this.send_del_fl = send_del_fl;
	}

	public String getRcv_del_fl() {
		return rcv_del_fl;
	}

	public void setRcv_del_fl(String rcv_del_fl) {
		this.rcv_del_fl = rcv_del_fl;
	}
	
}
