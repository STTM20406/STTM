package kr.or.ddit.ganttChart.model;

/**
 * GanttChartVo.java
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
 * 유승진 2019-07-31 최초 생성
 *
 * </pre>
 */
public class GanttChartVo {
	private String id;				//간트차트 데이터 id = prj_id, wrk_lst_id, wrk_id
	private String text;			//간트차트 데이터 제목 = prj_nm, wrk_lst_nm, wrk_nm
	private String start_date;		//간트차트 데이터 시작일 = wrk_start_dt
	private String end_date;		//간트차트 데이터 마감일 = wrk_end_dt
	private Boolean unscheduled;	//간트차트 데이터 시작 / 마감일 존재여부
	private String parent;			//간트차트 데이터 부모 개체 id 업무 - 업무리스트 - 프로젝트
	private Boolean open;			//간트차트 데이터 개체 로드시 하위개체 표시 여부
	private Integer duration;		//간트차트 데이터 일정 길이
	private Integer progress;		//간트차트 진행도 표시 (WRK_CMP_FL에 따라 0 또는 100)
	private String color;			//간트차트 색깔 코드
	private Boolean readonly;		//간트차트 업데이트 가능 여부
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getUnscheduled() {
		return unscheduled;
	}
	public void setUnscheduled(Boolean unscheduled) {
		this.unscheduled = unscheduled;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Boolean getReadonly() {
		return readonly;
	}
	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}
	
	@Override
	public String toString() {
		return "{" + (id != null ? "id=" + id + ", " : "") + (text != null ? "text=" + text + ", " : "")
				+ (start_date != null ? "start_date=" + start_date + ", " : "")
				+ (end_date != null ? "end_date=" + end_date + ", " : "")
				+ (unscheduled != null ? "unscheduled=" + unscheduled + ", " : "")
				+ (parent != null ? "parent=" + parent + ", " : "") + (open != null ? "open=" + open + ", " : "")
				+ (duration != null ? "duration=" + duration + ", " : "")
				+ (progress != null ? "progress=" + progress + ", " : "")
				+ (color != null ? "color=" + color + ", " : "") + (readonly != null ? "readonly=" + readonly : "")
				+ "}";
	}
	
}
