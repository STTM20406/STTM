package kr.or.ddit.work_al_mem.model;

/**
 * Work_Al_MemVo.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 * WORK_AL_MEM - 업무 알림 멤버
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-19
 *
 * </pre>
 */
public class Work_Al_MemVo {

	private int wrk_rv_mem_id;	// 업무 알림 멤버 ID
	private int prj_id;			// 프로젝트 ID
	private int wrk_rv_id;		// 업무 예약 알림 ID
	private String user_email;	// 이메일
	
	
	
	public Work_Al_MemVo() {

	}
	
	@Override
	public String toString() {
		return "Work_Al_MemVo [wrk_rv_mem_id=" + wrk_rv_mem_id + ", prj_id=" + prj_id + ", wrk_rv_id=" + wrk_rv_id
				+ ", user_email=" + user_email + "]";
	}
	public int getWrk_rv_mem_id() {
		return wrk_rv_mem_id;
	}
	public void setWrk_rv_mem_id(int wrk_rv_mem_id) {
		this.wrk_rv_mem_id = wrk_rv_mem_id;
	}
	public int getPrj_id() {
		return prj_id;
	}
	public void setPrj_id(int prj_id) {
		this.prj_id = prj_id;
	}
	public int getWrk_rv_id() {
		return wrk_rv_id;
	}
	public void setWrk_rv_id(int wrk_rv_id) {
		this.wrk_rv_id = wrk_rv_id;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	
	
}
