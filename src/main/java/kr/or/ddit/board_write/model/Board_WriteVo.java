package kr.or.ddit.board_write.model;

import java.util.Date;

/**
 * 
* Board_WriteVo.java
*
* @author 김경호
* @version 1.0
* @see
* Board_Write - 게시판 게시글
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
public class Board_WriteVo {
	
	private int write_id; // 게시글 아이디
	private int board_id; // 게시판 아이디
	private String user_email; // 이메일
	private String subject; // 제목
	private String content; // 내용
	private Date writedate; // 작성 일시
	private int view_cnt; // 조회수
	private int like_cnt; // 좋아요수
	private String del_yn; // 삭제 여부
	
	public Board_WriteVo() {

	}
	
	

	public Board_WriteVo(int board_id, String user_email, String subject, String content) {
		super();
		this.board_id = board_id;
		this.user_email = user_email;
		this.subject = subject;
		this.content = content;
	}
	public Board_WriteVo(int write_id, int board_id, String user_email, String subject, String content, Date writedate,
			int view_cnt, int like_cnt, String del_yn) {
		super();
		this.write_id = write_id;
		this.board_id = board_id;
		this.user_email = user_email;
		this.subject = subject;
		this.content = content;
		this.writedate = writedate;
		this.view_cnt = view_cnt;
		this.like_cnt = like_cnt;
		this.del_yn = del_yn;
	}

	@Override
	public String toString() {
		return "Board_WriteVo [write_id=" + write_id + ", board_id=" + board_id + ", user_email=" + user_email
				+ ", subject=" + subject + ", content=" + content + ", writedate=" + writedate + ", view_cnt="
				+ view_cnt + ", like_cnt=" + like_cnt + ", del_yn=" + del_yn + "]";
	}

	public int getWrite_id() {
		return write_id;
	}

	public void setWrite_id(int write_id) {
		this.write_id = write_id;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
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

	public int getView_cnt() {
		return view_cnt;
	}

	public void setView_cnt(int view_cnt) {
		this.view_cnt = view_cnt;
	}

	public int getLike_cnt() {
		return like_cnt;
	}

	public void setLike_cnt(int like_cnt) {
		this.like_cnt = like_cnt;
	}

	public String getDel_yn() {
		return del_yn;
	}

	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}

}
