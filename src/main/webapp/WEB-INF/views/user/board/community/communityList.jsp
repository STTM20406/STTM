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
}
</style>

<script>
	$(document).ready(function() {
		$('#addBtn').click(function() {
			$("#frm").submit();
		})

		$(".tb_style_01").on("click", ".boardTr",function() {
			console.log("boardTr click");
			var boardNum = $(this).find(".boardNum").text();
			$("#write_id").val(boardNum);
			$("#frm").attr("action", "/postView");
			$("#frm").attr("method", "get");
			$("#frm").submit();
		})
		
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
			MyCommunityListPagination(board_id,1, 10);
		})

		$("#searchBtn").on("click", function() {
			$("#searchFrm").submit();
		
		});
		
	})
	
	function CommunityListPagination(board_id,page,pageSize){
		$.ajax({
				url:"/communityAjax",
				method:"post",
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
					
					data.boardList.forEach(function(boardList, index){
							//html생성
							html += "<tr class='boardTr' >";
							html += "<td class='boardNum' style= 'display:none;''>"+ boardList.write_id + "</td>";
							html += "<td>"+ boardList.rn + "</td>";
							html += "<td>"+boardList.subject+"</td>";	
							html += "<td>"+ boardList.user_email + "</td>";
							html += "<td>"+boardList.writedate+"</td>";
							html += "<td>댓글수 들어갈거야</td>";
							html += "<td>"+boardList.like_cnt+"</td>";
							html += "<td>"+boardList.view_cnt+"</td>";
							html += "</tr>";
					});
					var pHtml = "";
					var pageVo = data.pageVo;
					console.log(pageVo);
					
					if(pageVo.page==1)
						pHtml += "<li class='disabled'><span>«<span></li>";
					else
						pHtml += "<li><a onclick='CommunityListPagination("+board_id+","+(pageVo.page-1)+", "+pageVo.pageSize+");' href='javascript:void(0);'>«</a></li>";
					
					for(var i =1; i <=data.paginationSize; i++){
						if(pageVo.page==i)
							pHtml += "<li class='active'><span>" + i + "</span></li>";
						else
							pHtml += "<li><a href='javascript:void(0);' onclick='CommunityListPagination("+board_id+","+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
					}
					if(pageVo.page == data.paginationSize)
						pHtml += "<li class='disabled'><span>»<span></li>";
					else
						pHtml += "<li><a href='javascript:void(0);' onclick='CommunityListPagination("+board_id+","+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
					
					$(".pagination").html(pHtml);
					$("#communityHeader").html(hhtml);
					$("#communityList").html(html);
				}
		
			});	
	}
	
	function MyCommunityListPagination(board_id,page,pageSize){
		$.ajax({
				url:"/communityAjax",
				method:"post",
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
					
					data.myBoardList.forEach(function(boardList, index){
							//html생성
							html += "<tr class='boardTr' >";
							html += "<td class='boardNum' style= 'display:none;''>"+ boardList.write_id + "</td>";
							html += "<td>"+ boardList.rn + "</td>";
							html += "<td>"+boardList.subject+"</td>";	
							html += "<td>"+ boardList.user_email + "</td>";
							html += "<td>"+boardList.writedate+"</td>";
							html += "<td>댓글수 들어갈거야</td>";
							html += "<td>"+boardList.like_cnt+"</td>";
							html += "<td>"+boardList.view_cnt+"</td>";
							html += "</tr>";
					});
					var pHtml = "";
					var pageVo = data.pageVo;
					console.log(html);
					console.log(pageVo);
					
					if(pageVo.page==1)
						pHtml += "<li class='disabled'><span>«<span></li>";
					else
						pHtml += "<li><a onclick='MyCommunityListPagination("+board_id+","+(pageVo.page-1)+", "+pageVo.pageSize+");' href='javascript:void(0);'>«</a></li>";
					
					for(var i =1; i <=data.myaginationSize; i++){
						if(pageVo.page==i)
							pHtml += "<li class='active'><span>" + i + "</span></li>";
						else
							pHtml += "<li><a href='javascript:void(0);' onclick='MyCommunityListPagination("+board_id+","+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
					}
					if(pageVo.page == data.myaginationSize)
						pHtml += "<li class='disabled'><span>»<span></li>";
					else
						pHtml += "<li><a href='javascript:void(0);' onclick='MyCommunityListPagination("+board_id+","+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
					
					$(".pagination").html(pHtml);
					$("#communityHeader").html(hhtml);
					$("#communityList").html(html);
				}
		
			});	
	}
</script>


<section class="contents">
	<div id="container">

		<div class="sub_menu">
			<ul class="sub_menu_item">
				<li>
					<a href="/community?board_id=${board_id }">게시글</a>
				</li>
				<li>
					<a href="/myCommunity?board_id=${board_id }">내가 작성한 글</a>
				</li>
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
						<thead id="communityHeader">
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
						<tbody id=communityList>
							<c:forEach items="${boardList }" var="board">
										<tr class="boardTr"> 
											<td class="boardNum" style= "display:none;">${board.write_id }</td>
											<td>${board.rn }</td>
											<td>${board.subject }</td>
											<td>${board.user_email }</td>
											<td><fmt:formatDate value="${board.writedate }" pattern="yyyy-MM-dd" /></td>
											<c:forEach items="${mapAnswerCnt}" var="cnt">
												<c:if test="${cnt.key == board.write_id }">
													<td>${cnt.value }</td>
												</c:if>
											</c:forEach>
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
				
		</div>
	</div>
</section>