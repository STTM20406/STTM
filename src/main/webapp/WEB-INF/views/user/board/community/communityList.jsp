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
		
		$(".sub_menu").on("click", "#postList",function(){
			var board_id = $("#boardnum02").val();
			console.log(board_id);
			alert("게시글List입니다.");
			CommunityListPagination(board_id,1, 10);
		})
		
		$(".sub_menu").on("click", "#myPostList",function(){
			var board_id = $("#boardnum02").val();
			console.log(board_id);
			alert("내가 작성한 게시글List입니다.");
// 			sendNoteListPagination(1, 10);
		})

		$("#searchBtn").on("click", function() {
			$("#searchFrm").submit();
		
		});
		
	})
	
	function CommunityListPagination(board_id,page,pageSize){
		$.ajax({
				url:"/communityAjax",
				method:"get",
				data:"board_id=" + board_id + "&page="+page+"&pageSize="+pageSize ,
				success : function(data){
					console.log("lllllllllllllllll")
					console.log(data);
					//사용자 리스트
					var hhtml = "";
					var html = "";
					
					//hhtml생성
					hhtml += "<tr>";
					hhtml += "<th>번호</th>";
					hhtml += "<th>제목</th>";
					hhtml += "<th>작성자</th>";
					hhtml += "<th>작성일</th>";
					hhtml += "<th>댓글</th>";
					hhtml += "<th>좋아요</th>";
					hhtml += "<th>조회수</th>";
					hhtml += "</tr>";
					
					data.boardList.forEach(function(board, index){
							//html생성
							html += "<tr class='boardTr' >";
							html += "<td class='boardNum' style= 'display:none;''>"+ board.write_id + "</td>";
							html += "<td>"+ board.rn + "</td>";
							html += "<td>"+board.subject+"</td>";	
							html += "<td>"+ board.user_email + "</td>";
							html += "<td>"+board.writedate+"</td>";
							html += "<td>댓글수 들어갈거야</td>";
							html += "<td>"+board.like_cnt+"</td>";
							html += "<td>"+board.view_cnt+"</td>";
							html += "</tr>";
					});
					var pHtml = "";
					var pageVo = data.pageVo;
					console.log(data);
					console.log(pageVo);
					
					if(pageVo.page==1)
						pHtml += "<li class='disabled'><span>«<span></li>";
					else
						pHtml += "<li><a onclick='paginationSize("+(pageVo.page-1)+", "+pageVo.pageSize+");' href='javascript:void(0);'>«</a></li>";
					
					for(var i =1; i <=data.paginationSize; i++){
						if(pageVo.page==i)
							pHtml += "<li class='active'><span>" + i + "</span></li>";
						else
							pHtml += "<li><a href='javascript:void(0);' onclick='paginationSize("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
					}
					if(pageVo.page == data.paginationSize)
						pHtml += "<li class='disabled'><span>»<span></li>";
					else
						pHtml += "<li><a href='javascript:void(0);' onclick='paginationSize("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
					
					$(".pagination").html(pHtml);
					$("#publicHeader").html(hhtml);
					$("#publicList").html(html);
				}
		
			})	
	}
		
		
	
</script>


<section class="contents">
	<div id="container">

		<div class="sub_menu">
			<ul class="tabs">
				<li id="postList">게시글</li>
				<li id="myPostList">내가 작성한 글</li>
			</ul>
		</div>

		<div class="tab_con">
			<div id="tab-1" class="tab-content current">
			<form id="searchFrm" action="/boardSearch" method="post">
			<input type="text" name="boardnum02" id="boardnum02" value="${board_id }"> 
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
						<thead id="publicHeader">
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>댓글</th>
								<th>좋아요</th>
								<th>조회수</th>
							</tr>
						</thead>
						<tbody id="publicList">
							<c:forEach items="${boardList }" var="board">
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
							</c:forEach>
						</tbody>
					</table>

					<button id="addBtn" type="button" class="btn_style_01">게시글 작성</button>
					
					<div class="pagination">
		                  <c:choose>
		                     <c:when test="${pageVo.page == 1 }">
		                        <a href class="btn_first"></a>
		                     </c:when>
		                     <c:otherwise>
		                        <a href="${cp}/community?board_id=${pageVo.board_id }&page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
		                     
		                     </c:otherwise>
		                  </c:choose>
		
		                  <c:forEach begin="1" end="${paginationSize}" var="i">
		                     <c:choose>
		                        <c:when test="${pageVo.page == i}">
		                           <span>${i}</span>
		                        </c:when>
		                        <c:otherwise>
		                        <a href="${cp}/community?board_id=${pageVo.board_id }&page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
		                        </c:otherwise>
		                     </c:choose>
		
		                  </c:forEach>
		
		                  <c:choose>
		                     <c:when test="${pageVo.page == paginationSize}">
		                        <a href class="btn_last"></a>
		                     </c:when>
		                     <c:otherwise>
		                     <a href="${cp}/community?board_id=${pageVo.board_id }&page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
		                        
		
		                     </c:otherwise>
		                  </c:choose>
		            
		            </div>
					
					
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
						<thead id="publicHeader">
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>댓글</th>
								<th>좋아요</th>
								<th>조회수</th>
							</tr>
							</thead>
							<tbody id="publicList">
							<c:forEach items="${myBoardList }" var="myboard">
								<tr class="boardTr">
									<td class="boardNum" style="display:none;">${myboard.write_id }</td>
									<td>${myboard.rn }</td>
									<td>${myboard.subject }</td>
									<td>${myboard.user_email }</td>
									<td><fmt:formatDate value="${myboard.writedate }" pattern="yyyy-MM-dd" /></td>
									<td>댓글수 들어갈거야</td>
									<td>${myboard.like_cnt }</td>
									<td>${myboard.view_cnt }</td>
								</tr>
							</c:forEach>
							</tbody>
					</table>
					
					<div class="pagination">
		                  <c:choose>
		                     <c:when test="${pageVo.page == 1 }">
		                        <a href class="btn_first"></a>
		                     </c:when>
		                     <c:otherwise>
		                        <a href="${cp}/community?board_id=${pageVo.board_id }&page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
		                     
		                     </c:otherwise>
		                  </c:choose>
		
		                  <c:forEach begin="1" end="${myaginationSize}" var="i">
		                     <c:choose>
		                        <c:when test="${pageVo.page == i}">
		                           <span>${i}</span>
		                        </c:when>
		                        <c:otherwise>
		                        <a href="${cp}/community?board_id=${pageVo.board_id }&page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
		                        </c:otherwise>
		                     </c:choose>
		
		                  </c:forEach>
		
		                  <c:choose>
		                     <c:when test="${pageVo.page == myaginationSize}">
		                        <a href class="btn_last"></a>
		                     </c:when>
		                     <c:otherwise>
		                     <a href="${cp}/community?board_id=${pageVo.board_id }&page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
		                        
		
		                     </c:otherwise>
		                  </c:choose>
		            
		            </div>
					
					</form>
				</div>
		</div>
	</div>
</section>