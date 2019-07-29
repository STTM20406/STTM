package kr.or.ddit.project_trnf_hs.model;

import java.util.Date;

/**
 * Project_Trnf_HsVo.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 * PROJECT_TRNF_HS - 프로젝트 소유권 이전이력
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-19
 *
 * </pre>
 */
public class Project_Trnf_HsVo {

	private int trnp_id;			// 이전 ID
	private int prj_id;				// 프로젝트 ID
	private String prj_own_email;	// 프로젝트 소유자
	private String prj_trns_email;	// 프로젝트 이전권자
	private Date trnp_date;			// 이전 일시
	
	
	
	public Project_Trnf_HsVo() {
		
	}
	
	@Override
	public String toString() {
		return "Project_Trnf_HsVo [trnp_id=" + trnp_id + ", prj_id=" + prj_id + ", prj_own_email=" + prj_own_email
				+ ", prj_trns_email=" + prj_trns_email + ", trnp_date=" + trnp_date + "]";
	}
	public int getTrnp_id() {
		return trnp_id;
	}
	public void setTrnp_id(int trnp_id) {
		this.trnp_id = trnp_id;
	}
	public int getPrj_id() {
		return prj_id;
	}
	public void setPrj_id(int prj_id) {
		this.prj_id = prj_id;
	}
	public String getPrj_own_email() {
		return prj_own_email;
	}
	public void setPrj_own_email(String prj_own_email) {
		this.prj_own_email = prj_own_email;
	}
	public String getPrj_trns_email() {
		return prj_trns_email;
	}
	public void setPrj_trns_email(String prj_trns_email) {
		this.prj_trns_email = prj_trns_email;
	}
	public Date getTrnp_date() {
		return trnp_date;
	}
	public void setTrnp_date(Date trnp_date) {
		this.trnp_date = trnp_date;
	}
	
	
	
	
}
