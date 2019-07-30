<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 프로젝트 업무 간트차트 -->
<script src="/js/dhtmlxgantt.js"></script>
<link rel="stylesheet" href="/css/dhtmlxgantt.css" type="text/css">
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
	    		</div>
	    	<br><br><hr>
	    	<label>팔로우한 멤버 구분</label><br>
	    		<div id="followerList">
	    		</div>
		    	<br>
		    	<button type="button" onclick="reset()">필터 초기화</button>
		    	<input type="hidden" name="user_email" value="${USER_INFO.user_email }">
	    </form>
</div>
<div id="gantt_here" style="width:900px; height:652px; overflow:hidden; padding:0px; margin:0px; float:left;"></div>
<script>
function search() {
	var serial = $("#filterFrm").serialize();
	console.log(serial);
	$.ajax({
		url: "/filter/prjgantt",
		type: "post",
		data: serial,
		success: function(data) {
			console.log(data);
			var filterFrm = data.resultMap.filterFrm;
			var result = data.resultMap.result;
			var prjList = data.resultMap.prjList;
			var makerList = data.resultMap.makerList;
			var followerList = data.resultMap.followerList;
			
			gantt.clearAll();
			var task = JSON.parse(result);
			gantt.parse(task);
			console.log(filterFrm);
		}
	})
}

function searchInit() {
	var serial = $("#filterFrm").serialize();
	console.log(serial);
	// ganttChart에 맞는 데이터 형태로 전송해줄 필요 있음
	$.ajax({
		url: "/filter/prjgantt",
		type: "post",
		data: serial,
		success: function(data) {
			console.log(data);
			var filterFrm = data.resultMap.filterFrm;
			var result = data.resultMap.result;
			var prjList = data.resultMap.prjList;
			var makerList = data.resultMap.makerList;
			var followerList = data.resultMap.followerList;
			
			var task = JSON.parse(result);
			
			gantt.parse(task);
			
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
// 			$.ajax({
// 				url: '/updateGantt',
// 				data: 
// 			});
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
		      {view: "scrollbar", scroll: "x", id:"scrollHor", height:20}
		    ]
		};
		gantt.config.date_format = "%Y-%n-%j %H:%i";
		gantt.init("gantt_here");
	}
	
	$(function(){
		searchInit();
		loadGantt();
	})
</script>