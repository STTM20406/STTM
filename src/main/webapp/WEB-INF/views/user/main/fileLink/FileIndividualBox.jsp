<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	$(document).ready(function() {
		individualPagination(1,10);
		
		$('.tb_sch_wr').on('click',"#searchBtn",function(){
			if($('#searchText').val().length == 0){
				alert("검색어를 입력해주세요");
			}else{
				var original_file_nm = $('#searchText').val();
				searchFile(1,10,original_file_nm);
			}
		});
		
		$(".sub_menu").on("click", "#individualBox",function(){
			alert("개인보관함입니다.");
			individualBox(1, 10);
		})
	})
	
	function searchFile(page, pageSize,original_file_nm) {
		$.ajax({
			url : "/searchFile",
			method : "post",
			data : "page=" + page + "&pageSize="+ pageSize +"&original_file_nm=" + original_file_nm,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";
				console.log(data);
				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>File_Num</th>";
				hhtml += "<th>파일명</th>";
				hhtml += "<th>등록일</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";
				
				data.individualList.forEach(function(file, index){
					//html생성
					html += "<tr id='filetr'>";
					html += "<td>"+ file.rn + "</td>";
					html += "<td><a href='/fileDownLoad?file_id="+file.file_id+"'>" + file.original_file_nm+ "</a></td>";	
					html += "<td>"+ file.prjStartDtStr + "</td>";
					html += "<td><a href='javascript:updateInFile("+file.file_id+")'>삭제</a></td>";
					html += "</tr>";
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				
				if(pageVo.page==1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:individualPagination("+(pageVo.page-1)+", "+pageVo.pageSize+");'>«</a></li>";
				
				for(var i =1; i <=data.paginationSize; i++){
					if(pageVo.page==i)
						pHtml += "<li class='active'><span>" + i + "</span></li>";
					else
						pHtml += "<li><a href='javascript:individualPagination("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
				}
				if(pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:individualPagination("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
		
				$(".pagination").html(pHtml);
				$("#FileIndividualBox").html(hhtml);
				$("#fileList").html(html);
			}
		});
	}
	
		
	function individualPagination(page, pageSize) {
		$.ajax({
			url : "/individualPagination",
			method : "post",
			data : "page=" + page + "&pageSize="+ pageSize,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";
				
				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>File_Num</th>";
				hhtml += "<th>파일명</th>";
				hhtml += "<th>등록일</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";
				
				console.log(data);
				data.individualList.forEach(function(file, index){
					//html생성
					html += "<tr id='filetr'>";
					html += "<td>"+ file.rn + "</td>";
					html += "<td><a href='/fileDownLoad?file_id="+file.file_id+"'>" + file.original_file_nm+ "</a></td>";
					html += "<td>"+ file.prjStartDtStr + "</td>";
					html += "<td><a href='javascript:updateInFile("+file.file_id+")'>삭제</a></td>";
					html += "</tr>";
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				
				if(pageVo.page==1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:individualPagination("+(pageVo.page-1)+", "+pageVo.pageSize+");'>«</a></li>";
				
				for(var i =1; i <=data.paginationSize; i++){
					if(pageVo.page==i)
						pHtml += "<li class='active'><span>" + i + "</span></li>";
					else
						pHtml += "<li><a href='javascript:individualPagination("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
				}
				if(pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:individualPagination("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
		
				$(".pagination").html(pHtml);
				$("#FileIndividualBox").html(hhtml);
				$("#fileList").html(html);
			}
		});
	}
	
	function individualBox(page, pageSize) {
		$.ajax({
			url : "/individualPagination",
			method : "post",
			data : "page=" + page + "&pageSize="+ pageSize,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";
				
				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>File_Num</th>";
				hhtml += "<th>파일명</th>";
				hhtml += "<th>등록일</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";
				
				data.individualList.forEach(function(file, index){
					//html생성
					html += "<tr id='filetr'>";
					html += "<td>"+ file.rn + "</td>";
					html += "<td><a href='/fileDownLoad?file_id="+file.file_id+"'>" + file.original_file_nm+ "</a></td>";
					html += "<td>"+ file.prjStartDtStr + "</td>";
					html += "<td><a href='javascript:updateInFile("+file.file_id+")'>삭제</a></td>";
					html += "</tr>";
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				
				if(pageVo.page==1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:individualPagination("+(pageVo.page-1)+", "+pageVo.pageSize+");'>«</a></li>";
				
				for(var i =1; i <=data.paginationSize; i++){
					if(pageVo.page==i)
						pHtml += "<li class='active'><span>" + i + "</span></li>";
					else
						pHtml += "<li><a href='javascript:individualPagination("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
				}
				if(pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:individualPagination("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
		
				$(".pagination").html(pHtml);
				$("#FileIndividualBox").html(hhtml);
				$("#fileList").html(html);
			}
		});
	}
	
	function updateInFile(fileID){
		$.ajax({
			url : "/updateInFile",
			method : "post",
			data : "file_id=" + fileID,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";
				
				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>File_Num</th>";
				hhtml += "<th>파일명</th>";
				hhtml += "<th>등록일</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";
				
				console.log(data);
				
				data.individualList.forEach(function(file, index){
					//html생성
					html += "<tr id='filetr'>";
					html += "<td>"+ file.rn + "</td>";
					html += "<td><a href='/fileDownLoad?file_id="+file.file_id+"'>" + file.original_file_nm+ "</a></td>";
					html += "<td>"+ file.prjStartDtStr + "</td>";
					html += "<td><a href='javascript:updateInFile("+file.file_id+")'>삭제</a></td>";
					html += "</tr>";
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				
				if(pageVo.page==1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:individualPagination("+(pageVo.page-1)+", "+pageVo.pageSize+");'>«</a></li>";
				
				for(var i =1; i <=data.paginationSize; i++){
					if(pageVo.page==i)
						pHtml += "<li class='active'><span>" + i + "</span></li>";
					else
						pHtml += "<li><a href='javascript:individualPagination("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
				}
				if(pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:individualPagination("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
		
				$(".pagination").html(pHtml);
				$("#FileIndividualBox").html(hhtml);
				$("#fileList").html(html);  
				$(".ctxt").text("해당 게시물이 삭제 되었습니다.");
	        	layer_popup("#layer2");
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

	<div id="container">
		
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
							<col width="30%">
							<col width="20%">
						</colgroup>
						
							<thead id="FileIndividualBox">
							<thead>
							
							<tbody id="fileList">
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
