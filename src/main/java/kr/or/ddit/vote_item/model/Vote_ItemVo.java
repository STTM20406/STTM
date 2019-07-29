package kr.or.ddit.vote_item.model;

/**
 * 
* Vote_ItemVo.java
*
* @author 김경호
* @version 1.0
* @see
* Vote_Item - 투표 항목
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
public class Vote_ItemVo {
	
	private int vote_item_id; // 투표 항목 아이디
	private int vote_id; // 투표 아이디
	private String votd_item_con; // 투표 항목 아이디
	
	public Vote_ItemVo() {

	}

	public Vote_ItemVo(int vote_item_id, int vote_id, String votd_item_con) {
		super();
		this.vote_item_id = vote_item_id;
		this.vote_id = vote_id;
		this.votd_item_con = votd_item_con;
	}

	@Override
	public String toString() {
		return "Vote_ItemVo [vote_item_id=" + vote_item_id + ", vote_id=" + vote_id + ", votd_item_con=" + votd_item_con
				+ "]";
	}

	public int getVote_item_id() {
		return vote_item_id;
	}

	public void setVote_item_id(int vote_item_id) {
		this.vote_item_id = vote_item_id;
	}

	public int getVote_id() {
		return vote_id;
	}

	public void setVote_id(int vote_id) {
		this.vote_id = vote_id;
	}

	public String getVotd_item_con() {
		return votd_item_con;
	}

	public void setVotd_item_con(String votd_item_con) {
		this.votd_item_con = votd_item_con;
	}
	
}
