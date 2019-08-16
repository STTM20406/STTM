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
		publicFilePagination2(1,10)

		function publicFilePagination2(page, pageSize) {
		$.ajax({
			url : "/publicFilePagination2",
			method : "post",
			data : "page=" + page + "&pageSize="+ pageSize,
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
				console.log(data.publicFileList);
				data.publicFileList.forEach(function(file, index){
				
					//html생성
					html += "<tr id='filetr'>";
					html += "<td>"+ file.num + "</td>";
					html += "<td>"+ file.wrk_nm + "</td>";
					html += "<td><a href='#'>"+file.original_file_nm+"</a></td>";	
					html += "<td>"+ file.user_email + "</td>";
					html += "<td>"+ file.user_nm + "</td>";
					html += "<td>"+ file.prjStartDtStr + "</td>";
					html += "<td><a href='javascript:updateFile("+file.file_id+")'>삭제</a></td>";
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
			data : "page=" + page + "&pageSize="+ pageSize,
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
					html += "<td><a href='javascript:updateLink("+link.link_id+")'>삭제</a></td>";
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
	
	function updateLink(linkID){
		alert("삭제긔긔");
		$.ajax({
			url : "/updateLink",
			method : "post",
			data : "link_id=" + linkID,
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
					html += "<td><a href='javascript:updateLink("+link.link_id+")'>삭제</a></td>";
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
				$(".ctxt").text("해당 게시물이 삭제 되었습니다.");
	        	layer_popup("#layer2");
	            return false;
			}
		});
	}
	
	
	
	function publicFilePagination2(page, pageSize) {
		$.ajax({
			url : "/publicFilePagination2",
			method : "post",
			data : "page=" + page + "&pageSize="+ pageSize,
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
				console.log(data.publicFileList);
				data.publicFileList.forEach(function(file, index){
				
					//html생성
					html += "<tr id='filetr'>";
					html += "<td>"+ file.num + "</td>";
					html += "<td>"+ file.wrk_nm + "</td>";
					html += "<td><a href='#'>"+file.original_file_nm+"</a></td>";	
					html += "<td>"+ file.user_email + "</td>";
					html += "<td>"+ file.user_nm + "</td>";
					html += "<td>"+ file.prjStartDtStr + "</td>";
					html += "<td><a href='javascript:updateFile("+file.file_id+")'>삭제</a></td>";
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
	
	function updateFile(fileID){
		alert("삭제긔긔");

		$.ajax({
			url : "/updateFile",
			method : "post",
			data : "file_id=" + fileID,
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
				console.log(data.publicFileList);
				data.publicFileList.forEach(function(file, index){
				
					//html생성
					html += "<tr id='filetr'>";
					html += "<td>"+ file.num + "</td>";
					html += "<td>"+ file.wrk_nm + "</td>";
					html += "<td><a href='#'>"+file.original_file_nm+"</a></td>";	
					html += "<td>"+ file.user_email + "</td>";
					html += "<td>"+ file.user_nm + "</td>";
					html += "<td>"+ file.prjStartDtStr + "</td>";
					html += "<td><a href='javascript:updateFile("+file.file_id+")'>삭제</a></td>";
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
				$(".ctxt").text("해당 게시물이 삭제 되었습니다.");
	        	layer_popup("#layer2");
	            return false;
			}
		});
	}
	
	
	
</script>

<section class="contents">

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
							<col width="10%">
							<col width="20%">
							<col width="10%">
							<col width="20%">
							<col width="10%">
							<col width="10%">
						</colgroup>
						
							<thead id="publicHeader">
							<thead>
							
							<tbody id="publicList">
						</tbody>
						
					</table>
				</div>

			<div class="pagination">
				</div>
				
			</div>
		</div>
	</div>
</section>


<!-- 오류 알림창 -->
<!-- <div class="dim-layer"> -->
<!-- 	<div class="dimBg"></div> -->
<div id="layer2" class="pop-layer">
	<div class="pop-container">
		<div class="pop-conts">
			<!--content //-->
			<p class="ctxt mb20"></p>
			<div class="btn-r">
				<a href="#" class="btn-layerClose">Close</a>
			</div>
			<!--// content-->
		</div>
	</div>
</div>
<!-- </div> -->
