package kr.or.ddit.board_answer.model;

import java.util.Date;

/**
 * 
* Board_AnswerVo.java
*
* @author 김경호
* @version 1.0
* @see
* Board_Answer 게시판 답변
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
public class Board_AnswerVo {
	
	private int comm_id; // 댓글 아이디
	private int write_id; // 게시글 아이디
	private String user_email; // 이메일
	private String content; // 내용
	private Date writedate; // 작성 일시
	private String del_fl; // 글 삭제 여부
	private int rn;
	public Board_AnswerVo() {

	}

	
	
	public Board_AnswerVo(int write_id, String user_email, String content) {
		super();
		this.write_id = write_id;
		this.user_email = user_email;
		this.content = content;
	}



	public Board_AnswerVo(int comm_id, int write_id, String user_email, String content, Date writedate, String del_fl) {
		super();
		this.comm_id = comm_id;
		this.write_id = write_id;
		this.user_email = user_email;
		this.content = content;
		this.writedate = writedate;
		this.del_fl = del_fl;
	}

	@Override
	public String toString() {
		return "Board_AnswerVo [comm_id=" + comm_id + ", write_id=" + write_id + ", user_email=" + user_email
				+ ", content=" + content + ", writedate=" + writedate + ", del_fl=" + del_fl + "]";
	}

	
	public int getRn() {
		return rn;
	}



	public void setRn(int rn) {
		this.rn = rn;
	}



	public int getComm_id() {
		return comm_id;
	}

	public void setComm_id(int comm_id) {
		this.comm_id = comm_id;
	}

	public int getWrite_id() {
		return write_id;
	}

	public void setWrite_id(int write_id) {
		this.write_id = write_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWritedate() {
		return writedate;
	}

	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}

	public String getDel_fl() {
		return del_fl;
	}

	public void setDel_fl(String del_fl) {
		this.del_fl = del_fl;
	}
	
}
