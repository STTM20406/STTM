package kr.or.ddit.calendar.model;

public class CalendarVo {
	 
	String _id; //wrk_id
	String title; //wrk_nm
	String start; //wrk_start_dt
	String end; //wrk_start_dt
	String type; //wrk_lst_nm
	String username; //user_nm
	String backgroundColor; //wrk_color_cd
	
	CalendarVo(){
		
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
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

	@Override
	public String toString() {
		return "CalendarVo [_id=" + _id + ", title=" + title + ", start=" + start + ", end=" + end + ", type=" + type
				+ ", username=" + username + ", textColor=" + backgroundColor + "]";
	}
	
	
}
