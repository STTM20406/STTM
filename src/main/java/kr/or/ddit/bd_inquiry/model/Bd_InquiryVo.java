package kr.or.ddit.bd_inquiry.model;

import java.util.Date;

/**
 * 
* Bd_InquiryVo.java
*
* @author 김경호
* @version 1.0
* @see
* Bd_Inquiry - 1:1 문의 게시판
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
public class Bd_InquiryVo {
	
	private int inq_id; // 문의 아이디
	private String user_email; // 문의 이메일
	private String subject; // 문의 제목
	private String inq_cate; // 문의 카테고리
	private String inq_con; // 문의 내용
	private String ans_con; // 답변 내용
	private Date inq_dt; // 문의 작성일
	private Date ans_dt; // 답변 작성일
	private String ans_st; // 답변 여부
	private String del_fl; // 글 삭제 여부
	
	
	public Bd_InquiryVo() {

	}

	
	
	public Bd_InquiryVo(int inq_id, String ans_con) {
		super();
		this.inq_id = inq_id;
		this.ans_con = ans_con;
	}

	
	

	public Bd_InquiryVo(int inq_id, String subject, String inq_con) {
		super();
		this.inq_id = inq_id;
		this.subject = subject;
		this.inq_con = inq_con;
	}



	public Bd_InquiryVo(String user_email, String subject, String inq_cate, String inq_con) {
		super();
		this.user_email = user_email;
		this.subject = subject;
		this.inq_cate = inq_cate;
		this.inq_con = inq_con;
	}



	public Bd_InquiryVo(int inq_id, String user_email, String subject, String inq_cate, String inq_con, String ans_con,
			Date inq_dt, Date ans_dt, String ans_st, String del_fl) {
		super();
		this.inq_id = inq_id;
		this.user_email = user_email;
		this.subject = subject;
		this.inq_cate = inq_cate;
		this.inq_con = inq_con;
		this.ans_con = ans_con;
		this.inq_dt = inq_dt;
		this.ans_dt = ans_dt;
		this.ans_st = ans_st;
		this.del_fl = del_fl;
	}

	@Override
	public String toString() {
		return "Bd_InquiryVo [inq_id=" + inq_id + ", user_email=" + user_email + ", subject=" + subject + ", inq_cate="
				+ inq_cate + ", inq_con=" + inq_con + ", ans_con=" + ans_con + ", inq_dt=" + inq_dt + ", ans_dt="
				+ ans_dt + ", ans_st=" + ans_st + ", del_fl=" + del_fl + "]";
	}

	public int getInq_id() {
		return inq_id;
	}

	public void setInq_id(int inq_id) {
		this.inq_id = inq_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getInq_cate() {
		return inq_cate;
	}

	public void setInq_cate(String inq_cate) {
		this.inq_cate = inq_cate;
	}

	public String getInq_con() {
		return inq_con;
	}

	public void setInq_con(String inq_con) {
		this.inq_con = inq_con;
	}

	public String getAns_con() {
		return ans_con;
	}

	public void setAns_con(String ans_con) {
		this.ans_con = ans_con;
	}

	public Date getInq_dt() {
		return inq_dt;
	}

	public void setInq_dt(Date inq_dt) {
		this.inq_dt = inq_dt;
	}

	public Date getAns_dt() {
		return ans_dt;
	}

	public void setAns_dt(Date ans_dt) {
		this.ans_dt = ans_dt;
	}

	public String getAns_st() {
		return ans_st;
	}

	public void setAns_st(String ans_st) {
		this.ans_st = ans_st;
	}

	public String getDel_fl() {
		return del_fl;
	}

	public void setDel_fl(String del_fl) {
		this.del_fl = del_fl;
	}
	
}
