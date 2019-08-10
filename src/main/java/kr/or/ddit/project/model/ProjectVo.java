package kr.or.ddit.project.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


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
 * 박서경   수정 이력 : 2019-07-31
 *
 * </pre>
 */
public class ProjectVo {

	private int prj_id;					// 프로젝트 ID
	private String prj_nm;			// 프로젝트 이름
	private String prj_exp;     		// 프로젝트 설명
	private String prj_auth;    		// 프로젝트 권한
	private Date prj_start_dt;  	// 프로젝트 시작일시
	private Date prj_end_dt;    	// 프로젝트 마감일시
	private Date prj_cmp_dt;    	// 프로젝트 실제 완료일시
	private String prj_st;      		// 프로젝트 상태
	private Date prj_update;      	// 프로젝트 업데이트 일시
	private String del_fl;      	// 프로젝트 삭제 여부
	
	private String user_email; 		// 프로젝트 멤버 이메일
	
	
	public String getPrjStartDtStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(prj_start_dt == null) {
			return "";
		}
		return sdf.format(prj_start_dt);
	}
	
	public String getPrjEndDtStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(prj_end_dt == null) {
			return "";
		}
		return sdf.format(prj_end_dt);
	}
	
	public String getPrjCmpDtStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(prj_cmp_dt == null) {
			return "";
		}
		return sdf.format(prj_cmp_dt);
	}
	
//	public String getPrjUpdateStr() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		if(prj_update == null) {
//			return "";
//		}
//		return sdf.format(prj_update);
//	}
	
	
	public ProjectVo() {
		
	}
	

	/**
	 * 영하가 달으래서 달아요. 프로젝트 설정 업데이트 할려고 만든 생성자님
	 * @param prj_id
	 * @param prj_nm
	 * @param prj_exp
	 * @param prj_auth
	 * @param prj_start_dt
	 * @param prj_end_dt
	 * @param prj_cmp_dt
	 * @param prj_st
	 */
	public ProjectVo(int prj_id, String prj_nm, String prj_exp, String prj_auth, Date prj_start_dt, Date prj_end_dt,
		Date prj_cmp_dt, String prj_st) {
		super();
		this.prj_id = prj_id;
		this.prj_nm = prj_nm;
		this.prj_exp = prj_exp;
		this.prj_auth = prj_auth;
		this.prj_start_dt = prj_start_dt;
		this.prj_end_dt = prj_end_dt;
		this.prj_cmp_dt = prj_cmp_dt;
		this.prj_st = prj_st;
	}
	
	/**
	 * 프로젝트 설정 업데이트 생성자
	 * @param prj_id
	 * @param prj_nm
	 * @param prj_exp
	 * @param prj_auth
	 * @param prj_st
	 */
	public ProjectVo(int prj_id, String prj_nm, String prj_exp, String prj_auth,  String prj_st) {
			super();
			this.prj_id = prj_id;
			this.prj_nm = prj_nm;
			this.prj_exp = prj_exp;
			this.prj_auth = prj_auth;
			this.prj_st = prj_st;
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
	

	public Date getPrj_update() {
		return prj_update;
	}

	public void setPrj_update(Date prj_update) {
		this.prj_update = prj_update;
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
				+ ", prj_st=" + prj_st + ", prj_update=" + prj_update + ", del_fl=" + del_fl + ", user_email="
				+ user_email + "]";
	}

	
}
