package kr.or.ddit.work_list.model;

import java.util.Date;

/**
 * Work_ListVo.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 * WORK_LIST - 업무리스트
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-19
 *
 * </pre>
 */
public class Work_ListVo {

	private int wrk_lst_id;		// 업무리스트 ID
	private int prj_id;			// 프로젝트 ID
	private String wrk_lst_nm;	// 업무리스트 이름
	private Date wrk_lst_dt;	// 업무리스트 생성일
	private String del_fl;		// 업무리스트 삭제 여부
	
	
	public Work_ListVo() {
		
	}
	
	@Override
	public String toString() {
		return "Work_ListVo [wrk_lst_id=" + wrk_lst_id + ", prj_id=" + prj_id + ", wrk_lst_nm=" + wrk_lst_nm
				+ ", wrk_lst_dt=" + wrk_lst_dt + ", del_fl=" + del_fl + "]";
	}
	public int getWrk_lst_id() {
		return wrk_lst_id;
	}
	public void setWrk_lst_id(int wrk_lst_id) {
		this.wrk_lst_id = wrk_lst_id;
	}
	public int getPrj_id() {
		return prj_id;
	}
	public void setPrj_id(int prj_id) {
		this.prj_id = prj_id;
	}
	public String getWrk_lst_nm() {
		return wrk_lst_nm;
	}
	public void setWrk_lst_nm(String wrk_lst_nm) {
		this.wrk_lst_nm = wrk_lst_nm;
	}
	public Date getWrk_lst_dt() {
		return wrk_lst_dt;
	}
	public void setWrk_lst_dt(Date wrk_lst_dt) {
		this.wrk_lst_dt = wrk_lst_dt;
	}
	public String getDel_fl() {
		return del_fl;
	}
	public void setDel_fl(String del_fl) {
		this.del_fl = del_fl;
	}
	
	
}
