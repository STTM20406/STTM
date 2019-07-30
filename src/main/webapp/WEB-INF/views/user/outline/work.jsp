<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- ToastUI Chart load -->
<link rel="stylesheet" href="https://uicdn.toast.com/tui.chart/latest/tui-chart.min.css">
<script src="https://uicdn.toast.com/tui.chart/latest/tui-chart-all.min.js"></script>
<script src="/js/toast-ui-chart.js"></script>
<div id="frmContainer" style="height:100%;width:250px;float:left;">
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
        <div id="resultContainer" style="width:450px;padding:25px;height:95%; float:left;"></div>
        <div id="chartContainer" style="width:550px;padding:25px;height:95%;float:left;">
        	<div id="pieChartContainer"></div>
        	<div id="priorChartContainer"></div>
        	<div id="percentChartContainer"></div>
        	<div id="blankContainer" style="font-size:large;width:550px;height:600px;text-align:center;padding:225px;">
        		<p>데이터 없음</p>
        	</div>
        </div>
        <div id="work_detail" style="width:600px;padding:25px;height:100%;float:left;"></div>
<script>
	var percentChart = null;
	var priorChart = null;
	var pieChart = null;
	
	function search() {
		var serial = $("#filterFrm").serialize();
		console.log(serial);
		$.ajax({
			url: "/filter/ajax",
			type: "post",
			data: serial,
			success: function(data) {
				console.log(data);
				var filterFrm = data.resultMap.filterFrm;
				var result = data.resultMap.result;
				var prjList = data.resultMap.prjList;
				var makerList = data.resultMap.makerList;
				var followerList = data.resultMap.followerList;
				
				var pieChartData = data.resultMap.pieChartData;
				var percentChartData = data.resultMap.percentChartData;
				var priorChartData = data.resultMap.priorChartData;
				
				var isBlank = data.resultMap.isBlank;
				if(isBlank=="true") {
					hideChart();
				} else {
					showChart();
				}
				var pieChartContainer = document.getElementById('pieChartContainer');
				var priorChartContainer = document.getElementById('priorChartContainer');
				var percentChartContainer = document.getElementById('percentChartContainer');
					pieChart.setData(JSON.parse(pieChartData));
					percentChart.setData(JSON.parse(percentChartData));
					priorChart.setData(JSON.parse(priorChartData));
// 				loadPieChart(pieChartContainer, pieChartData);
// 				loadBarChart(priorChartContainer, percentChartContainer, priorChartData, percentChartData);
				console.log(filterFrm);
				$("#resultContainer").html(result);
				
				
			}
		})
	}
	
	function searchInit() {
		var serial = $("#filterFrm").serialize();
		console.log(serial);
		$.ajax({
			url: "/filter/ajax",
			type: "post",
			data: serial,
			success: function(data) {
				console.log(data);
				var filterFrm = data.resultMap.filterFrm;
				var result = data.resultMap.result;
				var prjList = data.resultMap.prjList;
				var makerList = data.resultMap.makerList;
				var followerList = data.resultMap.followerList;
				var pieChartData = data.resultMap.pieChartData;
				var percentChartData = data.resultMap.percentChartData;
				var priorChartData = data.resultMap.priorChartData;
				
				$("#blankContainer").hide();

				var isBlank = data.resultMap.isBlank;
				if(isBlank=="true") {
					hideChart();
				} else {
					showChart();
				}
				
				$("#resultContainer").html(result);
// 				$("#frmContainer").html(filterFrm);
				$("#prjList").html(prjList);
				$("#makerList").html(makerList);
				$("#followerList").html(followerList);
				loadBarChart(priorChartContainer, percentChartContainer, priorChartData, percentChartData);
				loadPieChart(pieChartContainer, pieChartData);
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
	
		$("#resultContainer").on("click", ".result", function(){
			var wrk_id = $(this).data(wrk_id);
			workDetail(wrk_id);	
		});
		
		$("#frmContainer").on("change", ".filter", function(){
			search();
		});
		
		$("#frmContainer").on("reset", "#filterFrm", function(){
			$("#filterFrm select").prop("selectedIndex", 0);
			$("#filterFrm input[type=checkbox]").prop("checked", false);	
			search();
		})
	$(function(){
		searchInit();		
	})
	function hideChart() {
		var blank = $("#blankContainer");
		$("#pieChartContainer").hide();		
		$("#percentChartContainer").hide();		
		$("#priorChartContainer").hide();		
		$(blank).show();
	}
	function showChart() {
		var blank = $("#blankContainer");
		$("#pieChartContainer").show();		
		$("#percentChartContainer").show();		
		$("#priorChartContainer").show();		
		$(blank).hide();
	}
</script>
<script>
</script>