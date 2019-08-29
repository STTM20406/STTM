package kr.or.ddit.project_mem.model;

/**
 * Project_MemVo.java
 *
 * @author 양한솔
 * @version 1.0
 * @see
 * PROJECT_MEM - 프로젝트멤버
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 양한솔   최초 생성 : 2019-07-19
 * 박서경   수정 일시 : 2019-08-03
 * 박서경	수정 일시 : 2019-08-26
 *
 * </pre>
 */
public class Project_MemVo {

	private int prj_id;				// 프로젝트 ID
	private String user_email;		// 프로젝트 멤버 이메일
	private String prj_mem_lv;		// 프로젝트 멤버 레벨
	private String prj_mem_nik;		// 프로젝트 멤버 닉네임
	private String prj_own_fl;		// 프로젝트 소유 유무
	private String prj_mem_del_fl; 	// 프로젝트 멤버 삭제 여부
	
	private String user_nm;		// 프로젝트 멤버 이메일에 해당하는 이름
	private String prj_nm;      // 프로젝트 이름
	
	private String frd_email;   // 포로젝트 친구 이메일
	
	public Project_MemVo() {
		
	}

	public Project_MemVo(int prj_id, String user_email, String prj_mem_lv, String prj_mem_nik, String prj_own_fl) {
		super();
		this.prj_id = prj_id;
		this.user_email = user_email;
		this.prj_mem_lv = prj_mem_lv;
		this.prj_mem_nik = prj_mem_nik;
		this.prj_own_fl = prj_own_fl;
	}
	
	/**
	 * @param prj_id
	 * @param user_email
	 * 현재 접속한 사용자의 프로젝트 멤버 정보를 조회 하기 위한 생성자
	 */
	public Project_MemVo(int prj_id, String user_email) {
		super();
		this.prj_id = prj_id;
		this.user_email = user_email;
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

	public String getPrj_mem_lv() {
		return prj_mem_lv;
	}

	public void setPrj_mem_lv(String prj_mem_lv) {
		this.prj_mem_lv = prj_mem_lv;
	}

	public String getPrj_mem_nik() {
		return prj_mem_nik;
	}

	public void setPrj_mem_nik(String prj_mem_nik) {
		this.prj_mem_nik = prj_mem_nik;
	}

	public String getPrj_own_fl() {
		return prj_own_fl;
	}

	public void setPrj_own_fl(String prj_own_fl) {
		this.prj_own_fl = prj_own_fl;
	}

	public String getPrj_mem_del_fl() {
		return prj_mem_del_fl;
	}

	public void setPrj_mem_del_fl(String prj_mem_del_fl) {
		this.prj_mem_del_fl = prj_mem_del_fl;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public String getPrj_nm() {
		return prj_nm;
	}

	public void setPrj_nm(String prj_nm) {
		this.prj_nm = prj_nm;
	}

	public String getFrd_email() {
		return frd_email;
	}

	public void setFrd_email(String frd_email) {
		this.frd_email = frd_email;
	}

	@Override
	public String toString() {
		return "Project_MemVo [prj_id=" + prj_id + ", user_email=" + user_email + ", prj_mem_lv=" + prj_mem_lv
				+ ", prj_mem_nik=" + prj_mem_nik + ", prj_own_fl=" + prj_own_fl + ", prj_mem_del_fl=" + prj_mem_del_fl
				+ ", user_nm=" + user_nm + ", prj_nm=" + prj_nm + ", frd_email=" + frd_email + "]";
	}

}
