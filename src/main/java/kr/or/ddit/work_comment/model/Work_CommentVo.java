package kr.or.ddit.work_comment.model;

/**
 * Work_CommentVo.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 * WORK_COMMENT - 업무코멘트
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-19
 *
 * </pre>
 */
public class Work_CommentVo {

	private int comm_id;			// 코멘트 ID
	private int prj_id;				// 프로젝트 ID
	private String user_email;		// 이메일
	private int wrk_id;				// 업무 ID
	private String comm_content;	// 코멘트 내용
	private String comm_date;		// 코멘트 작성일
	private String del_fl;			// 삭제 여부
	
	
	
	public Work_CommentVo() {
		
	}
	
	@Override
	public String toString() {
		return "Work_CommentVo [comm_id=" + comm_id + ", prj_id=" + prj_id + ", user_email=" + user_email + ", wrk_id="
				+ wrk_id + ", comm_content=" + comm_content + ", comm_date=" + comm_date + ", del_fl=" + del_fl + "]";
	}
	public int getComm_id() {
		return comm_id;
	}
	public void setComm_id(int comm_id) {
		this.comm_id = comm_id;
	}
	public int getPrj_id() {
		return prj_id;
	}
	public void setPrj_id(int prj_id) {
		this.prj_id = prj_id;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public int getWrk_id() {
		return wrk_id;
	}
	public void setWrk_id(int wrk_id) {
		this.wrk_id = wrk_id;
	}
	public String getComm_content() {
		return comm_content;
	}
	public void setComm_content(String comm_content) {
		this.comm_content = comm_content;
	}
	public String getComm_date() {
		return comm_date;
	}
	public void setComm_date(String comm_date) {
		this.comm_date = comm_date;
	}
	public String getDel_fl() {
		return del_fl;
	}
	public void setDel_fl(String del_fl) {
		this.del_fl = del_fl;
	}
	
	
}
