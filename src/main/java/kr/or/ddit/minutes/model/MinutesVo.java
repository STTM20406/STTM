package kr.or.ddit.minutes.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * MinutesVo.java
 *
 * @author 박서경
 * @version 1.0
 * @see
 * MINUTES - 회의록 게시판
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
public class MinutesVo {
	
	private int mnu_id;				//회의록 게시글 ID
	private int prj_id;             //프로젝트 ID
	private String user_email;      //이메일
	private String subject;         //회의내용
	private String special;         //특이사항
	private Date write_date;        //작성일시
	private String del_fl;          //삭제여부
	
	private String user_nm; //사용자 이름
	private int rn; //레벨
	
	//기본생성자
	public MinutesVo() {
		
	}
	
	public String getPrjStartDtStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(write_date == null) {
			return "";
		}
		return sdf.format(write_date);
	}
	
	/**
	 * @param prj_id
	 * @param user_email
	 * @param subject
	 * @param special
	 * @param user_nm
	 * 회의록 등록
	 */
	public MinutesVo(int prj_id, String user_email, String subject, String special) {
		this.prj_id = prj_id;
		this.user_email = user_email;
		this.subject = subject;
		this.special = special;
	}
	
	//getter, setter
	public int getMnu_id() {
		return mnu_id;
	}

	public void setMnu_id(int mnu_id) {
		this.mnu_id = mnu_id;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public Date getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}

	public String getDel_fl() {
		return del_fl;
	}

	public void setDel_fl(String del_fl) {
		this.del_fl = del_fl;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public int getRn() {
		return rn;
	}

	public void setRn(int rn) {
		this.rn = rn;
	}

	@Override
	public String toString() {
		return "MinutesVo [mnu_id=" + mnu_id + ", prj_id=" + prj_id + ", user_email=" + user_email + ", subject="
				+ subject + ", special=" + special + ", write_date=" + write_date + ", del_fl=" + del_fl + ", user_nm="
				+ user_nm + ", rn=" + rn + "]";
	}
	
}
