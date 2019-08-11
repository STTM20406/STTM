package kr.or.ddit.minutes.model;

/**
 * Minutes_MemVo.java
 *
 * @author 손영하
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 박서경   최초 생성 2019-08-08
 *
 * </pre>
 */
public class Minutes_MemVo {

	int mnu_mem_id;     //참석자 ID
	int	mnu_id;         //회의록 게시글 ID
	String	user_email; //참석자 이메일
	String user_nm;
	
	public Minutes_MemVo(){
		
	}
	
	public int getMnu_mem_id() {
		return mnu_mem_id;
	}

	public void setMnu_mem_id(int mnu_mem_id) {
		this.mnu_mem_id = mnu_mem_id;
	}

	public int getMnu_id() {
		return mnu_id;
	}

	public void setMnu_id(int mnu_id) {
		this.mnu_id = mnu_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	@Override
	public String toString() {
		return "Minutes_MemVo [mnu_mem_id=" + mnu_mem_id + ", mnu_id=" + mnu_id + ", user_email=" + user_email
				+ ", user_nm=" + user_nm + "]";
	}

	

	
}
