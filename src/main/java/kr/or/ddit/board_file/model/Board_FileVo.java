package kr.or.ddit.board_file.model;

import java.util.Date;

/**
 * 
* Board_FileVo.java
*
* @author 김경호
* @version 1.0
* @see
* Board_File - 게시판 첨부 파일
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
public class Board_FileVo {
	
	private int file_id; // 첨부파일 아이디
	private int write_id; // 게시글 아이디
	private String original_file_nm; // 실제 파일명
	private String db_file_nm; // DB 파일명
	private String file_size; // 파일 크기
	private Date file_dt; // 등록일
	private String del_fl; // 삭제여부
	
	public Board_FileVo() {

	}

	public Board_FileVo(int file_id, int write_id, String original_file_nm, String db_file_nm, String file_size,
			Date file_dt, String del_fl) {
		super();
		this.file_id = file_id;
		this.write_id = write_id;
		this.original_file_nm = original_file_nm;
		this.db_file_nm = db_file_nm;
		this.file_size = file_size;
		this.file_dt = file_dt;
		this.del_fl = del_fl;
	}

	@Override
	public String toString() {
		return "Board_FileVo [file_id=" + file_id + ", write_id=" + write_id + ", original_file_nm=" + original_file_nm
				+ ", db_file_nm=" + db_file_nm + ", file_size=" + file_size + ", file_dt=" + file_dt + ", del_fl="
				+ del_fl + "]";
	}

	public int getFile_id() {
		return file_id;
	}

	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}

	public int getWrite_id() {
		return write_id;
	}

	public void setWrite_id(int write_id) {
		this.write_id = write_id;
	}

	public String getOriginal_file_nm() {
		return original_file_nm;
	}

	public void setOriginal_file_nm(String original_file_nm) {
		this.original_file_nm = original_file_nm;
	}

	public String getDb_file_nm() {
		return db_file_nm;
	}

	public void setDb_file_nm(String db_file_nm) {
		this.db_file_nm = db_file_nm;
	}

	public String getFile_size() {
		return file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public Date getFile_dt() {
		return file_dt;
	}

	public void setFile_dt(Date file_dt) {
		this.file_dt = file_dt;
	}

	public String getDel_fl() {
		return del_fl;
	}

	public void setDel_fl(String del_fl) {
		this.del_fl = del_fl;
	}
	
}
