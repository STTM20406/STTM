package kr.or.ddit.memo.model;

import java.util.Date;

/**
 * 
* MemoVo.java
*
* @author 김경호
* @version 1.0
* @see
* Memo - 메모
*
* <pre>
* << 개정이력(Modification Information) >>
*
* 수정자 수정내용
* ------ ------------------------
* 김경호 최초 생성
*
* </pre>
 */
public class MemoVo {
	
	private int memo_id; // 메모 아이디
	private int prj_id; // 프로젝트 아이디
	private String memo_email; // 메모 작성자
	private String memo_con; // 메모 내용
	private String memo_date; // 메모ㅗ 작성일
	private Date memo_update; // 메모 업데이트 일시
	private String memo_del_fl; // 메모 삭제 여부
	
	public MemoVo() {

	}

	public MemoVo(int memo_id, int prj_id, String memo_email, String memo_con, String memo_date, Date memo_update,
			String memo_del_fl) {
		super();
		this.memo_id = memo_id;
		this.prj_id = prj_id;
		this.memo_email = memo_email;
		this.memo_con = memo_con;
		this.memo_date = memo_date;
		this.memo_update = memo_update;
		this.memo_del_fl = memo_del_fl;
	}

	@Override
	public String toString() {
		return "MemoVo [memo_id=" + memo_id + ", prj_id=" + prj_id + ", memo_email=" + memo_email + ", memo_con="
				+ memo_con + ", memo_date=" + memo_date + ", memo_update=" + memo_update + ", memo_del_fl="
				+ memo_del_fl + "]";
	}

	public int getMemo_id() {
		return memo_id;
	}

	public void setMemo_id(int memo_id) {
		this.memo_id = memo_id;
	}

	public int getPrj_id() {
		return prj_id;
	}

	public void setPrj_id(int prj_id) {
		this.prj_id = prj_id;
	}

	public String getMemo_email() {
		return memo_email;
	}

	public void setMemo_email(String memo_email) {
		this.memo_email = memo_email;
	}

	public String getMemo_con() {
		return memo_con;
	}

	public void setMemo_con(String memo_con) {
		this.memo_con = memo_con;
	}

	public String getMemo_date() {
		return memo_date;
	}

	public void setMemo_date(String memo_date) {
		this.memo_date = memo_date;
	}

	public Date getMemo_update() {
		return memo_update;
	}

	public void setMemo_update(Date memo_update) {
		this.memo_update = memo_update;
	}

	public String getMemo_del_fl() {
		return memo_del_fl;
	}

	public void setMemo_del_fl(String memo_del_fl) {
		this.memo_del_fl = memo_del_fl;
	}
	
}
