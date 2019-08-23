package kr.or.ddit.calendar.model;

public class CalendarVo {
	 
	private Integer _id; //wrk_id
	private String title; //wrk_nm
	private String start; //wrk_start_dt
	private String end; //wrk_start_dt
	private String description; //prj_nm 
	private String type; //wrk_lst_nm
	private String username; //user_nm
	private String backgroundColor; //wrk_color_cd
	private String textColor = "#ffffff"; 
	private boolean allDay; 
	
	//특정 업무의 프로젝트 리스트와 업무 리스트 명 받아오기 위한 
	private String prj_id;
	private String prj_nm;
	private String wrk_lst_id;
	private String wrk_lst_nm;
	
	public String getPrj_id() {
		return prj_id;
	}
	public void setPrj_id(String prj_id) {
		this.prj_id = prj_id;
	}
	public String getPrj_nm() {
		return prj_nm;
	}
	public void setPrj_nm(String prj_nm) {
		this.prj_nm = prj_nm;
	}
	public String getWrk_lst_id() {
		return wrk_lst_id;
	}
	public void setWrk_lst_id(String wrk_lst_id) {
		this.wrk_lst_id = wrk_lst_id;
	}
	public String getWrk_lst_nm() {
		return wrk_lst_nm;
	}
	public void setWrk_lst_nm(String wrk_lst_nm) {
		this.wrk_lst_nm = wrk_lst_nm;
	}
	public CalendarVo(){
		
	}
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getTextColor() {
		return textColor;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	@Override
	public String toString() {
		return "CalendarVo [_id=" + _id + ", title=" + title + ", start=" + start + ", end=" + end + ", description="
				+ description + ", type=" + type + ", username=" + username + ", backgroundColor=" + backgroundColor
				+ ", textColor=" + textColor + ", allDay=" + allDay + ", prj_id=" + prj_id + ", prj_nm=" + prj_nm
				+ ", wrk_lst_id=" + wrk_lst_id + ", wrk_lst_nm=" + wrk_lst_nm + "]";
	}

	
}
