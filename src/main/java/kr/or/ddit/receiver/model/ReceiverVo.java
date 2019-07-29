package kr.or.ddit.receiver.model;

/**
 * ReceiverVo.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 * RECEIVER - 수신자
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-19
 *
 * </pre>
 */
public class ReceiverVo {

	private String rcv_email;	// 수신자 이메일
	private int not_id;			// 알림 ID
	
	public ReceiverVo() {
	}
	
	@Override
	public String toString() {
		return "ReceiverVo [rcv_email=" + rcv_email + ", not_id=" + not_id + "]";
	}
	public String getRcv_email() {
		return rcv_email;
	}
	public void setRcv_email(String rcv_email) {
		this.rcv_email = rcv_email;
	}
	public int getNot_id() {
		return not_id;
	}
	public void setNot_id(int not_id) {
		this.not_id = not_id;
	}
	
	
}
