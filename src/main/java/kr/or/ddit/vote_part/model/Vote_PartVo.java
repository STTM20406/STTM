package kr.or.ddit.vote_part.model;

/**
 * 
* Vote_PartVo.java
*
* @author 김경호
* @version 1.0
* @see
* Vote_Part - 투표 참여자
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
public class Vote_PartVo {
	
	private int prj_id; // 프로젝트 아이디
	private String user_email; // 이메일
	private int vote_id; // 투표 아이디
	private int vote_item_id; // 투표 항목 아이디
	
	public Vote_PartVo() {

	}

	public Vote_PartVo(int prj_id, String user_email, int vote_id, int vote_item_id) {
		super();
		this.prj_id = prj_id;
		this.user_email = user_email;
		this.vote_id = vote_id;
		this.vote_item_id = vote_item_id;
	}

	@Override
	public String toString() {
		return "Vote_PartVo [prj_id=" + prj_id + ", user_email=" + user_email + ", vote_id=" + vote_id
				+ ", vote_item_id=" + vote_item_id + "]";
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

	public int getVote_id() {
		return vote_id;
	}

	public void setVote_id(int vote_id) {
		this.vote_id = vote_id;
	}

	public int getVote_item_id() {
		return vote_item_id;
	}

	public void setVote_item_id(int vote_item_id) {
		this.vote_item_id = vote_item_id;
	}
	
}
