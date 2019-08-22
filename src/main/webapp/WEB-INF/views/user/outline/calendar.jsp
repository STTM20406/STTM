<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href='/css/fullcalendar.min.css'>
<link rel="stylesheet" href='/css/main.css'>
<link rel="stylesheet" href='/css/material-icon.css'>
<link rel="stylesheet" href='/css/roboto.css'>
<link rel="stylesheet" href='/css/select2.min.css'>


<script>
	function calAjax() {
		console.log("before calAjax");
		$.ajax({
			url: "/calendarTest",
			type: "post",
			data: $("#filterFrm").serialize(),
			success: function(data){
				console.log("before calAjax success");
				console.log("calAjax()입니다.");
				console.log(data.data);
				var filterFrm = data.filterFrm;
				var makerList = data.makerList;
				var prjList = data.prjList;
				console.log("filterFrm : " + filterFrm);
				console.log("makerList : " + makerList);
				console.log("prjList : " + prjList);
// 				$("#frmContainer").html(filterFrm);
				$("#prjList").html(prjList);
				$("#makerList").html(makerList);
				console.log("after calAjax success");
			}
		});
	}
	$(document).ready(function() {
		initCalendar();
		
		
// 		calAjax();
		
		if ("${IW}" == 1) {
			alert("등록완료!");
		}
		
		$("#edit-prj").on("change",function(){
			var prj_id = $(this).val();
			prjStAjax(prj_id);	
		});
		
		//프로젝트 상태값 변경 ajax
		function prjStAjax(prj_id){
			$.ajax({
				url:"/changeWorkList",
				method:"post",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
				data: "prj_id=" + prj_id,
				success:function(data){
					console.log("prjStAjax(prj_id)");
					console.log(data);
					//다녀오셨어요
					var html="";
					data.data.forEach(function(item, index){
						html += "<option value='" +item.wrk_lst_id+"'>"+item.wrk_lst_nm+"</option>";
					});
					$("#edit-type").html(html);
				}
			});
		}
		
		$("#frmContainer").on("change", ".filter", function(){
			console.log("frm : " + $("#filterFrm").serialize());
			
			$("#calendar").fullCalendar( "refetchEvents" );
			
// 			    $.ajax({
// 			      method:"post",
// 			      url: "/calendarTest",
// 			      data: $("#filterFrm").serialize(),
// 			      contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
// 			      success: function (response) {
// 			    	  response = response.data;
// 			    	  console.log(response);
// 			    	  calendar.fullCalendar('removeEvents');
// 			        var fixedDate = response.map(function (array) {
// 			          if (array.allDay && array.start !== array.end) {
// 			            // 이틀 이상 AllDay 일정인 경우 달력에 표기시 하루를 더해야 정상출력
// 			            array.end = moment(array.end).add(1, 'days');
// 			          }
// 			          return array;
// 			        });
// 			        $(fixedDate).each(function(){
// 			        	console.log("Test");
// 			        	console.log(this);
// 			        	calendar.fullCalendar('renderEvent', this);
// 			        });
// 			      }
// 			    });
		});
		
		
	});
	
	
</script>
<div id="frmContainer" style="height:100%;width:250px;float:left;margin-right:0;">
	    <form id="filterFrm">
	    	<label>업무 구분</label><br>
	    	<select name="wrk_is_mine" class="filter">
	    		<option value="all" selected>전체 업무</option>
	    		<option value="mine">내 업무만</option>
	    	</select>
	    	<br><br><hr>
	    	<label>작성일 기준</label><br>
	    	<select name="wrk_dt" class="filter">
	    		<option value="0" selected>전체</option>
	    		<option value="30">30일 이내</option>
	    		<option value="60">60일 이내</option>
	    		<option value="90">90일 이내</option>
	    	</select>
	    	<br><br><hr>
	    	<label>업무 주체</label>
	    	<br>
		    	<input type="checkbox" class="filter" name="wrk_i_assigned" value="y"> 내게 할당된 업무 <br>
		    	<input type="checkbox" class="filter" name="wrk_i_made" value="y">	내가 작성한 업무 <br>
		    	<input type="checkbox" class="filter" name="wrk_i_following" value="y"> 내가 팔로우한 업무 <br>
	    	<br><br><hr>
	    	<label>프로젝트 구분</label><br>
	    		<div id="prjList">
	    		</div>
	    	<br><br><hr>
	    	<label>마감일 기준</label><br>
		    	<input type="checkbox" class="filter" name="overdue" value="y"> 마감일 지남 <br>
		    	<input type="checkbox" class="filter" name="till_this_week" value="y"> 이번 주까지 <br>
		    	<input type="checkbox" class="filter" name="till_this_month" value="y"> 이번 달까지 <br>
		    	<input type="checkbox" class="filter" name="no_deadline" value="y"> 마감일 없음 <br>
	    	<br><br><hr>
	    	<label>업무 상태 구분</label><br>
		    	<input type="checkbox" class="filter" name="is_cmp" value="y"> 완료된 업무 <br>
		    	<input type="checkbox" class="filter" name="is_del" value="y"> 삭제된 업무 <br>
	    	<br><br><hr>
	    	<label>업무 작성자 구분</label><br>
	    		<div id="makerList">
						<c:forEach items="${mList}" var="MB">
							<input class='filter'type="checkbox" value="${MB.user_nm}" checked>${MB.user_nm}<br>
						</c:forEach>
	    		</div>
		    	<br>
		    	<button type="button" onclick="reset()">필터 초기화</button>
		    	<input type="hidden" name="is_cal" value="true">
		    	<input type="hidden" name="user_email" value="${USER_INFO.user_email }">
	    </form>
</div>
<!-- <div id="frmContainer"> -->
<!-- 	<form id="filterFrm"> -->
<!-- 		<label>업무 구분</label><br> -->
<!-- 		<select id="sel" name="wrk_is_mine" class="filter"> -->
<!-- 			<option value="all" selected>전체 업무</option> -->
<!-- 			<option value="mine">내 업무만</option> -->
<!-- 		</select> -->
<!-- 		<br><br><hr> -->
<!-- 		<label>프로젝트 구분</label><br> -->
<!-- 			<div id="prjList"> -->
<%-- 				<c:forEach items="${projectList}" var="PL"> --%>
<%-- 					<input type="checkbox" value="${PL.prj_id}" checked>${PL.prj_nm}<br> --%>
<%-- 				</c:forEach> --%>
<!-- 			</div> -->
<!-- 		<br><br><hr> -->
<!-- 		<label>업무 작성자 구분</label><br> -->
<!-- 			<div id="makerList"> -->

<!-- 			</div> -->
<!-- 			<br><br><br> -->
<!-- 	 	<button type="button" onclick="reset()">필터 초기화</button> -->
<%-- 		<input type="hidden" name="user_email" value="${USER_INFO.user_email}"> --%>
<!-- 	</form>	 -->
<!-- </div> -->

	<!-- 일자 클릭시 메뉴오픈 -->
	<div id="contextMenu" class="dropdown clearfix">
		<ul class="dropdown-menu dropNewEvent" role="menu"
			aria-labelledby="dropdownMenu"
			style="display: block; position: static; margin-bottom: 5px;">
			<li><a tabindex="-1" href="#">일정 등록 긔긔</a></li>
		</ul>
	</div>
	
	<div class="cal_wrapper">
		<div id="loading"></div>
		<div id="calendar"></div>
	</div>

	<!-- 	<input type="hidden" name="wrk_id" id="wrk_id" value=""> -->

	<!-- 일정 추가 MODAL -->
	<div class="modal fade" tabindex="-1" role="dialog" id="eventModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">

					<div class="row">
						<div class="col-xs-12">
							<label class="col-xs-4" for="edit-allDay">하루종일</label> <input
								class='allDayNewEvent' id="edit-allDay" type="checkbox">
						</div>
					</div>

					<div class="row">
						<div class="col-xs-12">
							<label class="col-xs-4" for="edit-title">일정 명</label> <input
								class="inputModal" type="text" name="wrk_nm" id="edit-title"
								required="required" />
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<label class="col-xs-4" for="edit-start">업무 시작일</label> <input
								class="inputModal" type="text" name="start_dt" id="edit-start" />
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<label class="col-xs-4" for="edit-end">업무 종료일</label> <input
								class="inputModal" type="text" name="end_dt" id="edit-end" />
						</div>
					</div>

					<div class="row">
						<div class="col-xs-12">
							<label class="col-xs-4" for="edit-type">프로젝트 리스트</label> 
							<select	class="inputModal" name="prj_id" id="edit-prj">
								<c:forEach items="${projectList}" var="PL">
									<option value="${PL.prj_id}">${PL.prj_nm}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="row">
						<div class="col-xs-12">
							<label class="col-xs-4" for="edit-type">업무 리스트</label>
							<select class="inputModal" name="wrk_lst_id" id="edit-type">
								<c:forEach items="${workList}" var="WL">
									<option value="${WL.wrk_lst_id}">${WL.wrk_lst_nm}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12">
							<label class="col-xs-4" for="edit-color">색상</label> <select
								class="inputModal" name="wrk_color_cd" id="edit-color">
								<option value="#D25565" style="color: #D25565;">빨간색</option>
								<option value="#9775fa" style="color: #9775fa;">보라색</option>
								<option value="#ffa94d" style="color: #ffa94d;">주황색</option>
								<option value="#74c0fc" style="color: #74c0fc;">파란색</option>
								<option value="#f06595" style="color: #f06595;">핑크색</option>
								<option value="#63e6be" style="color: #63e6be;">연두색</option>
								<option value="#a9e34b" style="color: #a9e34b;">초록색</option>
								<option value="#4d638c" style="color: #4d638c;">남색</option>
								<option value="#495057" style="color: #495057;">검정색</option>
							</select>
						</div>
					</div>
				</div>
				<div class="modal-footer modalBtnContainer-addEvent">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-primary" id="save-event">저장</button>
				</div>
				<div class="modal-footer modalBtnContainer-modifyEvent">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					<button type="button" class="btn btn-danger" id="deleteEvent">삭제</button>
					<button type="button" class="btn btn-primary" id="updateEvent">저장</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /.modal -->

<!-- /.container -->

<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/moment.min.js"></script>
<script src="/js/fullcalendar.min.js"></script>
<script src="/js/ko.js"></script>
<script src="/js/select2.min.js"></script>
<script src="/js/bootstrap-datetimepicker.min.js"></script>
<script src="/js/main.js?v=2"></script>
<script src="/js/addEvent.js"></script>
<script src="/js/editEvent.js"></script>
<script src="/js/etcSetting.js"></script>

