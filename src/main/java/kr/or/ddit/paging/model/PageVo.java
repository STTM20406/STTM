package kr.or.ddit.paging.model;

public class PageVo {
   private int page; // 페이지 번호
   private int pageSize; // 페이지당 건수
   
   private int inq_id; // 해당 1:1문의 게시판의 id
   private String inq_cate; // 문의 카테고리
   private String user_email; // 문의 이메일
   private int board_id; // 게시판 id
   private String content; //게시판 내용
   private String subject; //게시판 제목
   
   private String send_email; // 쪽지 발신자
   private String rcv_email; // 쪽지 수신자   
   
   private int prj_id;
   private String memo_email;
   
   
   public int getPrj_id() {
	return prj_id;
}

public void setPrj_id(int prj_id) {
	this.prj_id = prj_id;
}

public String getMemo_email() {
	return memo_email;
}

public void setMemo_email(String memo_email) {
	this.memo_email = memo_email;
}

public String getSend_email() {
	return send_email;
}

public void setSend_email(String send_email) {
	this.send_email = send_email;
}

public String getRcv_email() {
	return rcv_email;
}

public void setRcv_email(String rcv_email) {
	this.rcv_email = rcv_email;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}

public String getSubject() {
	return subject;
}

public void setSubject(String subject) {
	this.subject = subject;
}

public int getInq_id() {
      return inq_id;
   }

   public void setInq_id(int inq_id) {
      this.inq_id = inq_id;
   }

   public int getBoard_id() {
      return board_id;
   }

   public void setBoard_id(int board_id) {
      this.board_id = board_id;
   }

   public PageVo() {
      
   }
   
   public PageVo(int page, int pageSize, int inq_id) {
      super();
      this.page = page;
      this.pageSize = pageSize;
      this.inq_id = inq_id;
   }

   public PageVo(int page, int pageSize) {
      this.page = page;
      this.pageSize = pageSize;
   }
//   사용자 페이징리스트
   public PageVo(int page, int pageSize,String user_email) {
      super();
      this.page = page;
      this.pageSize = pageSize;
      this.user_email = user_email;
   }

   public String getInq_cate() {
      return inq_cate;
   }

   public void setInq_cate(String inq_cate) {
      this.inq_cate = inq_cate;
   }

   public String getUser_email() {
      return user_email;
   }

   public void setUser_email(String user_email) {
      this.user_email = user_email;
   }

   public int getPage() {
      return page == 0 ? 1 : page;
   }
   public void setPage(int page) {
      this.page = page;
   }
   public int getPageSize() {
      return pageSize == 0 ? 10 :pageSize;
   }
   public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
   }
   
   @Override
   public String toString() {
      return "PageVo [page=" + page + ", pageSize=" + pageSize + "]";
   }
   
}