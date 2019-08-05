<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://uicdn.toast.com/tui.chart/latest/tui-chart.min.css">
<script src="https://uicdn.toast.com/tui.chart/latest/tui-chart-all.min.js"></script>
<script src="/js/toast-ui-chart.js"></script>
<div id="dateContainer" style="width:100%; height:120px;border:1px solid #e1e1e1;">
	<div id="start_dt" style="width:14%;height:120px;float:left;">시작일</div>
	<div id="end_dt" style="width:14%;height:120px;float:left;">마감일</div>
	<div id="cmp_dt" style="width:14%;height:120px;float:left;">완료일</div>
	<div id="elapsed_time" style="width:14%;height:120px;float:left;">경과 시간</div>
	<div id="remain_time" style="width:14%;height:120px;float:left;">남은 시간</div>
	<div id="cmp_wrk_cnt" style="width:14%;height:120px;float:left;">완료한 업무</div>
	<div id="not_cmp_wrk_cnt" style="width:14%;height:120px;float:left;">남은 업무</div>
</div>
<div class="spacing" style="width:100%;height:20px;"></div>
<div id="prj_barchart" style="height:130px; width:100%;border:1px solid #e1e1e1;">
</div>
<div class="spacing" style="width:100%;height:20px;"></div>
	<div id="prj_piechart" style="background-color:#e1e1e1;">
		<div id="pie_wrk_i_assigned" style="float:left; margin:5px;">
		<h3 style="text-align:center;">나에게 배정된 업무</h3>
			<div class="blank" style="width:350px; height:250px;text-align:center;">
				<div style="position:relative;top:50%;left:50%; transform:translate(-50%, -50%);">데이터 없음</div>
			</div>
		</div>
		<div id="pie_wrk_i_made" style="float:left; margin:5px;">
		<h3 style="text-align:center;">내가 작성한 업무</h3>
			<div class="blank" style="width:350px; height:250px;text-align:center;">
				<div style="position:relative;top:50%;left:50%; transform:translate(-50%, -50%);">데이터 없음</div>
			</div>
		</div>
		<div id="pie_wrk_i_follow" style="float:left; margin:5px;">
		<h3 style="text-align:center;">내가 팔로우하는 업무</h3>
			<div class="blank" style="width:350px; height:250px;text-align:center;">
				<div style="position:relative;top:50%;left:50%; transform:translate(-50%, -50%);">데이터 없음</div>
			</div>
		</div>
	</div>
<div class="spacing" style="width:100%;height:20px;"></div>
	<div id="barChartContainer" style="height:300px;width:1050px;">
		<div id="wrk_lst_barchart" style="width:100%; height:300px;">
		<h3 style="text-align:center;">업무리스트 별 개요</h3>
		</div>
	</div>
<form id="frm">
	<input type="hidden" name="user_email" value="${USER_INFO.user_email }">
	<input type="hidden" name="prj_id" value="1">
	<input type="hidden" name="wrk_dt" value="0">
	<input type="hidden" name="wrk_is_mine" value="all">
</form>
<script>
	$(function() {
		var serial = $("#frm").serialize();
		var assignContainer = document.getElementById("pie_wrk_i_assigned");
		var madeContainer = document.getElementById("pie_wrk_i_made");
		var followContainer = document.getElementById("pie_wrk_i_follow");
		var listChartContainer = document.getElementById("wrk_lst_barchart");
		console.log(serial);
		$.ajax({
			url: "/project/overview/ajax",
			type: "POST",
			data: serial,
			success: function(data){
				console.log(data);
				var assignData = data.result.assign;
				var followingData = data.result.following;
				var madeData = data.result.made;
				var assignChart = loadPieChart(assignContainer, assignData, 350, 250);
				var madeChart = loadPieChart(madeContainer, madeData, 350, 250);
				var followChart = loadPieChart(followContainer, followingData, 350, 250);
				var listData = data.result.list;
				assignData.isBlank == "true" ? hideChart(assignContainer) : showChart(assignContainer); 
				madeData.isBlank == "true" ? hideChart(madeContainer) : showChart(madeContainer); 
				followingData.isBlank == "true" ? hideChart(followContainer) : showChart(followContainer);
				var listChart = loadListChart(listChartContainer, listData, 1050, 300);
			}
		});
	});
	
	function showChart(chartContainer) {
		$(chartContainer).children(".blank").hide();
		$(chartContainer).children(".tui-chart").show();
	}
	function hideChart(chartContainer) {
		$(chartContainer).children(".tui-chart").hide();
		$(chartContainer).children(".blank").show();
	}
</script>