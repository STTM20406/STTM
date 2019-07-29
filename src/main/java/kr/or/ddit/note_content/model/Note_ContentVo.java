package kr.or.ddit.note_content.model;

public class Note_ContentVo {
	
	private int note_con_id; // 쪽지 내용 아이디
	private int note_id; // 쪽지 아이디
	private String note_con; // 쪽지 내용
	
	public Note_ContentVo() {

	}

	public Note_ContentVo(int note_con_id, int note_id, String note_con) {
		super();
		this.note_con_id = note_con_id;
		this.note_id = note_id;
		this.note_con = note_con;
	}

	@Override
	public String toString() {
		return "Note_ContentVo [note_con_id=" + note_con_id + ", note_id=" + note_id + ", note_con=" + note_con + "]";
	}

	public int getNote_con_id() {
		return note_con_id;
	}

	public void setNote_con_id(int note_con_id) {
		this.note_con_id = note_con_id;
	}

	public int getNote_id() {
		return note_id;
	}

	public void setNote_id(int note_id) {
		this.note_id = note_id;
	}

	public String getNote_con() {
		return note_con;
	}

	public void setNote_con(String note_con) {
		this.note_con = note_con;
	}
	
}
