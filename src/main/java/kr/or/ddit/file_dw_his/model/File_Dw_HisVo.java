package kr.or.ddit.file_dw_his.model;

import java.text.SimpleDateFormat;
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
	
	private int num; //번호
	private String user_nm; //유저 이름
	private String wrk_nm; //업무이름
	private String original_file_nm; // 실제 파일이름
	
	
	public File_Dw_HisVo() {

	}

	public File_Dw_HisVo(int down_id, int prj_id, String user_email, int file_id, Date down_date) {
		this.down_id = down_id;
		this.prj_id = prj_id;
		this.user_email = user_email;
		this.file_id = file_id;
		this.down_date = down_date;
	}
	
	 public String getPrjStartDtStr() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if(down_date == null) {
				return "";
			}
			return sdf.format(down_date);
	}

	@Override
	public String toString() {
		return "File_Dw_HisVo [down_id=" + down_id + ", prj_id=" + prj_id + ", user_email=" + user_email + ", file_id="
				+ file_id + ", down_date=" + down_date + ", num=" + num + ", user_nm=" + user_nm + ", wrk_nm=" + wrk_nm
				+ ", original_file_nm=" + original_file_nm + "]";
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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

	public String getWrk_nm() {
		return wrk_nm;
	}

	public void setWrk_nm(String wrk_nm) {
		this.wrk_nm = wrk_nm;
	}

	public String getOriginal_file_nm() {
		return original_file_nm;
	}

	public void setOriginal_file_nm(String original_file_nm) {
		this.original_file_nm = original_file_nm;
	}
}
