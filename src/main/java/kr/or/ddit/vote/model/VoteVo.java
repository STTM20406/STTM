package kr.or.ddit.vote.model;

import java.util.Date;

/**
 * 
* VoteVo.java
*
* @author 김경호
* @version 1.0
* @see
* Vote - 투표
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
public class VoteVo {
	
	private int vote_id; // 투표 아이디
	private int prj_id; //프로젝트 아이디
	private String vote_email; // 투표 작성자
	private Date votd_start_date; // 투표 작성일
	private Date vote_end_date; // 투표 마감일
	private String vote_subject; // 투표 제목
	private String vote_con; // 투표 내용
	private String vote_ano; // 익명 여부
	private String vote_st; // 투표 상태
	
	public VoteVo() {

	}

	public VoteVo(int vote_id, int prj_id, String vote_email, Date votd_start_date, Date vote_end_date,
			String vote_subject, String vote_con, String vote_ano, String vote_st) {
		super();
		this.vote_id = vote_id;
		this.prj_id = prj_id;
		this.vote_email = vote_email;
		this.votd_start_date = votd_start_date;
		this.vote_end_date = vote_end_date;
		this.vote_subject = vote_subject;
		this.vote_con = vote_con;
		this.vote_ano = vote_ano;
		this.vote_st = vote_st;
	}

	@Override
	public String toString() {
		return "VoteVo [vote_id=" + vote_id + ", prj_id=" + prj_id + ", vote_email=" + vote_email + ", votd_start_date="
				+ votd_start_date + ", vote_end_date=" + vote_end_date + ", vote_subject=" + vote_subject
				+ ", vote_con=" + vote_con + ", vote_ano=" + vote_ano + ", vote_st=" + vote_st + "]";
	}

	public int getVote_id() {
		return vote_id;
	}

	public void setVote_id(int vote_id) {
		this.vote_id = vote_id;
	}

	public int getPrj_id() {
		return prj_id;
	}

	public void setPrj_id(int prj_id) {
		this.prj_id = prj_id;
	}

	public String getVote_email() {
		return vote_email;
	}

	public void setVote_email(String vote_email) {
		this.vote_email = vote_email;
	}

	public Date getVotd_start_date() {
		return votd_start_date;
	}

	public void setVotd_start_date(Date votd_start_date) {
		this.votd_start_date = votd_start_date;
	}

	public Date getVote_end_date() {
		return vote_end_date;
	}

	public void setVote_end_date(Date vote_end_date) {
		this.vote_end_date = vote_end_date;
	}

	public String getVote_subject() {
		return vote_subject;
	}

	public void setVote_subject(String vote_subject) {
		this.vote_subject = vote_subject;
	}

	public String getVote_con() {
		return vote_con;
	}

	public void setVote_con(String vote_con) {
		this.vote_con = vote_con;
	}

	public String getVote_ano() {
		return vote_ano;
	}

	public void setVote_ano(String vote_ano) {
		this.vote_ano = vote_ano;
	}

	public String getVote_st() {
		return vote_st;
	}

	public void setVote_st(String vote_st) {
		this.vote_st = vote_st;
	}

}
