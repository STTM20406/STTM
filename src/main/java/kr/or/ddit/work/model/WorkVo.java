package kr.or.ddit.work.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * WorkVo.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 * WORK - 업무
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-19
 *
 * </pre>
 */
public class WorkVo {
 
	private int wrk_id;				// 업무 ID
	private int wrk_lst_id;         		// 업무리스트 ID
	private int wrk_rv_id;          		// 업무 예약 알림 ID
	private int wrk_pr_id;          		// 부모 업무 ID
	private String user_email;		// 업무 작성자 이메일
	private String wrk_nm;          	// 업무 이름
	private Date wrk_dt;         		// 업무 생성일시
	private String wrk_grade;       	// 업무 등급
	private String wrk_color_cd;   	// 업무 컬러 코드
	
	@DateTimeFormat(pattern = "yyyy-MM-dd kk:mm")
	private Date wrk_start_dt;    		// 업무 시작일
	@DateTimeFormat(pattern = "yyyy-MM-dd kk:mm")
	private Date wrk_end_dt;      	// 업무 종료일
	
	private Date wrk_cmp_dt;      	// 업무 완료 체크일
	private String wrk_cmp_fl;      	// 업무 완료 여부
	private String wrk_del_fl;      		// 업무 삭제 여부
	
	//여기서부턴 Filter쪽에서 간편하게 값을 받아오기 위해 추가한 부분입니다.
	private int prj_id;				// 업무가 할당된 프로젝트 ID
	private String prj_nm;			// 업무가 할당된 프로젝트 이름
	private String wrk_lst_nm;		// 업무가 할당된 업무리스트 이름
	private String user_nm;			// 업무 작성자의 이름
	
	//권한 설정에 따라 가져
	private String auth; //권한레벨????
	
	public WorkVo() {

	}

	/**
	 * @param wrk_lst_id
	 * @param user_email
	 * @param wrk_nm
	 * @param wrk_color_cd
	 * @param wrk_start_dt
	 * @param wrk_end_dt
	 * @author 손영하 insert
	 */
	public WorkVo(int wrk_lst_id, String user_email, String wrk_nm, String wrk_color_cd, Date wrk_start_dt,Date wrk_end_dt ) {
		this.wrk_lst_id = wrk_lst_id;
		this.user_email = user_email;
		this.wrk_nm = wrk_nm;
		this.wrk_color_cd = wrk_color_cd;
		this.wrk_start_dt = wrk_start_dt;
		this.wrk_end_dt = wrk_end_dt;
	}
	
	/**
	 * @author 손영하  drag and drop update
	 * @param wrk_id
	 * @param wrk_start_dt
	 * @param wrk_end_dt
	 */
	public WorkVo(int wrk_id, Date wrk_start_dt,Date wrk_end_dt ) {
		this.wrk_id = wrk_id;
		this.wrk_start_dt = wrk_start_dt;
		this.wrk_end_dt = wrk_end_dt;
	}
	
	/**
	 * @param wrk_id
	 * @param wrk_lst_id
	 * @param wrk_nm
	 * @param wrk_color_cd
	 * @param wrk_start_dt
	 * @param wrk_end_dt
	 * 설명 : 해당 프로젝트 업데이트
	 */
	public WorkVo( int wrk_lst_id, String wrk_nm, String wrk_color_cd, Date wrk_start_dt, Date wrk_end_dt,int wrk_id) {
		this.wrk_lst_id = wrk_lst_id;
		this.wrk_nm = wrk_nm;
		this.wrk_color_cd = wrk_color_cd;
		this.wrk_start_dt = wrk_start_dt;
		this.wrk_end_dt = wrk_end_dt;
		this.wrk_id = wrk_id;
	}

	@Override
	public String toString() {
		return "WorkVo [wrk_id=" + wrk_id + ", wrk_lst_id=" + wrk_lst_id + ", wrk_rv_id=" + wrk_rv_id + ", wrk_pr_id="
				+ wrk_pr_id + ", user_email=" + user_email + ", wrk_nm=" + wrk_nm + ", wrk_dt=" + wrk_dt
				+ ", wrk_grade=" + wrk_grade + ", wrk_color_cd=" + wrk_color_cd + ", wrk_start_dt=" + wrk_start_dt
				+ ", wrk_end_dt=" + wrk_end_dt + ", wrk_cmp_dt=" + wrk_cmp_dt + ", wrk_cmp_fl=" + wrk_cmp_fl
				+ ", wrk_del_fl=" + wrk_del_fl + ", prj_id=" + prj_id + ", prj_nm=" + prj_nm + ", wrk_lst_nm="
				+ wrk_lst_nm + ", user_nm=" + user_nm + ", auth=" + auth + "]";
	}
	public int getWrk_id() {
		return wrk_id;
	}
	public void setWrk_id(int wrk_id) {
		this.wrk_id = wrk_id;
	}
	public int getWrk_lst_id() {
		return wrk_lst_id;
	}
	public void setWrk_lst_id(int wrk_lst_id) {
		this.wrk_lst_id = wrk_lst_id;
	}
	public int getWrk_rv_id() {
		return wrk_rv_id;
	}
	public void setWrk_rv_id(int wrk_rv_id) {
		this.wrk_rv_id = wrk_rv_id;
	}
	public int getWrk_pr_id() {
		return wrk_pr_id;
	}
	public void setWrk_pr_id(int wrk_pr_id) {
		this.wrk_pr_id = wrk_pr_id;
	}
	public String getWrk_nm() {
		return wrk_nm;
	}
	public void setWrk_nm(String wrk_nm) {
		this.wrk_nm = wrk_nm;
	}
	public Date getWrk_dt() {
		return wrk_dt;
	}
	public void setWrk_dt(Date wrk_dt) {
		this.wrk_dt = wrk_dt;
	}
	public String getWrk_color_cd() {
		return wrk_color_cd;
	}
	public void setWrk_color_cd(String wrk_color_cd) {
		this.wrk_color_cd = wrk_color_cd;
	}
	public Date getWrk_start_dt() {
		return wrk_start_dt;
	}
	public void setWrk_start_dt(Date wrk_start_dt) {
		this.wrk_start_dt = wrk_start_dt;
	}
	public Date getWrk_end_dt() {
		return wrk_end_dt;
	}
	public void setWrk_end_dt(Date wrk_end_dt) {
		this.wrk_end_dt = wrk_end_dt;
	}
	public Date getWrk_cmp_dt() {
		return wrk_cmp_dt;
	}
	public void setWrk_cmp_dt(Date wrk_cmp_dt) {
		this.wrk_cmp_dt = wrk_cmp_dt;
	}
	public String getWrk_cmp_fl() {
		return wrk_cmp_fl;
	}
	public void setWrk_cmp_fl(String wrk_cmp_fl) {
		this.wrk_cmp_fl = wrk_cmp_fl;
	}
	public String getWrk_del_fl() {
		return wrk_del_fl;
	}
	public void setWrk_del_fl(String wrk_del_fl) {
		this.wrk_del_fl = wrk_del_fl;
	}
	public String getWrk_grade() {
		return wrk_grade;
	}
	public void setWrk_grade(String wrk_grade) {
		this.wrk_grade = wrk_grade;
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
	public String getWrk_lst_nm() {
		return wrk_lst_nm;
	}
	public void setWrk_lst_nm(String wrk_lst_nm) {
		this.wrk_lst_nm = wrk_lst_nm;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
}
