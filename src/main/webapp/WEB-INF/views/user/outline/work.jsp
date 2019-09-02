<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- ToastUI Chart load -->
<link rel="stylesheet" href="https://uicdn.toast.com/tui.chart/latest/tui-chart.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://uicdn.toast.com/tui.chart/latest/tui-chart-all.min.js"></script>
<script src="/js/toast-ui-chart.js"></script>

<div class="sub_menu">
	<ul class="sub_menu_item">
		<li><a href="/overview/analysis">Work</a></li>
		<li><a href="/calendarGet">Calendar</a></li>
		<li><a href="/gantt/overview">Gantt Chart</a></li>
	</ul>
</div>


<section class="contents">
	<h2 class="contentTitle">업무 개요</h2>
		<div id="allContainer">
<div id="frmContainer">
	    <form id="filterFrm">
	    	<select name="wrk_is_mine" class="filter">
	    		<option value="all" selected>전체 업무</option>
	    		<option value="mine">내 업무만</option>
	    	</select>
	    	<select name="wrk_dt" class="filter">
	    		<option value="0" selected>전체</option>
	    		<option value="30">30일 이내</option>
	    		<option value="60">60일 이내</option>
	    		<option value="90">90일 이내</option>
	    	</select>
		    	<input type="hidden" name="user_email" value="${USER_INFO.user_email}">
	    </form>
</div>	
	        <div id="resultContainer" >
	        </div>
	        <div id="chartContainer">
	        	<div id="pieChartContainer"></div>
	        	<div id="priorChartContainer"></div>
	        	<div id="percentChartContainer"></div>
	        </div>
	       	<div class="blankContainer">
	       		<p>데이터 없음</p>
	       	</div>
		</div>
		
	
<!-- work setting layer -->
<div id="propertyWorkSet" class="propertySet">
	<div class="propertySetWrap">
		<div class="setHd">
			<div class="setHdTitle">
				<input type="hidden"  id="wps_id" name="wps_id" value="">
				<input type="hidden"  id="wps_wrk_id" name="wps_wrk_id" value="">
				<h2>
					<input type="text" id="wps_nm" name="wps_nm" value="">
				</h2>
			</div>
			<p class="wrk_update"><span>작성자</span><em id="wps_write_nm"></em><span>작성일</span><em id="wps_write_date" ></em></p>
		</div>
		
		<!-- work tab -->
		<div class="workTab">
			<ul class="tabs">
				<li class="active" data-tab="tab1">설정</li>
				<li id="commentId"data-tab="tab2">업무 코멘트</li>
				<li id="FL" data-tab="tab3">파일&amp;링크</li>
			</ul>
		</div>
		
		<!-- 여기서부터 property setting layer contents -->
		<!-- 업무설정 : tab1 / 업무코멘트 : tab2 / 업무파일 : tab3 -->
		
		<div class="setCon tab_content" id="tab1">
			<dl class="setItem">
				<dt>날짜 설정</dt>
				<dd>
					<input class="flatpickr flatpickr-input" type="text" placeholder="Select Date.." data-id="rangeDisable" id="wps_start_date" readonly="readonly">
				</dd>
			</dl>
			<dl class="setItem notifyBox">
				<dt>예약 알림</dt>
				<dd class="notifyCon"><input type="button" id="resNotifyBtn" value="예약알림추가"></dd>
				<dd class="notifyArea">
					<input class="flatpickr1 flatpickr-input" type="hidden" placeholder="Select Date.." data-id="rangeDisable" id="wps_res_date" readonly="readonly">
					<div class="notification" tabindex="-1">
						<h2>알림 받을 멤버</h2>
						<select id="notificationMem" name="notificationMem">
							<option value="나에게">나에게</option>
							<option value="배정된 멤버">배정된 멤버</option>
							<option value="팔로워 멤버">팔로워 멤버</option>
							<option value="모두">배정된 멤버 &amp; 팔로워 멤버</option>
						</select>
						<div class="notificationBtnArea">
							<input type="button" id="notificationCancelBtn" value="취소하기">
							<input type="button" id="notificationAddBtn" value="추가하기">
						</div>
					</div>
				</dd>
			</dl>
			<dl class="setItem">
				<dt>배정된 멤버</dt>
				<dd>
					<button type="button" id="wps_mem_set" name="wps_mem_set">멤버 추가</button>

					<!-- 프로젝트 관리자 리스트 box -->
					<ul class="wrk_add_box"></ul>

					<div class="wrk_add_mem">
						<label for="wrk_mem">배정된 멤버 추가</label>
						<input type="button" value="닫기" id="memClose" class="close">
						<div class="wrk_mem_list">
							<div class="wrk_mem_sch">
								<fieldset id="hd_sch">
									<legend>멤버 검색 검색</legend>
									<input type="text" name="wrk_mem" id="wrk_mem" maxlength="20" placeholder="검색어를 입력해주세요">
								</fieldset>
							</div>

							<!-- 추가된 프로젝트 관리자 리스트 box -->
							<ul class="wrk_mem_item"></ul>
						</div>
					</div>
				</dd>
			</dl>
			<dl class="setItem">
				<dt>팔로워</dt>
				<dd>
					<button type="button" id="wrk_flw_set" name="wrk_flw_set">팔로워 추가</button>

					<!-- 프로젝트 멤버 리스트 box -->
					<ul class="wrk_mem_flw_box"></ul>

					<div class="wrk_add_flw">
						<label for="wrk_flw">팔로워 추가</label>
						<input type="button" value="닫기" id="flwClose" class="close">
						<div class="wrk_flw_list">
							<div class="wrk_flw_sch">
								<fieldset id="hd_sch">
									<legend>멤버 검색</legend>
									<input type="text" name="wrk_flw" id="wrk_flw" maxlength="20" placeholder="검색어를 입력해주세요">
								</fieldset>
							</div>

							<!-- 추가된 프로젝트 멤버 리스트 box -->
							<ul class="wrk_flw_item_list"></ul>
						</div>
					</div>
				</dd>
			</dl>
			<dl class="setItem">
				<dt>중요도</dt>
				<dd>
					<div class="setGrade">
						<select id="wrk_gd" name="wrk_gd">
							<option value="A">A</option>
							<option value="B">B</option>
							<option value="C">C</option>
							<option value="D">D</option>
							<option value="E">E</option>
						</select>
					</div>
				</dd>
			</dl>
			
			<dl class="setItem">
				<dt>라벨 색상</dt>
				<dd>
					<div class="lableColor">
						<ul>
							<li>
								<label for="7d3bff" class="wrk_color wrk_color01" style="background:#7d3bff"></label>
								<input type="radio" value="#7d3bff" name="wrk_color_cd" id="7d3bff">
							</li>
							<li>
								<label for="cf5de1" class="wrk_color wrk_color02" style="background:#cf5de1"></label>
								<input type="radio" value="#cf5de1" name="wrk_color_cd" id="cf5de1">
							</li>
							<li>
								<label for="75dfff" class="wrk_color wrk_color03" style="background:#75dfff"></label>
								<input type="radio" value="#75dfff" name="wrk_color_cd" id="75dfff">
							</li>
							<li>
								<label for="287cff" class="wrk_color wrk_color04" style="background:#287cff"></label>
								<input type="radio" value="#287cff" name="wrk_color_cd" id="287cff">
							</li>
							<li>
								<label for="ffe604" class="wrk_color wrk_color05" style="background:#ffe604"></label>
								<input type="radio" value="#ffe604" name="wrk_color_cd" id="ffe604">
							</li>
							<li>
								<label for="ff8b03" class="wrk_color wrk_color06" style="background:#ff8b03"></label>
								<input type="radio" value="#ff8b03" name="wrk_color_cd" id="ff8b03">
							</li>
							<li>
								<label for="de4439" class="wrk_color wrk_color07" style="background:#de4439"></label>
								<input type="radio" value="#de4439" name="wrk_color_cd" id="de4439">
							</li>
							<li>
								<label for="0b16c6" class="wrk_color wrk_color08" style="background:#0b16c6"></label>
								<input type="radio" value="#0b16c6" name="wrk_color_cd" id="0b16c6">
							</li>
							<li>
								<label for="ff2f77" class="wrk_color wrk_color09" style="background:#ff2f77"></label>
								<input type="radio" value="#ff2f77" name="wrk_color_cd" id="ff2f77">
							</li>
							<li>
								<label for="3d434f" class="wrk_color wrk_color10" style="background:#3d434f"></label>
								<input type="radio" value="#3d434f" name="wrk_color_cd" id="3d434f">
							</li>
						</ul>
					</div>
				</dd>
			</dl>
		</div>
		
		<!--  여기서부터 work comment -->
		<form id="frm02">
		<div id="tab2" class="tab_content">
			<div class="tableWrap">
				<table class="tb_style_02">
					<colgroup>
						<col width="50%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
					</colgroup>
					<thead>
						<tr>
							<th>내용</th>
							<th>아이디</th>
							<th>작성일</th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody id="commBody">

					</tbody>
				</table>
			</div>
			<div class="commWrite">
				<input type="text" id="comm_content" name="comm_content" placeholder="댓글을 작성해 주세요." maxlength="70" style="width : 70%">
				<button type="button" name="replyBtn" id="replyBtn"> 댓글등록 </button>
			</div>
			<div class="pagination">
			</div>
		</div>
		</form>
		
		<!--  여기서부터 work file&link-->
		<!-- 영하가 수정함 여기부터 ㅎ-->
		<div id="tab3" class="tab_content">
			<div class="tab_sub_menu">
				<ul class="tabs">
					<li id="fileList">파일</li>
					<li id="linkList">링크</li>
				</ul>
			</div>
			
			<div id="locker">
				<input value="public" name="box" type="radio">공유함 긔긔
				<input value="individual" name="box" type="radio">개인함 긔긔
				<input value="both" name="box" type="radio">둘다 긔긔
			</div>

			<form id="frm" action="/workFileUpload" method="post" enctype="multipart/form-data">
				<div id="workFile">
					<input type="hidden" id="work" value="" name="wrk_id">
					<input type="hidden" id="box" value="" name="locker">
					<label class="col-sm-2 control-label">공유할 첨부파일:</label>
					<input type="file" class="file" name="profile"/>
					<button type="button" id="uploadFile">등록</button>
				</div>
			</form>
			
			<div id="workLink">
				<label class="col-sm-2 control-label">공유할 링크주소:</label>
				<input type="text" class="link" name="attch_url"/>
				<button type="button" id="uploadLink">등록</button>
			</div>
			
			<div class="tab_con">
	
				<div class="tab-content current">
					<div class="fileWrap">
						<table class="tb_style_03">
							<colgroup>
								<col width="30%">
								<col width="20%">
								<col width="30%">
								<col width="20%">
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
		<!-- 영하가 수정함 여기까지ㅎ -->
		
	</div>
	<div class="btnSetClose">닫기</div>
</div>
</section>
<script src="/js/work.js"></script>
<script>
	var percentChart = null;
	var priorChart = null;
	var pieChart = null;
	var projectAuth= "${PROJECT_INFO.prj_auth}";
	var projectMemLevel ="${userInfo.prj_mem_lv}";
	
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
				var blank = $(".blankContainer");
				blank.hide();
				var isBlank = data.resultMap.chartData.isBlank;
				if(isBlank=="true") {
					hideChart();
				} else {
					showChart();
				}
				var pieChartData = data.resultMap.chartData.pieChart;
				var priorData = data.resultMap.chartData.barChart.priorChart;
				var percentData = data.resultMap.chartData.barChart.percentChart;
				var pieChartContainer = document.getElementById('pieChartContainer');
				var priorChartContainer = document.getElementById('priorChartContainer');
				var percentChartContainer = document.getElementById('percentChartContainer');
					pieChart["chartContainer"].remove();
					pieChart = loadPieChart(pieChartContainer, pieChartData, 600, 300);
					percentChart["chartContainer"].remove();
					percentChart = loadPercentChart(percentChartContainer, percentData, 600, 200);
					priorChart["chartContainer"].remove();
					priorChart = loadPriorChart(priorChartContainer, priorData, 600, 200);
// 				console.log(filterFrm);
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
				var pieChartData = data.resultMap.chartData.pieChart;
				var priorData = data.resultMap.chartData.barChart.priorChart;
				var percentData = data.resultMap.chartData.barChart.percentChart;
				var blank = $(".blankContainer");
				blank.hide();

				var isBlank = data.resultMap.chartData.isBlank;
				if(isBlank=="true") {
					hideChart();
				} else {
					showChart();
				}
				
				$("#resultContainer").html(result);
				$("#frmContainer").html(filterFrm);
				$("#prjList").html(prjList);
				$("#makerList").html(makerList);
				$("#followerList").html(followerList);
				priorChart = loadPriorChart(priorChartContainer, priorData, 600, 200);
				percentChart = loadPercentChart(percentChartContainer, percentData, 600, 200);
				pieChart = loadPieChart(pieChartContainer, pieChartData, 600, 300);
			}
		})
	}
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
		$("#filterFrm p").hide();
		  // $("ul > li:first-child a").next().show();
		  $("#frmContainer").on("click", "ul li label", function(){
		    $(this).siblings().slideToggle(300);
		  });
		//업무 클릭시 업무 설정 레이어 열기
		$("#resultContainer").on("click", ".result", function(){
			$("#propertyWorkSet").animate({right:'0'}, 500);
			$(".tui-chart-series-custom-event-area").css("display","none");
			//업무 아이디를 변수에 담음
			var wrk_id = $(this).data("wrk_id");
			$("#wps_wrk_id").val(wrk_id);
			console.log(wrk_id);
			console.log($("#wps_wrk_id").val(wrk_id));
			propertyWorkSetAjax(wrk_id);
		});
		
		var dropdown = $(".dropdown-content").css("display");
		$("body").on("change", dropdown, function() {
			console.log(dropdown);
		});
		//프로젝트 닫기 버튼을 클릭했을 때
		$(".btnSetClose").on("click", function(){
			$("#propertyWorkSet").animate({right:'-700px'}, 500);
			$(".tui-chart-series-custom-event-area").css("display", "");
		});
		
		
	});
	function hideChart() {
		var blank = $(".blankContainer");
		$("#pieChartContainer").hide();		
		$("#percentChartContainer").hide();		
		$("#priorChartContainer").hide();		
		$(blank).show();
	}
	function showChart() {
		var blank = $(".blankContainer");
		$("#pieChartContainer").show();		
		$("#percentChartContainer").show();		
		$("#priorChartContainer").show();		
		$(blank).hide();
	}
	
</script>
<script>
</script>