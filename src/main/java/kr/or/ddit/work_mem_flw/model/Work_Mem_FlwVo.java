package kr.or.ddit.work_mem_flw.model;

/**
 * Work_Mem_FlwVo.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 * WORK_MEM_FLW - 업무멤버_팔로워
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-19
 *
 * </pre>
 */
public class Work_Mem_FlwVo {

	private String user_email;	// 업무멤버 이메일
	private int prj_id;			// 프로젝트 ID
	private int wrk_id;			// 업무 ID
	private String jn_fl;		// 참여구분
	
	private String user_nm;		//업무멤버 이름
	
	
	public Work_Mem_FlwVo() {
		
	}
	
	
	/**
	 * 업무 멤버/팔로워 리스트 조회
	 * @author 박서경
	 * @param wrk_id
	 * @param jn_fl
	 */
	public Work_Mem_FlwVo(int wrk_id, String jn_fl) {
		super();
		this.wrk_id = wrk_id;
		this.jn_fl = jn_fl;
	}
	
	/**
	 * 업무 멤버/팔로워 삭제
	 * @author 박서경
	 * @param user_email
	 * @param wrk_id
	 */
	public Work_Mem_FlwVo(String user_email, int wrk_id) {
		super();
		this.user_email = user_email;
		this.wrk_id = wrk_id;
	}


	@Override
	public String toString() {
		return "Work_Mem_FlwVo [user_email=" + user_email + ", prj_id=" + prj_id + ", wrk_id=" + wrk_id
				+ ", jn_fl=" + jn_fl + ", user_nm=" + user_nm + "]";
	}

	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public int getPrj_id() {
		return prj_id;
	}
	public void setPrj_id(int prj_id) {
		this.prj_id = prj_id;
	}
	public int getWrk_id() {
		return wrk_id;
	}
	public void setWrk_id(int wrk_id) {
		this.wrk_id = wrk_id;
	}
	public String getJn_fl() {
		return jn_fl;
	}
	public void setJn_fl(String jn_fl) {
		this.jn_fl = jn_fl;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	
	
}
