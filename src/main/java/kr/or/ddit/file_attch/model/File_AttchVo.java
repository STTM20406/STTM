package kr.or.ddit.file_attch.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * File_AttchVo.java
 *
 * @author 박서경
 * @version 1.0
 * @see
 * FILE_ATTCH - 첨부파일
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정자  수정내용
 * ------ ------------------------
 *  박서경  최초 생성 2019-07-19
 *
 * </pre>
 */
public class File_AttchVo {
	
	private int file_id;				//파일 ID					
    private int prj_id;                 //프로젝트 ID
    private String user_email;          //이메일
    private int wrk_id;                 //업무 ID
    private String original_file_nm;    //실제 파일명
    private String db_file_nm;          //DB 파일명
    private long file_size;           	//파일 크기
    private String file_exts;           //파일 확장자
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date file_dt;             	//등록일
    private String file_save_fl;		//파일 저장 구분  공유함(PU), 개인(IN)
    private String del_fl;              //삭제여부
    private String file_comm_fl;        //삭제여부
    
    private String wrk_nm; 				//업무명
    private String user_nm;				//작성자 이름
    private int num;
    
    //기본 생성자
    public File_AttchVo() {
    	
	}
    
    public String getPrjStartDtStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(file_dt == null) {
			return "";
		}
		return sdf.format(file_dt);
	}
    
    //생성자
	/**
	 * @param prj_id
	 * @param user_email
	 * @param wrk_id
	 * @param original_file_nm
	 * @param db_file_nm
	 * @param file_size
	 * @param file_exts
	 * file 업로드
	 */
	public File_AttchVo(int prj_id, String user_email, int wrk_id, String original_file_nm, String db_file_nm,
			long file_size, String file_exts) {
		this.prj_id = prj_id;
		this.user_email = user_email;
		this.wrk_id = wrk_id;
		this.original_file_nm = original_file_nm;
		this.db_file_nm = db_file_nm;
		this.file_size = file_size;
		this.file_exts = file_exts;
	}
	
	//getter, setter
	
	public int getFile_id() {
		return file_id;
	}

	public String getFile_comm_fl() {
		return file_comm_fl;
	}

	public void setFile_comm_fl(String file_comm_fl) {
		this.file_comm_fl = file_comm_fl;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public void setFile_id(int file_id) {
		this.file_id = file_id;
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

	public int getWrk_id() {
		return wrk_id;
	}

	public void setWrk_id(int wrk_id) {
		this.wrk_id = wrk_id;
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

	public long getFile_size() {
		return file_size;
	}

	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}

	public String getFile_exts() {
		return file_exts;
	}

	public void setFile_exts(String file_exts) {
		this.file_exts = file_exts;
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

	public String getWrk_nm() {
		return wrk_nm;
	}

	public void setWrk_nm(String wrk_nm) {
		this.wrk_nm = wrk_nm;
	}

	public String getFile_save_fl() {
		return file_save_fl;
	}

	public void setFile_save_fl(String file_save_fl) {
		this.file_save_fl = file_save_fl;
	}

	@Override
	public String toString() {
		return "File_AttchVo [file_id=" + file_id + ", prj_id=" + prj_id + ", user_email=" + user_email + ", wrk_id="
				+ wrk_id + ", original_file_nm=" + original_file_nm + ", db_file_nm=" + db_file_nm + ", file_size="
				+ file_size + ", file_exts=" + file_exts + ", file_dt=" + file_dt + ", file_save_fl=" + file_save_fl
				+ ", del_fl=" + del_fl + ", file_comm_fl=" + file_comm_fl + ", wrk_nm=" + wrk_nm + ", user_nm="
				+ user_nm + ", num=" + num + "]";
	}
	
} 