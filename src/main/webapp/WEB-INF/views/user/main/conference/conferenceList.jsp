<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.minutes:hover{
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
	$(document).ready(function(){
		
		conferencePagination(1,10);
		
		$('#searchBtn').on('click',function(){
			if($('#searchText').val().length == 0){
				alert("검색어를 입력해주세요");
			}else{
				var user_nm = $('#searchText').val();
				searchMinutes(1, 10, user_nm);
			}
		});
		
		function conferencePagination(page, pageSize){
			$.ajax({
				url : "/conferencePagination",
				method : "post",
				data : "page=" + page + "&pageSize="+ pageSize,
				success : function(data) {
					//사용자 리스트
					var hhtml = "";
					var html = "";
					
					//hhtml생성
					hhtml += "<tr>";
					hhtml += "<th>Minutes_Num</th>";
					hhtml += "<th>작성자 ID</th>";
					hhtml += "<th>작성자 이름</th>";
					hhtml += "<th>작성일시</th>";
					hhtml += "</tr>";
					
					data.minutesList.forEach(function(minutesList, index){
						//html생성
						
						html += "<tr class='minutes'>";
						
						<!-- 여기서 detail이동이 안되요 -->
// 						html += "<td class='mnu_id' id='"+ minutesList.mnu_id +"'>"+ minutesList.num + "</td>";
						html += "<td><a href='javascript:conferenceDetail?mnu_id=" + minutesList.mnu_id + "'>"+ minutesList.num + "</a></td>";
						html += "<td>"+ minutesList.user_email + "</td>";
						html += "<td>"+ minutesList.user_nm + "</td>";
						html += "<td>"+ minutesList.prjStartDtStr + "</td>";
						html += "</tr>";
					}); 
					
					var pHtml = "";
					var pageVo = data.pageVo;
					
					if(pageVo.page==1)
						pHtml += "<li class='disabled'><span>«<span></li>";
					else
						pHtml += "<li><a href='javascript:conferencePagination("+(pageVo.page-1)+", "+pageVo.pageSize+");'>«</a></li>";
					
					for(var i =1; i <=data.paginationSize; i++){
						if(pageVo.page==i)
							pHtml += "<li class='active'><span>" + i + "</span></li>";
						else
							pHtml += "<li><a href='javascript:conferencePagination("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
					}
					if(pageVo.page == data.paginationSize)
						pHtml += "<li class='disabled'><span>»<span></li>";
					else
						pHtml += "<li><a href='javascript:conferencePagination("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
					
					$(".pagination").html(pHtml);
					$("#conferenceListHeader").html(hhtml);
					$("#conferenceList").html(html);
				}
			});
		}
		
		function searchMinutes(page, pageSize, user_nm) {
			$.ajax({
				url : "/searchMinutes",
				method : "post",
				data : "page=" + page + "&pageSize="+ pageSize +"&user_nm=" + user_nm,
				success : function(data) {
					//사용자 리스트
					var hhtml = "";
					var html = "";
					
					//hhtml생성
					hhtml += "<tr>";
					hhtml += "<th>Minutes_Num</th>";
					hhtml += "<th>작성자 ID</th>";
					hhtml += "<th>작성자 이름</th>";
					hhtml += "<th>작성일시</th>";
					hhtml += "</tr>";
					
					data.searchList.forEach(function(searchList, index){
						//html생성
						
						html += "<tr class='minutes'>";
						html += "<td class='mnu_id' id='"+ searchList.mnu_id +"'>"+ searchList.num + "</td>";
						html += "<td>"+ searchList.user_email + "</td>";
						html += "<td>"+ searchList.user_nm + "</td>";
						html += "<td>"+ searchList.prjStartDtStr + "</td>";
						html += "</tr>";
					});
					
					var pHtml = "";
					var pageVo = data.pageVo;
					
					console.log(pageVo);
					console.log(data.paginationSize);
					
					if(pageVo.page==1)
						pHtml += "<li class='disabled'><span>«<span></li>";
					else
						pHtml += "<li><a onclick='conferencePagination("+(pageVo.page-1)+", "+pageVo.pageSize+");' href='javascript:void(0);'>«</a></li>";
					
					for(var i =1; i <=data.paginationSize; i++){
						if(pageVo.page==i)
							pHtml += "<li class='active'><span>" + i + "</span></li>";
						else
							pHtml += "<li><a href='javascript:void(0);' onclick='conferencePagination("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
					}
					if(pageVo.page == data.paginationSize)
						pHtml += "<li class='disabled'><span>»<span></li>";
					else
						pHtml += "<li><a href='javascript:void(0);' onclick='conferencePagination("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
					
					$(".pagination").html(pHtml);
					$("#conferenceListHeader").html(hhtml);
					$("#conferenceList").html(html);
				}
			});
		}
		
		$(".sub_menu").on("click", "#minutes",function(){
			conferencePagination(1, 10);
		})
		
		//Detail page로 가야하는데 클릭 event가 안먹어!!!!!!!!!!!!!!!!!!
		$(".minutes").on("click", function(){
			alert("click");
		});
//	 		var mnu_id = $(this).find(".mnu_id").attr("id");
//	 		$('#mnu_id').val(mnu_id);
//	 		$('#frm').submit();
	});
	
	function conferencePagination(page, pageSize){
		$.ajax({
			url : "/conferencePagination",
			method : "post",
			data : "page=" + page + "&pageSize="+ pageSize,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";
				
				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>Minutes_Num</th>";
				hhtml += "<th>작성자 ID</th>";
				hhtml += "<th>작성자 이름</th>";
				hhtml += "<th>작성일시</th>";
				hhtml += "</tr>";
				
				data.minutesList.forEach(function(minutesList, index){
					//html생성
					
					html += "<tr class='minutes'>";
					html += "<td class='mnu_id' id='"+ minutesList.mnu_id +"'>"+ minutesList.num + "</td>";
					html += "<td>"+ minutesList.user_email + "</td>";
					html += "<td>"+ minutesList.user_nm + "</td>";
					html += "<td>"+ minutesList.prjStartDtStr + "</td>";
					html += "</tr>";
				});
				
				var pHtml = "";
				var pageVo = data.pageVo;
				
				if(pageVo.page==1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:conferencePagination("+(pageVo.page-1)+", "+pageVo.pageSize+");'>«</a></li>";
				
				for(var i =1; i <=data.paginationSize; i++){
					if(pageVo.page==i)
						pHtml += "<li class='active'><span>" + i + "</span></li>";
					else
						pHtml += "<li><a href='javascript:conferencePagination("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
				}
				if(pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:conferencePagination("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
				
				$(".pagination").html(pHtml);
				$("#conferenceListHeader").html(hhtml);
				$("#conferenceList").html(html);
			}
		});
	}
	
	
	
</script>




<section class="contents">
	<div id="container">
	<form id="frm" action="/conferenceDetail" method="get">
		<input type="hidden" id="mnu_id" name="mnu_id">
	</form>
		
	<div class="sub_menu">
		<ul class="tabs">
			<li id="minutes">회의록 게시판</li>
		</ul>
	</div>
		
	<div class="searchBox">
		<div class="tb_sch_wr">
	 		 <select class="search" name="selectFile">
              	<option value="fileName">파일명</option>
             </select>
             <input type="text" value="" name="original_file_nm" id="searchText" maxlength="20" placeholder="검색어를 입력해주세요">
             <button type="button" id ="searchBtn" value="검색">검색</button>
    	</div>
    </div>
    
		<div class="tab_con">

			<div class="tab-content current">
				<div>
					<table class="tb_style_01">
						<colgroup>
							<col width="20%">
							<col width="30%">
							<col width="20%">
							<col width="30%">
						</colgroup>
						
							<thead id="conferenceListHeader">
							<thead>
							
							<tbody id="conferenceList">
						</tbody>
						
					</table>
				</div>
			<a href="/insertConference">회의록 작성</a>
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
