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

	int MNU_MEM_ID;     //참석자 ID
	int	MNU_ID;         //회의록 게시글 ID
	String	USER_NM;    //참석자 이름
	String	USER_EMAIL; //참석자 이메일
	
	Minutes_MemVo(){
		
	}
	
	public int getMNU_MEM_ID() {
		return MNU_MEM_ID;
	}
	public void setMNU_MEM_ID(int mNU_MEM_ID) {
		MNU_MEM_ID = mNU_MEM_ID;
	}
	public int getMNU_ID() {
		return MNU_ID;
	}
	public void setMNU_ID(int mNU_ID) {
		MNU_ID = mNU_ID;
	}
	public String getUSER_NM() {
		return USER_NM;
	}
	public void setUSER_NM(String uSER_NM) {
		USER_NM = uSER_NM;
	}
	public String getUSER_EMAIL() {
		return USER_EMAIL;
	}
	public void setUSER_EMAIL(String uSER_EMAIL) {
		USER_EMAIL = uSER_EMAIL;
	}
	@Override
	public String toString() {
		return "Minutes_MemVo [MNU_MEM_ID=" + MNU_MEM_ID + ", MNU_ID=" + MNU_ID + ", USER_NM=" + USER_NM
				+ ", USER_EMAIL=" + USER_EMAIL + "]";
	}
	
	
}
