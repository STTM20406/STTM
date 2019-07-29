package kr.or.ddit.mail_confirm.model;

import java.util.Date;

/**
 * 
 * Mail_ConfirmVo.java
 *
 * @author 박서경
 * @version 1.0
 * @see
 * MAIL_CONFIRM - 메일인증
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
public class Mail_ConfirmVo {
	
	private int ml_cf_id;				//메일 ID
	private String user_email;       	//이메일
	private String type;       			//인증 타입
	private String token;       		//인증 토큰
	private Date generate_date;       	//인증키 생성일자
	private Date use_date;       		//인증키 사용일자
	private String expired_flag;       	//인증기 파기 여부
	
	
	//기본생성자
	public Mail_ConfirmVo() {

	}
	
	
	//생성자

	
	
	//getter, setter
	public int getMl_cf_id() {
		return ml_cf_id;
	}


	public void setMl_cf_id(int ml_cf_id) {
		this.ml_cf_id = ml_cf_id;
	}


	public String getUser_email() {
		return user_email;
	}


	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public Date getGenerate_date() {
		return generate_date;
	}


	public void setGenerate_date(Date generate_date) {
		this.generate_date = generate_date;
	}


	public Date getUse_date() {
		return use_date;
	}


	public void setUse_date(Date use_date) {
		this.use_date = use_date;
	}


	public String getExpired_flag() {
		return expired_flag;
	}


	public void setExpired_flag(String expired_flag) {
		this.expired_flag = expired_flag;
	}


	//toString
	@Override
	public String toString() {
		return "Mail_ConfirmVo [ml_cf_id=" + ml_cf_id + ", user_email=" + user_email + ", type=" + type + ", token="
				+ token + ", generate_date=" + generate_date + ", use_date=" + use_date + ", expired_flag="
				+ expired_flag + "]";
	}
	
	
}
