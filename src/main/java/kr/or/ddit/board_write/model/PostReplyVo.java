package kr.or.ddit.board_write.model;

import java.util.Date;

public class PostReplyVo {
	private int write_id; // 게시글 아이디
	private int board_id; // 게시판 아이디
	private String user_email; // 이메일
	private String subject; // 제목
	private String content; // 내용
	private Date writedate; // 작성 일시
	private int view_cnt; // 조회수
	private int like_cnt; // 좋아요수
	private String del_yn; // 삭제 여부
	private int comm_id; // 댓글 아이디
	private String del_fl; // 글 삭제 여부
	
	@Override
	public String toString() {
		return "PostReplyVo [write_id=" + write_id + ", board_id=" + board_id + ", user_email=" + user_email
				+ ", subject=" + subject + ", content=" + content + ", writedate=" + writedate + ", view_cnt="
				+ view_cnt + ", like_cnt=" + like_cnt + ", del_yn=" + del_yn + ", comm_id=" + comm_id + ", del_fl="
				+ del_fl + ", r_content=" + "]";
	}


	public PostReplyVo() {
		
	}
	
	public PostReplyVo(int write_id, int board_id, String user_email, String subject, String content, Date writedate,
			int view_cnt, int like_cnt, String del_yn, int comm_id, String del_fl) {
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
		this.comm_id = comm_id;
		this.del_fl = del_fl;
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
	public int getComm_id() {
		return comm_id;
	}
	public void setComm_id(int comm_id) {
		this.comm_id = comm_id;
	}
	public String getDel_fl() {
		return del_fl;
	}
	public void setDel_fl(String del_fl) {
		this.del_fl = del_fl;
	}
	
	
	
	
}
