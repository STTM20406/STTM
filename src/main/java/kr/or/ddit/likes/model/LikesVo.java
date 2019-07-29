package kr.or.ddit.likes.model;

/**
 * 
 * LikesVo.java
 *
 * @author 박서경
 * @version 1.0
 * @see
 * LIKES - 좋아요
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
public class LikesVo {
	
	private int like_id;			//좋아요 ID
	private int board_id;           //게시판아이디
	private int write_id;           //게시글아이디
	private String user_email;      //이메일
	
	//기본 생성자
	public LikesVo() {
		
	}

	//생성자
	
	
	//getter, setter
	public int getLike_id() {
		return like_id;
	}

	public void setLike_id(int like_id) {
		this.like_id = like_id;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public int getWrite_id() {
		return write_id;
	}

	public void setWrite_id(int write_id) {
		this.write_id = write_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	//toString
	@Override
	public String toString() {
		return "LikesVo [like_id=" + like_id + ", board_id=" + board_id + ", write_id=" + write_id + ", user_email="
				+ user_email + "]";
	}
	
	
}
