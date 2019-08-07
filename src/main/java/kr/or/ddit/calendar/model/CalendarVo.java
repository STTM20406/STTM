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
		return " { _id=" + _id + ", title=" + title + ", start=" + start + ", end=" + end + ", description="
				+ description + ", type=" + type + ", username=" + username + ", backgroundColor=" + backgroundColor
				+ ", textColor=" + textColor + ", allDay=" + allDay + "}";
	}
	
}
