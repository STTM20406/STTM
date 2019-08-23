<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 사용자 별 전체 프로젝트 업무 간트차트 -->
<script src="/js/dhtmlxgantt.js"></script>
<link rel="stylesheet" href="/css/dhtmlxgantt.css" type="text/css">
<style>
	#filterFrm label { font-size:13px; font-weight: 500; }
	#filterFrm ul li label { cursor: pointer; }
	
</style>
<div class="sub_menu">
	<ul class="sub_menu_item">
		<li><a href="/overview/analysis">Work List</a></li>
		<li><a href="/calendarGet">Calendar</a></li>
		<li><a href="/gantt/overview">Gantt Chart</a></li>
	</ul>
	<div class="sub_btn">
	</div>
</div>
<section class="contents">
<h2>Gantt Chart</h2>
<div id="frmContainer" style="height:100%;width:20%;float:left;">
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
	    	<br><br><hr>
	    		<div id="makerList">
	    		</div>
	    	<br><br><hr>
	    		<div id="followerList">
	    		</div>
		    	<br>
		    	<button type="button" onclick="reset()">필터 초기화</button>
		    	<input type="hidden" name="user_email" value="${USER_INFO.user_email }">
	    </form>
</div>
<div id="gantt_here" style="width:1200px; height:680px; padding:0px; margin:0px; float:left;"></div>
</section>
<script>
function search() {
	var serial = $("#filterFrm").serialize();
	console.log(serial);
	$.ajax({
		url: "/filter/overgantt",
		type: "post",
		data: serial,
		success: function(data) {
			console.log(data);
			var filterFrm = data.filterFrm;
			var result = data.result;
			var prjList = data.prjList;
			var makerList = data.makerList;
			var followerList = data.followerList;
			console.log(data);
			gantt.clearAll();
// 			var task = JSON.parse(result);
			gantt.parse(result);
			console.log(filterFrm);
		}
	})
}

function searchInit() {
	var serial = $("#filterFrm").serialize();
	console.log(serial);
	// ganttChart에 맞는 데이터 형태로 전송해줄 필요 있음
	$.ajax({
		url: "/filter/overgantt",
		type: "post",
		data: serial,
		success: function(data) {
			console.log(data);
			var filterFrm = data.filterFrm;
			var result = data.result;
			var prjList = data.prjList;
			var makerList = data.makerList;
			var followerList = data.followerList;
			console.log(data);
// 			var task = JSON.parse(result);
			
			gantt.parse(result);
			
			$("#prjList").html(prjList);
			$("#makerList").html(makerList);
			$("#followerList").html(followerList);
		}
	})
}

function workDetail(wrk_id){
	console.log(wrk_id);
	$.ajax({
		url: "/filter/detail",
		type: "post",
		data: wrk_id,
		success: function(data){
			var workDetail = data.workDetail;
			console.log(workDetail);
			
			$("#work_detail").html(workDetail);
		}
	});
}	
	$("#frmContainer").on("change", ".filter", function(){
		search();
	});
	
	$("#frmContainer").on("reset", "#filterFrm", function(){
		$("#filterFrm select").prop("selectedIndex", 0);
		$("#filterFrm input[type=checkbox]").prop("checked", false);	
		search();
	})
	function format(time) {
		var date = new Date(time);
		var str = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate(); 
		return str;
	}
	function loadGantt() {
		gantt.config.columns=[{name:"text",label:"이름",tree:true, width:'250'}];
		gantt.config.drag_links = false;
		gantt.config.drag_progress = false;
		gantt.config.show_unscheduled = true;
		gantt.config.row_height = 25;
		gantt.config.details_on_dblclick = false;
		gantt.config.scales = [
			{unit: "month", step: 1, format: "%Y년 %n월"},
			{unit: 'week', step: 1, format: "%W번째 주"},
		    {unit: "day", step: 1, format: "%j일", css: function(date) {
		    if(!gantt.isWorkTime({ date: date, unit: "day"})){
		            return "weekend";
		        }
		    }}
		];
		gantt.attachEvent("onAfterTaskDrag", function(id, mode, e){
			var target = gantt.getTask(id);
			console.log(target);
			console.log(target.duration);
			console.log(mode);
			console.log(target.id);
			console.log(target.start_date.getTime());
			$.ajax({
				url: '/gantt/update',
				data: {
						'id': target.id,
						'start_date': format(target.start_date.getTime()),
						'end_date': format(target.end_date.getTime()),
						'duration': target.duration,
						'mode': mode
					},
				type: "POST",
				success: function() {
					console.log("Update Done");
				}
			});
		});
		
		gantt.attachEvent("onTaskRowClick", function(id, row) {
			console.log(id);
			console.log(row);
		});
		
		gantt.config.scale_height = 50;
		gantt.config.layout = {css: "gantt_container",
		    rows:[{
		        cols: [
		          {view: "grid", id: "grid", scrollX:"scrollHor", scrollY:"scrollVer"},
		          {resizer: true, width: 1},
		          {view: "timeline", id: "timeline", scrollX:"scrollHor", scrollY:"scrollVer"},
		          {view: "scrollbar", scroll: "y", id:"scrollVer"}
		        ]
		       },
		      {view: "scrollbar", scroll: "x", id:"scrollHor"}
		    ]
		};
		gantt.config.date_format = "%Y-%n-%j %H:%i";
		gantt.init("gantt_here");
	}
	
	$(function(){
		searchInit();
		loadGantt();
		$("#filterFrm ul li p").hide();
		  // $("ul > li:first-child a").next().show();
		  $("#filterFrm").on("click", "ul li label", function(){
		    $(this).siblings().slideToggle(300);
		    // $(this).next().slideDown(300);
// 		    $("ul li span").not(this).next().slideUp(300);
		    return false;
		  });
// 		  $("#filterFrm ul li span").eq(0).trigger("click");
	})
</script>