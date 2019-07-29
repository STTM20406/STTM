package kr.or.ddit.board.model;

import java.util.Date;

/**
 * 
* BoardVo.java
*
* @author 김경호
* @version 1.0
* @see
* Board - 게시판
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
public class BoardVo {
	
	private int board_id; // 게시판 아이디
	private String name; // 게시판 이름
	private Date reg_dt; // 게시판 생성 일시
	private String use_fl; // 게시판 사용 여부
	
	public BoardVo() {

	}

	public BoardVo(int board_id, String name, Date reg_dt, String use_fl) {
		super();
		this.board_id = board_id;
		this.name = name;
		this.reg_dt = reg_dt;
		this.use_fl = use_fl;
	}

	@Override
	public String toString() {
		return "BoardVo [board_id=" + board_id + ", name=" + name + ", reg_dt=" + reg_dt + ", use_fl=" + use_fl + "]";
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(Date reg_dt) {
		this.reg_dt = reg_dt;
	}

	public String getUse_fl() {
		return use_fl;
	}

	public void setUse_fl(String use_fl) {
		this.use_fl = use_fl;
	}
	
}
