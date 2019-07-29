package kr.or.ddit.chat_file.model;

import java.util.Date;

/**
 * 
* Chat_FileVo.java
*
* @author 김경호
* @version 1.0
* @see
* Chat_File - 채팅 파일
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
public class Chat_FileVo {
	
	private int ct_file_id; // 파일 아이디
	private int ct_con_id; // 채팅 내용 아이디
	private String original_file_name; // 실제 파일명
	private String db_file_name; // DB 파일명
	private String file_size; // 파일 크기
	private Date file_date; // 파일 등록일
	
	public Chat_FileVo() {

	}

	public Chat_FileVo(int ct_file_id, int ct_con_id, String original_file_name, String db_file_name, String file_size,
			Date file_date) {
		super();
		this.ct_file_id = ct_file_id;
		this.ct_con_id = ct_con_id;
		this.original_file_name = original_file_name;
		this.db_file_name = db_file_name;
		this.file_size = file_size;
		this.file_date = file_date;
	}

	@Override
	public String toString() {
		return "Chat_FileVo [ct_file_id=" + ct_file_id + ", ct_con_id=" + ct_con_id + ", original_file_name="
				+ original_file_name + ", db_file_name=" + db_file_name + ", file_size=" + file_size + ", file_date="
				+ file_date + "]";
	}

	public int getCt_file_id() {
		return ct_file_id;
	}

	public void setCt_file_id(int ct_file_id) {
		this.ct_file_id = ct_file_id;
	}

	public int getCt_con_id() {
		return ct_con_id;
	}

	public void setCt_con_id(int ct_con_id) {
		this.ct_con_id = ct_con_id;
	}

	public String getOriginal_file_name() {
		return original_file_name;
	}

	public void setOriginal_file_name(String original_file_name) {
		this.original_file_name = original_file_name;
	}

	public String getDb_file_name() {
		return db_file_name;
	}

	public void setDb_file_name(String db_file_name) {
		this.db_file_name = db_file_name;
	}

	public String getFile_size() {
		return file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public Date getFile_date() {
		return file_date;
	}

	public void setFile_date(Date file_date) {
		this.file_date = file_date;
	}
	
}
