package kr.or.ddit.ad_board.model;

import java.util.Date;
/**
 * 
* Ad_BoardVo.java
*
* @author 김경호
* @version 1.0
* @see
* Ad_Board - 광고 배너
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
public class Ad_BoardVo {
	
	private int ad_id; // 광고 아이디
	private String user_email; // 이메일
	private String ad_email; // 광고주
	private String ad_subject; // 광고 제목
	private String ad_content; // 광고 설명
	private Date ad_start_date; // 광고 시작 일자
	private Date ad_end_date; // 광고 종료 일자
	private Date ad_date; // 광고 등록일
	private int ad_view; // 광고 노출수
	private int ad_cnt; // 광고 조회수
	private String ad_filename; // 광고 사진 파일명
	private String ad_path; // 광고 사진 경로
	private String del_fl; // 광고 삭제 여부
	
	public Ad_BoardVo() {

	}

	public Ad_BoardVo(int ad_id, String user_email, String ad_email, String ad_subject, String ad_content,
			Date ad_start_date, Date ad_end_date, Date ad_date, int ad_view, int ad_cnt, String ad_filename,
			String ad_path, String del_fl) {
		super();
		this.ad_id = ad_id;
		this.user_email = user_email;
		this.ad_email = ad_email;
		this.ad_subject = ad_subject;
		this.ad_content = ad_content;
		this.ad_start_date = ad_start_date;
		this.ad_end_date = ad_end_date;
		this.ad_date = ad_date;
		this.ad_view = ad_view;
		this.ad_cnt = ad_cnt;
		this.ad_filename = ad_filename;
		this.ad_path = ad_path;
		this.del_fl = del_fl;
	}

	@Override
	public String toString() {
		return "Ad_BoardVo [ad_id=" + ad_id + ", user_email=" + user_email + ", ad_email=" + ad_email + ", ad_subject="
				+ ad_subject + ", ad_content=" + ad_content + ", ad_start_date=" + ad_start_date + ", ad_end_date="
				+ ad_end_date + ", ad_date=" + ad_date + ", ad_view=" + ad_view + ", ad_cnt=" + ad_cnt
				+ ", ad_filename=" + ad_filename + ", ad_path=" + ad_path + ", del_fl=" + del_fl + "]";
	}

	public int getAd_id() {
		return ad_id;
	}

	public void setAd_id(int ad_id) {
		this.ad_id = ad_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getAd_email() {
		return ad_email;
	}

	public void setAd_email(String ad_email) {
		this.ad_email = ad_email;
	}

	public String getAd_subject() {
		return ad_subject;
	}

	public void setAd_subject(String ad_subject) {
		this.ad_subject = ad_subject;
	}

	public String getAd_content() {
		return ad_content;
	}

	public void setAd_content(String ad_content) {
		this.ad_content = ad_content;
	}

	public Date getAd_start_date() {
		return ad_start_date;
	}

	public void setAd_start_date(Date ad_start_date) {
		this.ad_start_date = ad_start_date;
	}

	public Date getAd_end_date() {
		return ad_end_date;
	}

	public void setAd_end_date(Date ad_end_date) {
		this.ad_end_date = ad_end_date;
	}

	public Date getAd_date() {
		return ad_date;
	}

	public void setAd_date(Date ad_date) {
		this.ad_date = ad_date;
	}

	public int getAd_view() {
		return ad_view;
	}

	public void setAd_view(int ad_view) {
		this.ad_view = ad_view;
	}

	public int getAd_cnt() {
		return ad_cnt;
	}

	public void setAd_cnt(int ad_cnt) {
		this.ad_cnt = ad_cnt;
	}

	public String getAd_filename() {
		return ad_filename;
	}

	public void setAd_filename(String ad_filename) {
		this.ad_filename = ad_filename;
	}

	public String getAd_path() {
		return ad_path;
	}

	public void setAd_path(String ad_path) {
		this.ad_path = ad_path;
	}

	public String getDel_fl() {
		return del_fl;
	}

	public void setDel_fl(String del_fl) {
		this.del_fl = del_fl;
	}
	
}
