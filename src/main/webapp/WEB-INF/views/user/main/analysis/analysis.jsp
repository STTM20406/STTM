<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://uicdn.toast.com/tui.chart/latest/tui-chart.min.css">
<script src="https://uicdn.toast.com/tui.chart/latest/tui-chart-all.min.js"></script>
<script src="/js/toast-ui-chart.js"></script>

<!-- flatpickr.js 시작 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://npmcdn.com/flatpickr/dist/l10n/ko.js"></script>
<!-- flatpicker.js 끝 -->
<div class="sub_menu">
	<ul class="sub_menu_item">
		<li><a href="/work/list">Work</a></li>
		<li><a href="/gantt/project">Gantt Chart</a></li>
		<li><a href="/analysis">Work Analysis</a></li>
		<li><a href="/publicFilePagination">File&amp;Link</a></li>
		<li><a href="/meeting/view">Meeting</a></li>
		<li><a href="/vote">Vote</a></li>
		<li><a href="/conferenceList">Minutes</a></li>
	</ul>
</div>
<!-- Include 끝 -->
<section class="contents">
<h2>${PROJECT_INFO.prj_nm } > Analysis</h2>
<div id="prj_list_container">
	<form id="prj_list_frm">
	<input type="hidden" name="over_prj_id" value="${PROJECT_INFO.prj_id }">
	<input type="hidden" name="user_email" value="${USER_INFO.user_email }">
	<input type="hidden" name="wrk_dt" value="0">
	<input type="hidden" name="wrk_is_mine" value="all">
</form>
</div>
<div id="dateContainer">
<h3>프로젝트 개요</h3>
	<div id="start_dt" class="midCont" >
			<p class="midCont_p">시작일</p>
		<div class="dt">
		<!-- pick를 사용할 input -->
				<input id="st_dt" name="start_dt" data-input/>
				<a class="input-button" title="clear" data-clear>
					X
				</a>
		</div>
	</div>
	<div id="end_dt" class="midCont" >
			<p class="midCont_p">마감일</p>
		<div class="dt">
			<input id="ed_dt" name="end_dt" data-input/>
			<a class="input-button" title="clear" data-clear>
				X
			</a>
		</div>
	</div>
	<div id="cmp_dt" class="midCont">
			<p class="midCont_p">완료일</p>
		<div class="dt">
			<input id="cp_dt" name="cmp_dt" data-input/>
			<a class="input-button" title="clear" data-clear>
				X
			</a>
		</div>
	</div>
	<div id="elapsed_time" class="midCont">
			<p class="midCont_p">경과 시간</p>
		<div class="timeDiv">
			<p id="elap" class="agn_cent">-</p>
		</div>
	</div>
	<div id="remain_time" class="midCont" >
			<p class="midCont_p">남은 시간</p>
		<div class="timeDiv">
			<p id="remain" class="agn_cent">-</p>
		</div>
	</div>
	<div id="cmp_wrk_cnt" class="midCont" >
			<p class="midCont_p">완료한 업무</p>
		<div class="wrkDiv">
			<p id="done" class="agn_cent">-</p>
		</div>
	</div>
	<div id="not_cmp_wrk_cnt" class="midCont">
			<p class="midCont_p">남은 업무</p>
		<div class="wrkDiv">
			<p id="undone" class="agn_cent">-</p>
		</div>
	</div>
</div>
<div id="prj_barchart">

</div>
	<div id="prj_piechart">
		<div id="pie_wrk_i_assigned" >
		<h3 class="agn_cent">나에게 배정된 업무</h3>
			<div class="blank">
				<div class="no_dataDiv">데이터 없음</div>
			</div>
		</div>
		
		<div id="pie_wrk_i_made">
		<h3 class="agn_cent">내가 작성한 업무</h3>
			<div class="blank">
				<div class="no_dataDiv">데이터 없음</div>
			</div>
		</div>
		
		<div id="pie_wrk_i_follow">
		<h3 class="agn_cent">내가 팔로우하는 업무</h3>
			<div class="blank">
				<div class="no_dataDiv">데이터 없음</div>
			</div>
		</div>
	</div>
		<div id="wrk_lst_barchart">
		<p class="agn_cent"><b>업무리스트 별 개요</b></p>
		</div>
</section>

<script>
var cal = null;
var assignChart = null;
var madeChart = null;
var followChart = null;
var listChart = null;
var progressChart = null;
var currPrjVo = null;

	function loadPrjList() {
		var user_email = $("#prj_list_frm input[name=user_email]").val();
		$.ajax({
			url: "/analysis/prjList",
			data: {"user_email": user_email},
			type: "post",
			success: function(data){
				console.log(data);
				var options = "";
				$(data).each(function(){
					options += this;
				})
				$("#prj_list").html(options);
				$("#prj_list").val(${PROJECT_INFO.prj_id});
			}
		});
	}
	function loadPrjOverview(serial) {
		$.ajax({
			url: "/analysis/ajax",
			type: "POST",
			data: serial,
			success: function(data){
				console.log(data);
				
				var assignContainer = document.getElementById("pie_wrk_i_assigned");
				var madeContainer = document.getElementById("pie_wrk_i_made");
				var followContainer = document.getElementById("pie_wrk_i_follow");
				var listChartContainer = document.getElementById("wrk_lst_barchart");
				var progressContainer = document.getElementById("prj_barchart"); 
				
				var assignData = data.result.assign;
				var followingData = data.result.following;
				var madeData = data.result.made;
				
				if(assignChart == null) {
					assignChart = loadPieChart(assignContainer, assignData, 487, 250);
				} else {
					assignChart["chartContainer"].remove();
					assignChart = loadPieChart(assignContainer, assignData, 487, 250);
				}
				if(madeChart == null){
					madeChart = loadPieChart(madeContainer, madeData, 487, 250);
				} else {
					madeChart["chartContainer"].remove();
					madeChart = loadPieChart(madeContainer, madeData, 487, 250);
				}
				
				if(followChart == null) {
					followChart = loadPieChart(followContainer, followingData, 487, 250);
				} else {
					followChart["chartContainer"].remove();
					followChart = loadPieChart(followContainer, followingData, 487, 250);
				}
					
				var listData = data.result.list;
				assignData.isBlank == "true" ? hideChart(assignContainer) : showChart(assignContainer); 
				madeData.isBlank == "true" ? hideChart(madeContainer) : showChart(madeContainer); 
				followingData.isBlank == "true" ? hideChart(followContainer) : showChart(followContainer);
				if(listChart == null) {
					listChart = loadListChart(listChartContainer, listData, 1200, 300);
				} else {
					listChart["chartContainer"].remove();					
					listChart = loadListChart(listChartContainer, listData, 1200, 300);
				}
				
				progressData = data.result.progress;
				
				if(progressChart == null) {
					progressChart = loadProgressChart(progressContainer, progressData, 1200, 150);
				} else {
					progressChart["chartContainer"].remove();
					progressChart = loadProgressChart(progressContainer, progressData, 1200, 150);
				}
				
				var prjVo = data.result.prjVo;
				setPrjDates(prjVo);
				
				currPrjVo = prjVo;
				var cnt = data.result.cnt;
				setCnts(cnt);
				
				var auth = data.result.auth;
				if(auth=="NO") {
					$(cal[0].input).attr("disabled", "true");
// 					$(cal[0].input).css("margin-left", "45px");
					$(cal[0].input).next().remove();
					$(cal[1].input).attr("disabled", "true");
					$(cal[1].input).next().remove();
// 					$(cal[1].input).css("margin-left", "45px");
					$(cal[2].input).attr("disabled", "true");
// 					$(cal[2].input).css("margin-left", "45px");
					$(cal[2].input).next().remove();
				}
					
				
			}
		});
	}
	function showChart(chartContainer) {
		$(chartContainer).children(".blank").hide();
		$(chartContainer).children(".tui-chart").show();
	}
	function hideChart(chartContainer) {
		$(chartContainer).children(".tui-chart").hide();
		$(chartContainer).children(".blank").show();
	}
	function setElapDay() {
		var st_dt = $("#st_dt").val();
		var ed_dt = $("#ed_dt").val();
		var today = new Date();
		var today_str = today.getFullYear()+"-"+(today.getMonth()+1)+"-"+today.getDate();
		var elapDay = "-";
		
		if(st_dt) {
			var st = new Date(st_dt);
			st.setHours(0);
			var isPast = new Date(today_str) - st; 
			if(isPast<0)
				elapDay = "-";
			else
				elapDay = Math.floor(isPast/(1000*60*60*24)) + "일";
			
			if(ed_dt) {
				ed = new Date(ed_dt);
				ed.setHours(0);
				var elapPercent = "";
				var elapVal = Math.round(((Math.floor( (new Date(today_str) - st) / (1000*60*60*24) )) / (Math.floor((ed-st)/(1000*60*60*24))) ) * 100)
				if((ed-st) == 0) {
					
				} else if((new Date(today_str)-st)<0){
					
				} else if(elapVal>100){
					
				} else {
					elapPercent = "(" + elapVal + "%)";
				}
				
				elapDay = elapDay + elapPercent; 
			}
		}
		$("#elap").text(elapDay);
	}
	function setRemainDay() {
		var ed_dt = $("#ed_dt").val();
		var st_dt = $("#st_dt").val();
		var today = new Date();
		var today_str = today.getFullYear()+"-"+(today.getMonth()+1)+"-"+today.getDate();
		var remainDay = "-";
		
		if(ed_dt) {
			var ed = new Date(ed_dt);
			ed.setHours(0);
			var isPast = ed - new Date(today_str);
			if(isPast<0)
				remainDay = "-";
			else
				remainDay = Math.floor(isPast/(1000*60*60*24)) + "일";
			
			if(st_dt) {
				st = new Date(st_dt);
				st.setHours(0);
				var remainPercent = "";
				var remainVal = Math.round(100 - (((Math.floor( (new Date(today_str) - st) / (1000*60*60*24) )) / (Math.floor((ed-st)/(1000*60*60*24))) ) * 100 ));
				if((ed-st)==0) {
				
				} else if(remainVal>100) {
					
				} else if(remainVal<0) {
					
				}else {
					remainPercent = "(" + remainVal + "%)";
				}
				remainDay = remainDay + remainPercent;
			}
		}
		$("#remain").text(remainDay);
	}
	function setPrjDates(prjVo) {
		console.log(prjVo);
		var st_str = prjVo.prjStartDtStr;
		var ed_str = prjVo.prjEndDtStr;
		var cmp_str = prjVo.prjCmpDtStr;
		
		var cal_st = cal[0];
		var cal_ed = cal[1];
		var cal_cmp = cal[2];
		
		var st_is_blank = cal[0].selectedDates.length == 0 ? true : false;
		var ed_is_blank = cal[1].selectedDates.length == 0 ? true : false;
		var cmp_is_blank = cal[2].selectedDates.length == 0 ? true : false;
	
// 		if(st_is_blank && ed_is_blank && cmp_is_blank){
			cal_st.setDate(st_str);
			cal_ed.setDate(ed_str);
			cal_cmp.setDate(cmp_str);
			setElapDay();
			setRemainDay();
// 		}
	}
	function setCnts(cnt) {
		var doneCnt = cnt.doneCnt;
		var undoneCnt = cnt.undoneCnt;
		var donePercent = cnt.donePercent;
		var undonePercent = cnt.undonePercent;
		
		var done = doneCnt + "개 (" + donePercent + "%)";
		var undone = undoneCnt + "개 (" + undonePercent + "%)";
		
		$("#done").text(done);
		$("#undone").text(undone);
	}
	function updatePrj() {
		var cal_st = cal[0];
		var cal_ed = cal[1];
		var cal_cmp = cal[2];
		
		var prj_st_dt = cal_st.selectedDates[0];
		var prj_ed_dt = cal_ed.selectedDates[0];
		var prj_cmp_dt = cal_cmp.selectedDates[0];
		
		var prjVo = {
				'prj_id': $("input[name='over_prj_id']").val(),
				'prj_start_dt': prj_st_dt,
				'prj_end_dt': prj_ed_dt,
				'prj_cmp_dt': prj_cmp_dt
				};
		console.log(prjVo);
		$.ajax({
			url: '/analysis/updatePrj',
			type: 'post',
			data: prjVo,
			success: function(data){
				currPrjVo = data;
			}
		});
	}
	
	$(function() {
// 		loadPrjList();	
	});
</script>
<script>
$(function() {
	cal = flatpickr(".dt", {"locale" : "ko", wrap: true}); // 한국어 설정
	
	$(".dt input").on("change", function() {
		// 체크해야할 경우의 수
		// 1. 시작일이 마감일보다 나중
		// 2. 완료일이 시작일보다 이전
		var valid = validateDt();
		console.log(valid);
		if(valid) {
			setElapDay();
			setRemainDay();
			updatePrj();
		}
	});
	$("#prj_list_container").on("change", "#prj_list", function() {
		var serial = $("#prj_list_frm").serialize();
		loadPrjOverview(serial);
	});
	var serial = $("#prj_list_frm").serialize();
	console.log(serial);
	loadPrjOverview(serial);
});
function validateDt() {
	var valid = true;
	
	if(currPrjVo)
		console.log(currPrjVo);
		
	var cal_st = cal[0];
	var cal_ed = cal[1];
	var cal_cmp = cal[2];
	
	var prj_st_dt = cal_st.selectedDates[0];
	var prj_ed_dt = cal_ed.selectedDates[0];
	var prj_cmp_dt = cal_cmp.selectedDates[0];
	
	if(prj_st_dt)
		var st_dt = prj_st_dt.getTime();
	if(prj_ed_dt)
		var ed_dt = prj_ed_dt.getTime();
	if(prj_cmp_dt)
		var cmp_dt = prj_cmp_dt.getTime();
	
	if(prj_st_dt && prj_cmp_dt ) {
		if(st_dt > cmp_dt ) {
			valid = false;
// 			alert("완료일은 시작일 이전으로 설정할 수 없습니다.");
			$(".ctxt").text("완료일은 시작일 이전으로 설정할 수 없습니다");
			layer_popup("#layer2");
			cal_st.setDate(currPrjVo.prj_start_dt);
			cal_cmp.setDate(currPrjVo.prj_cmp_dt);
		}
	}
	
	if(prj_st_dt && prj_ed_dt) {
		if(st_dt > ed_dt) {
			valid = false;
// 			alert("시작일은 마감일 이전으로 설정할 수 없습니다.");
			$(".ctxt").text("시작일은 마감일 이전으로 설정할 수 없습니다.");
			layer_popup("#layer2");
			cal_st.setDate(currPrjVo.prj_start_dt);
			cal_ed.setDate(currPrjVo.prj_end_dt);
		}
	}
	
	return valid;
}
</script>