<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.boardTr:hover {
	cursor: pointer;
}
ul.tabs {
	margin: 0px;
	padding: 0px;
	list-style: none;
}

ul.tabs li {
	background: none;
	color: #222;
	display: inline-block;
	padding: 10px 15px;
	cursor: pointer;
}

ul.tabs li.current {
	color: #222;
}

.tab-content {
	display: none;
	padding: 15px;
}

.tab-content.current {
	display: inherit;
</style>

<script>
	$(document).ready(function() {
		$('#addBtn').click(function() {
			$("#frm").submit();
		})

		$(".boardTr").on("click", function() {
			console.log("boardTr click");
			var boardNum = $(this).find(".boardNum").text();
			$("#write_id").val(boardNum);
			$("#frm").attr("action", "/postView");
			$("#frm").attr("method", "get");
			$("#frm").submit();
		})
		
		$('ul.tabs li').click(function() {
			var tab_id = $(this).attr('data-tab');

			$('ul.tabs li').removeClass('current');
			$('.tab-content').removeClass('current');

			$(this).addClass('current');
			$("#" + tab_id).addClass('current');
		});

		$("#searchBtn").on("click", function() {
			$("#searchFrm").submit();
		
		});
		
		
		
	})
</script>


<section class="contents">
	<div id="container">

		<div class="sub_menu">
			<ul class="tabs">
				<li data-tab="tab-1">게시글</li>
				<li data-tab="tab-2">내가 작성한 글</li>
			</ul>
		</div>

		<div class="tab_con">
			<div id="tab-1" class="tab-content current">
			<form id="searchFrm" action="/boardSearch" method="post">
			<input type="hidden" name="boardnum02" id="boardnum02" value="${board_id }"> 
				<div class="searchBox">
                  <div class="tb_sch_wr">
                     <fieldset id="hd_sch">
                           <select id="search" name="search">
                              <option value="title">제목</option>
                              <option value="content">내용</option>
                           </select>
                           <input type="text" name="searchText" id="searchText" maxlength="20" placeholder="검색어를 입력해주세요">
                           <button type="submit" id="searchBtn" value="검색">검색</button>
                        </fieldset>
                     </div>
                  </div>
                </form>
				<form id="frm" action="/postAdd" method="get">
				<input type="hidden" name="boardnum" id="boardnum" value="${board_id }"> 
					<input type="hidden" id="write_id" name="write_id" value="" />
					<table class="tb_style_01">
						<tbody>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>댓글</th>
								<th>좋아요</th>
								<th>조회수</th>
							</tr>
							<c:forEach items="${boardList }" var="board">
								<c:choose>
									<c:when test="${board.del_yn == 'N' }">
										<tr class="boardTr"> 
											<td class="boardNum" style= "display:none;">${board.write_id }</td>
											<td>${board.rn }</td>
											<td>${board.subject }</td>
											<td>${board.user_email }</td>
											<td><fmt:formatDate value="${board.writedate }" pattern="yyyy-MM-dd" /></td>
											<td>댓글수 들어갈거야</td>
											<td>${board.like_cnt }</td>
											<td>${board.view_cnt }</td>
										</tr>
									</c:when>
								</c:choose>
							</c:forEach>
						</tbody>
					</table>

					<button id="addBtn" type="button" class="btn_style_01">게시글 작성</button>

					</form>	
				</div>
				
				<div id="tab-2" class="tab-content">
				<form id="searchFrm" action="/boardSearch" method="post">
				<input type="hidden" name="boardnum02" id="boardnum02" value="${board_id }"> 
					<div class="searchBox">
	                  <div class="tb_sch_wr">
	                     <fieldset id="hd_sch">
	                           <select id="search" name="search">
	                              <option value="title">제목</option>
	                              <option value="content">내용</option>
	                           </select>
	                           <input type="text" name="searchText" id="searchText" maxlength="20" placeholder="검색어를 입력해주세요">
	                           <button type="submit" id="searchBtn" value="검색">검색</button>
	                        </fieldset>
	                     </div>
	                  </div>
                </form>
					<form id="frm02">
					<table class="tb_style_01">
						<tbody>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>댓글</th>
								<th>좋아요</th>
								<th>조회수</th>
							</tr>
							<c:forEach items="${boardList }" var="board">
								<c:choose>
									<c:when test="${board.del_yn == 'N' && board.user_email == USER_INFO.user_email}">
										<tr class="boardTr">
											<td class="boardNum" style="display:none;">${board.write_id }</td>
											<td>${board.rn }</td>
											<td>${board.subject }</td>
											<td>${board.user_email }</td>
											<td><fmt:formatDate value="${board.writedate }" pattern="yyyy-MM-dd" /></td>
											<td>댓글수 들어갈거야</td>
											<td>${board.like_cnt }</td>
											<td>${board.view_cnt }</td>
										</tr>
									</c:when>
								</c:choose>
							</c:forEach>
							</tbody>
					</table>
					</form>
				</div>
		</div>
	</div>
</section>