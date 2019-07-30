package kr.or.ddit.filter.model;

import java.util.List;

/**
 * FilterVo.java
 *
 * @author 유승진
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 유승진 2019-07-22 최초 생성
 *
 * </pre>
 */
public class FilterVo {
	/*
	 	검색 필터로 사용할 값들은 다음과 같다.
	 	
	 	-	업무 기간 : 최근 30, 60, 90일, 전체 - int ** 전체는 0
	 	-	내 업무 / 전체 업무
	 	-	업무 주체 : 내게 배정된 업무, 내가 작성한 업무, 내가 팔로우하는 업무
	 	-	프로젝트 : 프로젝트별 설정 가능
	 	-	마감일 : 마감일 지남, 이번주까지, 이번 달까지, 마감일 없음
	 	-	상태 : 완료 / 삭제됨 (체크박스 선택가능)
	 	-	작성자 : 업무 작성자 기준 (체크박스 선택가능)
	 	-	팔로워 : 내 업무에 팔로우 된 멤버별 선택가능 (체크박스)
	 */
	private	int wrk_dt;					//	작성일 기준 며칠 이내의 업무인지
	private String wrk_is_mine;			//	내 업무 / 전체 업무 구분자
										//	-- 업무 주체 --
	private	String wrk_i_assigned;		//	내게 할당된 업무	
	private	String wrk_i_made;			//	내가 작성한 업무
	private	String wrk_i_following;		//	내가 팔로우한 업무
	private List<Integer> prj_id_list;	//	프로젝트 아이디 목록
										//	-- 마감일 기준 --
	private String overdue;				//	마감일 지남
	private String till_this_week;		//	이번 주까지
	private String till_this_month;		//	이번 달까지
	private String no_deadline;			//	마감일 없음
	private String td_month;			//	이번 달을 표시하는 변수
	private String td_week;				//	이번 주를 표시하는 변수
										//	-- 업무 상태 --
	private String is_cmp;				//	완료된 업무
	private String is_del;				//	삭제된 업무
										
	private List<String> wrk_maker;		//	업무 작성자
	private List<String> wrk_follower;	//	업무 팔로워 기준
	
	private String user_email;			//	내 이메일 
	private String is_cal;				//	캘린더용 자료 확인값(시작일 / 마감일이 있는 자료만 검색)
	
	public int getWrk_dt() {
		return wrk_dt;
	}
	public void setWrk_dt(int wrk_dt) {
		this.wrk_dt = wrk_dt;
	}
	public String getWrk_is_mine() {
		return wrk_is_mine;
	}
	public void setWrk_is_mine(String wrk_is_mine) {
		this.wrk_is_mine = wrk_is_mine;
	}
	public List<String> getWrk_maker() {
		return wrk_maker;
	}
	public void setWrk_maker(List<String> wrk_maker) {
		this.wrk_maker = wrk_maker;
	}
	public List<String> getWrk_follower() {
		return wrk_follower;
	}
	public void setWrk_follower(List<String> wrk_follower) {
		this.wrk_follower = wrk_follower;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getWrk_i_assigned() {
		return wrk_i_assigned;
	}
	public void setWrk_i_assigned(String wrk_i_assigned) {
		this.wrk_i_assigned = wrk_i_assigned;
	}
	public String getWrk_i_made() {
		return wrk_i_made;
	}
	public void setWrk_i_made(String wrk_i_made) {
		this.wrk_i_made = wrk_i_made;
	}
	public String getWrk_i_following() {
		return wrk_i_following;
	}
	public void setWrk_i_following(String wrk_i_following) {
		this.wrk_i_following = wrk_i_following;
	}
	public List<Integer> getPrj_id_list() {
		return prj_id_list;
	}
	public void setPrj_id_list(List<Integer> prj_id_list) {
		this.prj_id_list = prj_id_list;
	}
	public String getOverdue() {
		return overdue;
	}
	public void setOverdue(String overdue) {
		this.overdue = overdue;
	}
	public String getTill_this_week() {
		return till_this_week;
	}
	public void setTill_this_week(String till_this_week) {
		this.till_this_week = till_this_week;
	}
	public String getTill_this_month() {
		return till_this_month;
	}
	public void setTill_this_month(String till_this_month) {
		this.till_this_month = till_this_month;
	}
	public String getNo_deadline() {
		return no_deadline;
	}
	public void setNo_deadline(String no_deadline) {
		this.no_deadline = no_deadline;
	}
	public String getIs_cmp() {
		return is_cmp;
	}
	public void setIs_cmp(String is_cmp) {
		this.is_cmp = is_cmp;
	}
	public String getIs_del() {
		return is_del;
	}
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}
	public String getTd_month() {
		return td_month;
	}
	public void setTd_month(String td_month) {
		this.td_month = td_month;
	}
	public String getTd_week() {
		return td_week;
	}
	public void setTd_week(String td_week) {
		this.td_week = td_week;
	}
	public String getIs_cal() {
		return is_cal;
	}
	public void setIs_cal(String is_cal) {
		this.is_cal = is_cal;
	}
	@Override
	public String toString() {
		return "FilterVo [wrk_dt=" + wrk_dt + ", wrk_is_mine=" + wrk_is_mine + ", wrk_i_assigned=" + wrk_i_assigned
				+ ", wrk_i_made=" + wrk_i_made + ", wrk_i_following=" + wrk_i_following + ", prj_id_list=" + prj_id_list
				+ ", overdue=" + overdue + ", till_this_week=" + till_this_week + ", till_this_month=" + till_this_month
				+ ", no_deadline=" + no_deadline + ", td_month=" + td_month + ", td_week=" + td_week + ", is_cmp="
				+ is_cmp + ", is_del=" + is_del + ", wrk_maker=" + wrk_maker + ", wrk_follower=" + wrk_follower
				+ ", user_email=" + user_email + ", is_cal=" + is_cal + "]";
	}
	
}
