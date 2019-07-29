package kr.or.ddit.link_attch.model;

import java.util.Date;

/**
 * 
 * Link_attchVo.java
 *
 * @author 박서경
 * @version 1.0
 * @see
 * LINK_ATTCH - 첨부링크
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
public class Link_attchVo {
	
	private int link_id;			//링크 ID
	private int prj_id;             //프로젝트 ID
	private String user_email;      //이메일
	private int wrk_id;             //업무 ID
	private String attch_url;       //첨부 URL
	private Date file_link_dt;    	//등록일
	private String del_fl;          //삭제 여부
	private String wrk_nm; 			//업무명
	
	//기본생성자
	public Link_attchVo() {
		
	}
	
	
	//생성자
	public Link_attchVo(int prj_id, String user_email, int wrk_id, String attch_url) {
		super();
		this.prj_id = prj_id;
		this.user_email = user_email;
		this.wrk_id = wrk_id;
		this.attch_url = attch_url;
	}

	//getter, setter
	public int getLink_id() {
		return link_id;
	}

	public void setLink_id(int link_id) {
		this.link_id = link_id;
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

	public String getAttch_url() {
		return attch_url;
	}

	public void setAttch_url(String attch_url) {
		this.attch_url = attch_url;
	}

	public Date getFile_link_dt() {
		return file_link_dt;
	}

	public void setFile_link_dt(Date file_link_dt) {
		this.file_link_dt = file_link_dt;
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

	//toString
	@Override
	public String toString() {
		return "Link_attchVo [link_id=" + link_id + ", prj_id=" + prj_id + ", user_email=" + user_email + ", wrk_id="
				+ wrk_id + ", attch_url=" + attch_url + ", file_link_dt=" + file_link_dt + ", del_fl=" + del_fl
				+ ", wrk_nm=" + wrk_nm + "]";
	}

}
