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
				
				var user_email = data.user_email
				data.publicFileList.forEach(function(file, index){
					//html생성
					html += "<tr id='filetr'>";
					html += "<td>"+ file.rn + "</td>";
					html += "<td>"+ file.wrk_nm + "</td>";
					html += "<td><a href='/fileDownLoad?file_id="+file.file_id+"'>" + file.original_file_nm+ "</a></td>";
					html += "<td>"+ file.user_email + "</td>";
					html += "<td>"+ file.user_nm + "</td>";
					html += "<td>"+ file.prjStartDtStr + "</td>";
					if(file.user_email==user_email){
						html += "<td><a href='javascript:updateFile("+file.file_id+")'>삭제</a></td>";
					}else{
						html += "<td></td>";
					}
					html += "</tr>";			
				});
				
				
				var lvhtml = "";
				if(data.LV.prj_mem_lv == 'LV0'){
					lvhtml += "<li id='adminFileList'>DownLoad Statistics</li>"; 
					if($('#adminFileList').length == 1){
						$("#adminFileList").remove();
					}
					$(".tabs").append(lvhtml);
					
				}
				
					
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
		
		$(".sub_menu").on("click", "#adminFileList", function(){
			historyPagination(1,20);
		});
	}
		
		$(".sub_menu").on("click", "#linkList",function(){
			publicLinkPagination(1, 10);
		})
		
		$(".sub_menu").on("click", "#fileList",function(){
			publicFilePagination2(1, 10);
		})
	});
	
	function historyPagination(page, pageSize){
		$.ajax({
			url : "/historyPagination",
			method : "post",
			data : "page=" + page + "&pageSize="+ pageSize,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";
				
				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>Statistic_Num</th>";
				hhtml += "<th>파일명</th>";
				hhtml += "<th>해당 업무명</th>";
				hhtml += "<th>다운로드 받은 멤버 ID</th>";
				hhtml += "<th>다운로드 받은 멤버 이름</th>";
				hhtml += "<th>다운로드 받은 날짜</th>";
				hhtml += "</tr>";
				
				data.historyList.forEach(function(history, index){
					//html생성
					html += "<tr>";
					html += "<td>"+ history.rn + "</td>";
					html += "<td>"+ history.original_file_nm + "</td>";
					html += "<td>"+ history.wrk_nm + "</td>";
					html += "<td>"+ history.user_email + "</td>";
					html += "<td>"+ history.user_nm + "</td>";
					html += "<td>"+ history.prjStartDtStr + "</td>";
					html += "</tr>";
				});
				
				var pHtml = "";
				var pageVo = data.pageVo;
				
				if(pageVo.page==1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a onclick='historyPagination("+(pageVo.page-1)+", "+pageVo.pageSize+");' href='javascript:void(0);'>«</a></li>";
				
				for(var i =1; i <=data.paginationSize; i++){
					if(pageVo.page==i)
						pHtml += "<li class='active'><span>" + i + "</span></li>";
					else
						pHtml += "<li><a href='javascript:void(0);' onclick='historyPagination("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
				}
				if(pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:void(0);' onclick='historyPagination("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
				
				$(".pagination").html(pHtml);
				$("#publicHeader").html(hhtml);
				$("#publicList").html(html);
			}
		});
	}
		
	
	
	
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
				
				var user_email = data.user_email
				data.publicLinkList.forEach(function(link, index){
					//html생성
					html += "<tr>";
					html += "<td>"+ link.rn + "</td>";
					html += "<td>"+ link.wrk_nm + "</td>";
					html += "<td><a href='https://"+link.attch_url+"' target='_blank'>"+link.attch_url+"</a></td>";	
					html += "<td>"+ link.user_email + "</td>";
					html += "<td>"+ link.user_nm + "</td>";
					html += "<td>"+ link.prjStartDtStr + "</td>";
					if(link.user_email==data.user_email){
						html += "<td><a href='javascript:updateLink("+link.link_id+")'>삭제</a></td>";
					}else{
						html += "<td></td>";
					}
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
				
				var user_email = data.user_email
				data.publicLinkList.forEach(function(link, index){
					//html생성
					html += "<tr>";
					html += "<td>"+ link.rn + "</td>";
					html += "<td>"+ link.wrk_nm + "</td>";
					html += "<td><a href='https://"+link.attch_url+"' target='_blank'>"+link.attch_url+"</a></td>";	
					html += "<td>"+ link.user_email + "</td>";
					html += "<td>"+ link.user_nm + "</td>";
					html += "<td>"+ link.prjStartDtStr + "</td>";
					if(link.user_email==data.user_email){
						html += "<td><a href='javascript:updateLink("+link.link_id+")'>삭제</a></td>";
					}else{
						html += "<td></td>";
					}
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
	        	layer_popup("#layer20");
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

				var user_email = data.user_email
				data.publicFileList.forEach(function(file, index){
				
					//html생성
					html += "<tr id='filetr'>";
					html += "<td>"+ file.rn + "</td>";
					html += "<td>"+ file.wrk_nm + "</td>";
					html += "<td><a href='/fileDownLoad?file_id="+file.file_id+"'>" + file.original_file_nm+ "</a></td>";
					html += "<td>"+ file.user_email + "</td>";
					html += "<td>"+ file.user_nm + "</td>";
					html += "<td>"+ file.prjStartDtStr + "</td>";
					if(file.user_email==user_email){
						html += "<td><a href='javascript:updateFile("+file.file_id+")'>삭제</a></td>";
					}
					html += "</tr>";	
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				
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
	
	function updateFile(fileID, wrk_id){
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
				
				var user_email = data.user_email
				data.publicFileList.forEach(function(file, index){
				
					//html생성
					html += "<tr id='filetr'>";
					html += "<td>"+ file.rn + "</td>";
					html += "<td>"+ file.wrk_nm + "</td>";
					html += "<td><a href='/fileDownLoad?file_id="+file.file_id+"'>" + file.original_file_nm+ "</a></td>";
					html += "<td>"+ file.user_email + "</td>";
					html += "<td>"+ file.user_nm + "</td>";
					html += "<td>"+ file.prjStartDtStr + "</td>";
					if(file.user_email==user_email){
						html += "<td><a href='javascript:updateFile("+file.file_id+")'>삭제</a></td>";
					}else{
						html += "<td></td>";
					}
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
	        	layer_popup("#layer20");
	            return false;
			}
		});
	}
	
	//layer popup - 프로젝트 생성
	function layer_popup(el){
		console.log(el);

        var $el = $(el);		//레이어의 id를 $el 변수에 저장
        var isDim = $el.prev().hasClass('dimBg');	//dimmed 레이어를 감지하기 위한 boolean 변수

        isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

        var $elWidth = ~~($el.outerWidth()),
            $elHeight = ~~($el.outerHeight()),
            docWidth = $(document).width(),
            docHeight = $(document).height();

        // 화면의 중앙에 레이어를 띄운다.
        if ($elHeight < docHeight || $elWidth < docWidth) {
            $el.css({
                marginTop: -$elHeight /2,
                marginLeft: -$elWidth/2
            })
        } else {
            $el.css({top: 0, left: 0});
        }

        $el.find('a.btn-layerClose').click(function(){
            isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
            return false;
        });

        $('.layer .dimBg').click(function(){
            $('.dim-layer').fadeOut();
            return false;
        });
    }
	
</script>

<section class="contents">

<div class="sub_menu">
	<ul class="sub_menu_item">
		<li><a href="/work/list">Work</a></li>
		<li><a href="/gantt/project">Gantt Chart</a></li>
		<li><a href="/analysis">Work Analysis</a></li>
		<li><a href="/publicFilePagination">File&amp;Link</a></li>
		<li><a href="">Meeting</a></li>
		<li><a href="/vote">Vote</a></li>
	</ul>
	
	<!-- 영하가 수정함 여기서부터ㅎ -->
	<div class="sub_btn">
		<ul>
			<li><a href="#">4</a></li>
			<li><a href="/conferenceList">회의록</a></li>
			<li><a href="#">프로젝트 대화</a></li>
			<li><a href="#">프로젝트 설정</a></li>
		</ul>
	</div>
	<!-- 영하가 수정함 여기까지ㅎ -->
	
</div>


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
<div id="layer20" class="pop-layer">
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
