package kr.or.ddit.users.model;

/**
 * UserVo.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 * USERS - 회원
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-19
 *
 * </pre>
 */
public class UserVo {

	private String user_email;		// 이메일
	private String user_pass;		// 비밀번호
	private String user_nm;			// 이름
	private String user_hp;			// 휴대폰번호
	private String user_job;		// 직무
	private String user_token;		// 토큰정보
	private String user_right;		// 회원구분
	private String user_st;			// 휴면계정여부
	private String user_path;		// 사진경로
	private String user_filename;	// 원본사진파일명
	
	public UserVo() {
	
	}
	
	public UserVo(String user_email, String user_pass, String user_nm, String user_hp, String user_job,
			String user_token, String user_right, String user_st, String user_path, String user_filename) {
		super();
		this.user_email = user_email;
		this.user_pass = user_pass;
		this.user_nm = user_nm;
		this.user_hp = user_hp;
		this.user_job = user_job;
		this.user_token = user_token;
		this.user_right = user_right;
		this.user_st = user_st;
		this.user_path = user_path;
		this.user_filename = user_filename;
	}

	@Override
	public String toString() {
		return "UserVo [user_email=" + user_email + ", user_pass=" + user_pass + ", user_nm=" + user_nm + ", user_hp="
				+ user_hp + ", user_job=" + user_job + ", user_token=" + user_token + ", user_right=" + user_right
				+ ", user_st=" + user_st + ", user_path=" + user_path + ", user_filename=" + user_filename + "]";
	}

	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_pass() {
		return user_pass;
	}
	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getUser_hp() {
		return user_hp;
	}
	public void setUser_hp(String user_hp) {
		this.user_hp = user_hp;
	}
	public String getUser_job() {
		return user_job;
	}
	public void setUser_job(String user_job) {
		this.user_job = user_job;
	}
	public String getUser_token() {
		return user_token;
	}
	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}
	public String getUser_right() {
		return user_right;
	}
	public void setUser_right(String user_right) {
		this.user_right = user_right;
	}
	public String getUser_st() {
		return user_st;
	}
	public void setUser_st(String user_st) {
		this.user_st = user_st;
	}
	public String getUser_path() {
		return user_path;
	}
	public void setUser_path(String user_path) {
		this.user_path = user_path;
	}
	public String getUser_filename() {
		return user_filename;
	}
	public void setUser_filename(String user_filename) {
		this.user_filename = user_filename;
	}
	
}
