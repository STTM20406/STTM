package kr.or.ddit.project.model;

import java.util.Date;


/**
 * ProjectVo.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 * PROJECT - 프로젝트
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-19
 *
 * </pre>
 */
public class ProjectVo {

	private int prj_id;			// 프로젝트 ID
	private String prj_nm;		// 프로젝트 이름
	private String prj_exp;     // 프로젝트 설명
	private String prj_auth;    // 프로젝트 권한
	private Date prj_start_dt;  // 프로젝트 시작일시
	private Date prj_end_dt;    // 프로젝트 마감일시
	private Date prj_cmp_dt;    // 프로젝트 실제 완료일시
	private String prj_st;      // 프로젝트 상태
	private String del_fl;      // 프로젝트 삭제 여부
	
	private String user_email; 	// 프로젝트 멤버 이메일
	
	
	public ProjectVo() {
		
	}

	public int getPrj_id() {
		return prj_id;
	}


	public void setPrj_id(int prj_id) {
		this.prj_id = prj_id;
	}


	public String getPrj_nm() {
		return prj_nm;
	}


	public void setPrj_nm(String prj_nm) {
		this.prj_nm = prj_nm;
	}


	public String getPrj_exp() {
		return prj_exp;
	}


	public void setPrj_exp(String prj_exp) {
		this.prj_exp = prj_exp;
	}


	public String getPrj_auth() {
		return prj_auth;
	}


	public void setPrj_auth(String prj_auth) {
		this.prj_auth = prj_auth;
	}


	public Date getPrj_start_dt() {
		return prj_start_dt;
	}


	public void setPrj_start_dt(Date prj_start_dt) {
		this.prj_start_dt = prj_start_dt;
	}


	public Date getPrj_end_dt() {
		return prj_end_dt;
	}


	public void setPrj_end_dt(Date prj_end_dt) {
		this.prj_end_dt = prj_end_dt;
	}


	public Date getPrj_cmp_dt() {
		return prj_cmp_dt;
	}


	public void setPrj_cmp_dt(Date prj_cmp_dt) {
		this.prj_cmp_dt = prj_cmp_dt;
	}


	public String getPrj_st() {
		return prj_st;
	}


	public void setPrj_st(String prj_st) {
		this.prj_st = prj_st;
	}


	public String getDel_fl() {
		return del_fl;
	}


	public void setDel_fl(String del_fl) {
		this.del_fl = del_fl;
	}


	public String getUser_email() {
		return user_email;
	}


	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	@Override
	public String toString() {
		return "ProjectVo [prj_id=" + prj_id + ", prj_nm=" + prj_nm + ", prj_exp=" + prj_exp + ", prj_auth=" + prj_auth
				+ ", prj_start_dt=" + prj_start_dt + ", prj_end_dt=" + prj_end_dt + ", prj_cmp_dt=" + prj_cmp_dt
				+ ", prj_st=" + prj_st + ", del_fl=" + del_fl + ", user_email=" + user_email + "]";
	}
	
	
}
