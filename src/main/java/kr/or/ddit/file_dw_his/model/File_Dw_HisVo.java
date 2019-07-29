package kr.or.ddit.file_dw_his.model;

import java.util.Date;

/**
 * 
* File_Dw_HisVo.java
*
* @author 김경호
* @version 1.0
* @see
* File_Dw_His - 파일 다운로드 이력
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
public class File_Dw_HisVo {
	
	private int down_id; // 파일 다운로드 아이디
	private int prj_id; // 프로젝트 아이디
	private String user_email; // 디운로드 한 이메일
	private int file_id; // 파일 아이디
	private Date down_date; // 다운로드 일시
	
	public File_Dw_HisVo() {

	}

	public File_Dw_HisVo(int down_id, int prj_id, String user_email, int file_id, Date down_date) {
		super();
		this.down_id = down_id;
		this.prj_id = prj_id;
		this.user_email = user_email;
		this.file_id = file_id;
		this.down_date = down_date;
	}

	@Override
	public String toString() {
		return "File_Dw_HisVo [down_id=" + down_id + ", prj_id=" + prj_id + ", user_email=" + user_email + ", file_id="
				+ file_id + ", down_date=" + down_date + "]";
	}

	public int getDown_id() {
		return down_id;
	}

	public void setDown_id(int down_id) {
		this.down_id = down_id;
	}

	public int getPrj_id() {
		return prj_id;
	}

	public void setPrj_id(int prj_id) {
		this.prj_id = prj_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public int getFile_id() {
		return file_id;
	}

	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}

	public Date getDown_date() {
		return down_date;
	}

	public void setDown_date(Date down_date) {
		this.down_date = down_date;
	}
	
}
