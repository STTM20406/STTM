<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
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
		
		$(".sub_menu").on("click", "#linkList",function(){
			alert("linkList입니다.");
			publicLinkPagination(1, 10);
		})
		
		
		//fileList 수정해야함!  
		$(".sub_menu").on("click", "#fileList",function(){
			alert("fileList입니다.");
			publicFilePagination2(1, 10);
		})
		
		
	});
	
	function publicLinkPagination(page, pageSize) {
		$.ajax({
			url : "/publicLinkPagination",
			method : "post",
			data : "page=" + page + "&pageSize"+ pageSize,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";
				
				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>Link_Num</th>";
				hhtml += "<th>업무명</th>";
				hhtml += "<th>링크명</th>";
				hhtml += "<th>작성자 멤버 ID</th>";
				hhtml += "<th>작성자 멤버 이름</th>";
				hhtml += "<th>등록일</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";
				
				data.publicLinkList.forEach(function(link, index){
					//html생성
					html += "<tr>";
					html += "<td>"+ link.num + "</td>";
					html += "<td>"+ link.wrk_nm + "</td>";
					html += "<td><a href='https://"+link.attch_url+"'>"+link.attch_url+"</a></td>";	
					html += "<td>"+ link.user_email + "</td>";
					html += "<td>"+ link.user_nm + "</td>";
					html += "<td>"+ link.prjStartDtStr + "</td>";
					html += "<td><a href='javascript:/updateLink?link_id='"+link.link_id+"'>"+"삭제</a></td>";
					html += "</tr>";
				});
				
				var pHtml = "";
				var pageVo = data.pageVo;
				
				if(pageVo.page==1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a onclick='publicLinkPagination("+(pageVo.page-1)+", "+pageVo.pageSize+");' href='javascript:void(0);'>«</a></li>";
				
				for(var i =1; i <=data.paginationSize; i++){
					if(pageVo.page==i)
						pHtml += "<li class='active'><span>" + i + "</span></li>";
					else
						pHtml += "<li><a href='javascript:void(0);' onclick='publicLinkPagination("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
				}
				if(pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:void(0);' onclick='publicLinkPagination("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
				
				$(".pagination").html(pHtml);
				$("#publicHeader").html(hhtml);
				$("#publicList").html(html);
			}
		});
	}
	
	
	function publicFilePagination2(page, pageSize) {
		$.ajax({
			url : "/publicFilePagination2",
			method : "post",
			data : "page=" + page + "&pageSize"+ pageSize,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";
				
				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>File_Num</th>";
				hhtml += "<th>업무명</th>";
				hhtml += "<th>파일명</th>";
				hhtml += "<th>작성자 멤버 ID</th>";
				hhtml += "<th>작성자 멤버 이름</th>";
				hhtml += "<th>등록일</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";
				
				data.publicFileList.forEach(function(file, index){
				
					//html생성
					html += "<tr>";
					html += "<td>"+ file.num + "</td>";
					html += "<td>"+ file.wrk_nm + "</td>";
					html += "<td><a href='#'>"+file.original_file_nm+"</a></td>";	
					html += "<td>"+ file.user_email + "</td>";
					html += "<td>"+ file.user_nm + "</td>";
					html += "<td>"+ file.prjStartDtStr + "</td>";
// 					html += "<td><a href='/updateFile?file_id='"+file.file_id+"'>"+"삭제</a></td>";
					html += "<td><a href='javascript:/updateFile('"+file.file_id+"');'>삭제</a></td>";
					html += "</tr>";
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				console.log(data);
				console.log(pageVo);
				
				if(pageVo.page==1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:publicFilePagination2("+(pageVo.page-1)+", "+pageVo.pageSize+");'>«</a></li>";
				
				for(var i =1; i <=data.paginationSize; i++){
					if(pageVo.page==i)
						pHtml += "<li class='active'><span>" + i + "</span></li>";
					else
						pHtml += "<li><a href='javascript:publicFilePagination2("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
				}
				if(pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:publicFilePagination2("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
				$(".pagination").html(pHtml);
				$("#publicHeader").html(hhtml);
				$("#publicList").html(html);
			}
		});
	}
	
	function updateFile(file_id){
		alert("긔긔");
	}
	
	
</script>

<section class="contents">

	<form id="frm" action="/admInquiryView" method="get"> 
		<input type="hidden" id="file_id" name="file_id" value=""/>
	</form>
	
	<div id="container">

		<div class="sub_menu">
			<ul class="tabs">
				<li id="fileList">FileList</li>
				<li id="linkList">LinkList</li>
			</ul>
		</div>

		<div class="tab_con">

			<div class="tab-content current">
				<div>
					<table class="tb_style_01">
						<colgroup>
							<col width="10%">
							<col width="10%">
							<col width="20%">
							<col width="20%">
							<col width="10%">
							<col width="20%">
							<col width="10%">
						</colgroup>
							<thead id="publicHeader">
								<tr>
									<th>File_Num</th>
									<th>업무명</th>
									<th>파일명</th>
									<th>작성자 멤버 ID</th>
									<th>작성자 멤버 이름</th>
									<th>등록일</th>
									<th>삭제</th>
								</tr>
							<thead>
							<tbody id="publicList">
								<c:forEach items="${publicFileList}" var="file">
									<c:choose>
									<c:when test="${file.del_fl eq 'N'}">
									<tr>
										<td>${file.num}</td>
										<td>${file.wrk_nm}</td>
										<td><a href="#">${file.original_file_nm}</a></td>
										<td>${file.user_email}</td>
										<td>${file.user_nm}</td>
										<fmt:formatDate value="${file.file_dt}" var="date" pattern="yyyy-MM-dd HH:mm"/>
										<td>${date}</td>
										<td><a href="/updateFile?file_id=${file.file_id}">삭제</a></td>
									</tr>
									</c:when>
									</c:choose>
								</c:forEach>
						</tbody>
					</table>
				</div>

			<div class="pagination">
				<c:choose>
					<c:when test="${pageVo.page eq 1}">
						<li class="disabled"><span>&lt;</span></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="/publicFilePagination?page=${pageVo.page-1}&pageSize=${pageVo.pageSize}">&lt; </a>
						</li>
					</c:otherwise>
				</c:choose>
			
				<c:forEach var="i" begin="1" end="${paginationSize}">
					<c:choose>
						<c:when test="${pageVo.page eq i}">
							<li class="active"><span>${i}</span></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="/publicFilePagination?page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			
				<c:choose>
					<c:when test="${pageVo.page eq paginationSize}">
						<li class="disabled"><span>&gt;</span></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="/publicFilePagination?page=${pageVo.page+1}&pageSize=${pageVo.pageSize}">&gt;</a>
						</li>
					</c:otherwise>
				</c:choose>
				</div>
			</div>
		</div>
	</div>
</section>