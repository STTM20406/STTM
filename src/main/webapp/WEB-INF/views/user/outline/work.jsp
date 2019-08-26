<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- ToastUI Chart load -->
<link rel="stylesheet" href="https://uicdn.toast.com/tui.chart/latest/tui-chart.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://uicdn.toast.com/tui.chart/latest/tui-chart-all.min.js"></script>
<script src="/js/toast-ui-chart.js"></script>
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
<h2>Work List</h2>
<div id="frmContainer" style="height:100%;width:200px;float:left;margin-right:0;">
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
		<div id="allContainer" style="height:95%; width:1100px;float:left;">
	        <div id="resultContainer" style="width:500px;padding:15px;height:700px; float:left;">
	        </div>
	        <div id="chartContainer" style="width:600px;padding:15px;height:100%; float:left;">
	        	<div id="pieChartContainer"></div>
	        	<div id="priorChartContainer"></div>
	        	<div id="percentChartContainer"></div>
	        </div>
	       	<div class="blankContainer" style="font-size:large;width:100%;height:730px;text-align:center;padding:250px;">
	       		<p>데이터 없음</p>
	       	</div>
		</div>
		
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
								<label for="D25565" class="wrk_color wrk_color01" style="background:#D25565"></label>
								<input type="radio" value="#D25565" name="wrk_color_cd" id="D25565">
							</li>
							<li>
								<label for="9775fa" class="wrk_color wrk_color02" style="background:#9775fa"></label>
								<input type="radio" value="#9775fa" name="wrk_color_cd" id="9775fa">
							</li>
							<li>
								<label for="ffa94d" class="wrk_color wrk_color03" style="background:#D25565"></label>
								<input type="radio" value="#ffa94d" name="wrk_color_cd" id="ffa94d">
							</li>
							<li>
								<label for="ffa94d" class="wrk_color wrk_color04" style="background:#74c0fc"></label>
								<input type="radio" value="#74c0fc" name="wrk_color_cd" id="74c0fc">
							</li>
							<li>
								<label for="f06595" class="wrk_color wrk_color05" style="background:#f06595"></label>
								<input type="radio" value="#f06595" name="wrk_color_cd" id="f06595">
							</li>
							<li>
								<label for="63e6be" class="wrk_color wrk_color06" style="background:#63e6be"></label>
								<input type="radio" value="#63e6be" name="wrk_color_cd" id="63e6be">
							</li>
							<li>
								<label for="a9e34b" class="wrk_color wrk_color07" style="background:#a9e34b"></label>
								<input type="radio" value="#a9e34b" name="wrk_color_cd" id="a9e34b">
							</li>
							<li>
								<label for="a9e34b" class="wrk_color wrk_color08" style="background:#4d638c"></label>
								<input type="radio" value="#4d638c" name="wrk_color_cd" id="a9e34b">
							</li>
							<li>
								<label for="495057" class="wrk_color wrk_color09" style="background:#495057"></label>
								<input type="radio" value="#495057" name="wrk_color_cd" id="495057">
							</li>
							<li>
								<label for="002dff" class="wrk_color wrk_color10" style="background:#002dff"></label>
								<input type="radio" value="#002dff" name="wrk_color_cd" id="002dff">
							</li>
						</ul>
					</div>
				</dd>
			</dl>
		</div>
		
		<!--  여기서부터 work comment -->
		<form id="frm02">
		<div id="tab2" class="tab_content">
			<div>
				<table class="tb_style_01">
					<colgroup>
						<col width="30%">
						<col width="10%">
						<col width="5%">
						<col width="2%">
						<col width="2%">
					</colgroup>
					<thead>
						<tr>
							<th>내용</th>
							<th>작성자 아이디</th>
							<th colspan='3'>작성일</th>
						</tr>
					</thead>
					<tbody id="commBody">

					</tbody>
				</table>
				<label>댓글 작성</label><br>
				<textarea rows="1" cols="60" name="comm_content" id="comm_content"></textarea>
				<button type="button" name="replyBtn" id="replyBtn"> 댓글등록 </button>
			</div>
			<div class="pagination">
			</div>
		</div>
		</form>
		
		<!--  여기서부터 work file&link-->
		<!-- 영하가 수정함 여기부터 ㅎ-->
		<div id="tab3" class="tab_content">
			<div class="sub_menu">
				<ul class="tabs">
					<li id="fileList">FileList</li>
					<li id="linkList">LinkList</li>
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
					<div>
						<table class="tb_style_01">
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
		
		//프로젝트 닫기 버튼을 클릭했을 때
		$(".btnSetClose").on("click", function(){
			$("#propertyWorkSet").animate({right:'-700px'}, 500);
			$(".tui-chart-series-custom-event-area").css("display", "");
		});
		// 업무 코멘트 시작점
		// 업무코멘트 수정
		$(".workTab").on("click", "#commentId", function(){
			var wps_wrk_id = $('#wps_wrk_id').val();
			commentPagination(wps_wrk_id,1, 10);
		})
		
		$("#commBody").on("click",".commUpdateBtn", function() {
			var commprid = $(this).siblings("input").val();
			
 			var inq_id = $(this).parent().prev().prev().prev().text();
 			var inq_trim = $.trim(inq_id);
 			$(this).parent().prev().prev().prev().replaceWith("<td><input type='text' name='updateComm' id='changeInput' value='"+inq_trim+"'/></td>");
 			$(this).replaceWith("<button type='button' id='commUpdateChgBtn' class='commUpdateChgBtn'>수정완료</button>");
 			
			$("#commBody").on("click","#commUpdateChgBtn", function(){
	 			var changVal = $("#changeInput").val();
								
				var inq_trim02 = $.trim(changVal);
				var prj_id = $(this).siblings("#prj_id02").val();
				var comm_id = $(this).siblings("#comm_id02").val();
				
				updateTest(inq_trim02,prj_id,comm_id);
				$(this).parent().prev().prev().prev().replaceWith("<td><p>"+inq_trim02+"</p></td>");
				$(this).replaceWith("<button type='button' id='commUpdateBtn' class='commUpdateBtn'>수정</button>");
			})
		})
		
		function updateTest(inq_trim02,prj_id,comm_id){
			$.ajax({
				url:"/commUpdate",
				method:"get",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				data : "inq_trim="+inq_trim02+"&prj_id="+prj_id+"&comm_id="+comm_id,
				success:function(data){
					
				}
			})
		}
		
		// 코멘트 삭제하기
		$("#commBody").on("click", "#commDeleteBtn", function(){
			var wps_wrk_id = $('#wps_wrk_id').val();
			var commDelete = $(this).attr("name");
			var comm_id = commDelete;
			
			commDeleteAjax(wps_wrk_id,comm_id);
		})
		
		function commDeleteAjax(wps_wrk_id,comm_id){
			$.ajax({
				url:"/commDelete",
				method:"post",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				data:"wps_wrk_id=" + wps_wrk_id +"&comm_id="+comm_id,
				success:function(data){
					commentPagination(wps_wrk_id,1, 10);
				
				}
			})	
		}
		
		// 댓글등록하기 버튼
		$("#replyBtn").on("click",function(){
			var wps_wrk_id = $('#wps_wrk_id').val();
			var wps_wrk_nm = $('#wps_nm').val();
			var content = $('#comm_content').val();
			commentInsert(wps_wrk_id,wps_wrk_nm,content,1,10);
		});
		
		//업무 클릭 했을 때 처음 보여주는 탭은 설정 탭
		$(".propertySetWrap div:nth-child(3)").show();
		
		//탭 버튼 클릭시 div 변경
		$(".workTab").on("click", "ul.tabs li", function () {
		    	$(".workTab ul.tabs li").removeClass("active").css("color", "#333");
		    	$(this).addClass("active").css("color", "darkred");
		    	$(".tab_content").hide()
		    	var activeTab = $(this).attr("data-tab");
		    	$("#" + activeTab).fadeIn()
		});
		
		/* 여기서부터 업무 셋팅 업데이트를 위한 이벤트 핸들러 입니다. */
		$("#propertyWorkSet input, select").on("propertychange keyup change click", function(){
			
			//프로젝트 셋팅 값 가져오기
			var id = $("#wps_id").val();
			var name = $("#wps_nm").val();
			var work_date = $("#wps_start_date").val();
			var res_date = $("#wps_res_date").val();
			var wrk_gd = $("#wrk_gd").val();
			
			var workSplit = work_date.split(" to ");
			var resSplit = res_date.split(" to ");
			
			var work_start_dt = workSplit[0];
			var work_end_dt = workSplit[1];
			
			if(!name){
				$(".ctxt").text("업무 이름을 작성해 주세요.");
 				layer_popup("#layer2");
				return false;
			}
			
			if(typeof work_start_dt == "undefined" || typeof work_end_dt == "undefined"){
				work_start_dt = "";
				work_end_dt = "";
			}
			
			var projectWorkSet = {
							  id : id
						  	, name : name
						  	, work_start_dt : work_start_dt
						 	, work_end_dt : work_end_dt
						  	, wrk_gd : wrk_gd
			}
			
			propertyWorkSetItemAjax(projectWorkSet);
		});
		
		//업무 설정 업데이트 ajax
		function propertyWorkSetItemAjax(projectWorkSet){
			$.ajax({
				url:"/work/propertyWorkSetItemAjax",
				method:"post",
				contentType:"application/x-www-form-urlencoded; charset=UTF-8",
				data:"wrk_id=" + projectWorkSet.id +
					"&wrk_nm=" + projectWorkSet.name +
					"&wrk_grade=" + projectWorkSet.wrk_gd +
					"&wrk_start_dt=" + projectWorkSet.work_start_dt +
					"&wrk_end_dt=" + projectWorkSet.work_end_dt,
				success:function(data){
					$(".workListItem").each(function() {
						var wrkID = $(this).attr("data-wrkid");
						if(wrkID == projectWorkSet.id){
							$(this).find(".wrk_title").html("<span>"+data.data.wrk_grade+"</span>" + data.data.wrk_nm);
							$(this).find(".wrk_date").text(data.data.wrkStartDtStr +" ~ "+ data.data.wrkEndDtStr );
						}
					});
				}
			});
		}
		
		var beforeChecked = -1;
		
  		$(".lableColor").on("click", "input[type=radio]", function(e) {
  			var id = $("#wps_id").val();
     		var index = $(this).prev().index("label");
     		$(this).prev().removeClass("colorSelect");
     		
     		if(beforeChecked == index) {
         		beforeChecked = -1;
         		$(this).prop("checked", false);
         		wrk_color = $("input:radio[name='wrk_color_cd']:checked").val();
         		console.log(wrk_color);
     		}else{
         		beforeChecked = index;
         		wrk_color = $("input:radio[name='wrk_color_cd']:checked").val();
         		console.log(wrk_color);
         		$(".wrk_color").removeClass("colorSelect");
         		$(this).prev().addClass("colorSelect");
     		}
     		
     		if(typeof wrk_color == "undefined"){
				wrk_color = "";
			}else{
				wrk_color = $("input:radio[name='wrk_color_cd']:checked").val();
			}
     		
     		propertyWorkLableColorAjax(id, wrk_color);
      	});
  		
  	//업무 라벨 색상 변경
		function propertyWorkLableColorAjax(id, wrk_color){
  			console.log(wrk_color);
  			var changeColor = encodeURIComponent(wrk_color);
			$.ajax({
				url:"/work/propertyWorkLableColorAjax",
				method:"post",
				contentType:"application/x-www-form-urlencoded; charset=UTF-8",
				data:"wrk_id=" + id + "&wrk_color_cd=" + changeColor,
				success:function(data){
				}
			});
		}
  	
		//예약알림 버튼 클릭시
  		$(".notifyCon").on("click", "#resNotifyBtn", function(){
  			$(".notifyArea").fadeIn(100);
  		});
  		
  		//예약알림 취소 버튼 클릭시
  		$("#notificationCancelBtn").on("click", function(){
  			$(".notifyArea").fadeOut(100);
  		});
  		
		//날짜 선택 - 예약알림
		$(".flatpickr1").flatpickr({
			inline: true,
		    	minDate: "today",
		    	enableTime: true
		});
		
		//날짜 선택 - 시작일, 종료일
		$(".flatpickr").flatpickr({
		    	mode: "range",
		    	minDate: "today",
		    	enableTime: true
		});
		

		// file, link 등록 부분 구현중
		
		$('#workLink').hide(0);
		
		$(".workTab").on("click", "#FL", function(){
			var wrk_id = $('#wps_wrk_id').val();
			workFilePagination(1, 10, wrk_id);
		})
	
		$(".sub_menu").on("click", "#fileList",function(){
			var wrk_id = $('#wps_wrk_id').val();
			$('#locker').fadeIn(0);
			$('#workLink').fadeOut(0);
			$('#workFile').fadeIn(0);
			workFilePagination(1, 10, wrk_id);
		})
		
		$(".sub_menu").on("click", "#linkList",function(){
			$('#locker').hide(0);
			var wrk_id = $('#wps_wrk_id').val();
			$('#workFile').fadeOut(0);
			$('#workLink').fadeIn(0);
			workLinkPagination(1, 10,wrk_id);
		})
		
		
		$("#workLink").on('click','#uploadLink', function(){
			alert("link가즈아!!");
			var attch_url = $('.link').val();
			var locker = $("#locker input[type=radio]:checked").val();
			var wrk_id = $('#wps_wrk_id').val();
			
			$('#box').val(locker);
			$('#work').val(wrk_id);
			
			$.ajax({
	 		    url: "/workLinkUpload",
	 		    type: "POST",
	 		    data: "attch_url=" + attch_url + "&locker=" + locker + "&wrk_id="+ wrk_id,
	 		    contentType:"application/x-www-form-urlencoded; charset=UTF-8",
	 		    success: function(data){
	 		    	workLinkPagination(1, 10, wrk_id);
	 		    },
	 		    error: function(e){
	 		        alert(e.reponseText);
	 		    }
	 		});
			
			
		})
		
		$("#workFile").on('click','#uploadFile', function(){

			var form = document.getElementById("frm");
			console.log(form);
			form.method = "POST";
			form.enctype = "multipart/form-data";
// 			formData.append("uploadfile",$("input[name=uploadfile]")[0].files[0]);
			
			var locker = $("#locker input[type=radio]:checked").val();
			var wrk_id = $('#wps_wrk_id').val();
			$('#box').val(locker);
			$('#work').val(wrk_id);
			var formData  = new FormData(form);
			console.log(formData);
			console.log($(formData).serialize());
			alert(formData);
			$.ajax({
	 		    url: "/workFileUpload",
	 		    type: "POST",
	 		    data: formData,
				cache : false,		
	 		    contentType: false,
	 		    processData: false,
	 		    success: function(data){
	 		    	workFilePagination(1, 10, wrk_id);
	 		    },
	 		    error: function(e){
	 		        alert(e.reponseText);
	 		    }
	 		});
		});
		
		
		//업무 멤버 추가하기 버튼 클릭시 해당 프로젝트 멤버 가져오기
		$(".wrk_add_mem").fadeOut(0); //멤버리스트 layer 숨기기
		$("#wps_mem_set").on("click", function(){
			var wrkID = $("#wps_id").val();
			$(".wrk_add_mem").fadeIn(300);
			workMemListAjax(wrkID);
		});
		
		//업무 멤버 가져오는 ajax
		function workMemListAjax(wrkID){
			$.ajax({
				url:"/work/workMemListAjax",
				method:"post",
				data: "wrk_id="+ wrkID,
				success:function(data){
					
					var html = "";
					data.projectMemList.forEach(function(item, index){
						//html 생성
						html += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
					});	
					$(".wrk_mem_item").html(html);
				}
			});
		}
		
		//업무 멤버 클릭 했을 때
		$(".wrk_mem_item").on("click", "li", function(){
			var mem_add_email = $(this).attr("id");
			var wrkID = $("#wps_id").val();
			workMemAddAjax(wrkID, mem_add_email);	
		});
		
		//배정된 멤버로 선택한 멤버 추가
		function workMemAddAjax(wrkID, mem_add_email){
			$.ajax({
				url:"/work/workMemAddAjax",
				method:"post",
				data: "wrk_id="+ wrkID + "&user_email=" + mem_add_email,
				success:function(data){
					console.log(data);
					
					var html = "";
					var html2 = "";
					var html3 = "";
					var html4 = "";
					
					data.wrkFlwList.forEach(function(item, index){
						html += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
					});	
					
					data.projectFlwList.forEach(function(item, index){
						html2 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
					});	
					
					data.wrkMemList.forEach(function(item, index){
						html3 += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
					});
					
					data.projectMemList.forEach(function(item, index){
						html4 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
					});	
					
					
					$(".wrk_mem_flw_box").html(html);
					$(".wrk_flw_item_list").html(html2);
					$(".wrk_add_box").html(html3);
					$(".wrk_mem_item").html(html4);
				}
			});
		}
		
		//업무 배정된 멤버 삭제 클릭 했을 때
		$(".wrk_add_box").on("click", "li input", function(){
			var textSplit = $(this).parent().attr("id").split("_");
			var id = textSplit[1];
			var email = textSplit[0];
			workMemDelAjax(id, email);
		});
		
		function workMemDelAjax(id, email){
			$.ajax({
				url:"/work/workMemDelAjax",
				method:"post",
				data:"wrk_id="+ id + "&user_email=" + email,
				success:function(data){
					
					var html = "";
					var html2 = "";
					
					data.wrkMemList.forEach(function(item, index){
						html += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
					});	
					
					data.projectMemList.forEach(function(item, index){
						html2 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
					});	
					
					$(".wrk_add_box").html(html);
					$(".wrk_mem_item").html(html2);
				}
			});
		}
		
		
		//팔로워 멤버 추가하기 버튼 클릭시 해당 프로젝트 멤버 가져오기
		$(".wrk_add_flw").fadeOut(0); //멤버리스트 layer 숨기기
		$("#wrk_flw_set").on("click", function(){
			var wrkID = $("#wps_id").val();
			$(".wrk_add_flw").fadeIn(300);
			workFlwListAjax(wrkID);
		});
		
		//팔로워 멤버 가져오는 ajax
		function workFlwListAjax(wrkID){
			$.ajax({
				url:"/work/workFlwListAjax",
				method:"post",
				data: "wrk_id="+ wrkID,
				success:function(data){
					var html = "";
					data.projectMemList.forEach(function(item, index){
						//html 생성
						html += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
					});	
					$(".wrk_flw_item_list").html(html);
				}
			});
		}
		
		
		//팔로워 멤버 클릭 했을 때
		$(".wrk_flw_item_list").on("click", "li", function(){
			var mem_add_email = $(this).attr("id");
			var wrkID = $("#wps_id").val();
			
			workFlwAddAjax(wrkID, mem_add_email);	
		});
		
		//팔로우 멤버로 멤버 추가
		function workFlwAddAjax(wrkID, mem_add_email){
			$.ajax({
				url:"/work/workFlwAddAjax",
				method:"post",
				data: "wrk_id="+ wrkID + "&user_email=" + mem_add_email,
				success:function(data){
					
					var html = "";
					var html2 = "";
					var html3 = "";
					var html4 = "";
					
					data.wrkFlwList.forEach(function(item, index){
						html += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
					});	
					
					data.projectFlwList.forEach(function(item, index){
						html2 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
					});	
					
					data.wrkMemList.forEach(function(item, index){
						html3 += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
					});
					
					data.projectMemList.forEach(function(item, index){
						html4 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
					});	
					
					
					$(".wrk_mem_flw_box").html(html);
					$(".wrk_flw_item_list").html(html2);
					$(".wrk_add_box").html(html3);
					$(".wrk_mem_item").html(html4);
				}
			});
		}
		
		//업무 배정된 멤버 삭제 클릭 했을 때
		$(".wrk_mem_flw_box").on("click", "li input", function(){
			var textSplit = $(this).parent().attr("id").split("_");
			var id = textSplit[1];
			var email = textSplit[0];
			workFlwDelAjax(id, email);
		});
		
		
		//예약알림
		$("#notificationAddBtn").on("click", function(){
			var notifyMem = $("#notificationMem option:selected").val();
			var notifyDate = $("#wps_res_date").val();
			var wrkID = $("#wps_id").val();
			
			notificationAddAjax(notifyMem, notifyDate, wrkID);
		});
		
		//예약알림 삭제 클릭시
		$(".notifyCon").on("click", ".pushDel", function(){
			var wrk_rv_id = $(this).parent().attr("id");
			notificationDelAjax(wrk_rv_id);
		});
		
	});
		//예약알림
		function notificationAddAjax(notifyMem, notifyDate, wrkID){
			$.ajax({
				url:"/work/notificationAddAjax",
				method:"post",
				data:"wrk_id="+ wrkID + "&memType=" + notifyMem + "&wrk_dt=" + notifyDate,
				success:function(data){
					
					$(".notifyArea").fadeOut(100);
					$("#resNotifyBtn").css({display:"none"});
					
					var html = "<p class='resDate' id='"+data.getWrokPush.wrk_rv_id+"'>"+data.getWrokPush.wrkDtStr+" | <span class='pushDel'>삭제</span></p>";
					
					$(".notifyCon").html(html)
				}
			});
		};
		
		//예약알림삭제
		function notificationDelAjax(wrk_rv_id){
			$.ajax({
				url:"/work/notificationDelAjax",
				method:"post",
				data:"wrk_rv_id="+ wrk_rv_id,
				success:function(data){
					
					var html = "<input type='button' id='resNotifyBtn' value=예약알림추가'>";
					$(".resDate").css({display:"none"});
					$(".notifyCon").html(html)
				}
			});
		};
		
		//업무 배정된 멤버 삭제 클릭 했을 때
		function workFlwDelAjax(id, email){
			$.ajax({
				url:"/work/workFlwDelAjax",
				method:"post",
				data:"wrk_id="+ id + "&user_email=" + email,
				success:function(data){
					
					var html = "";
					var html2 = "";
					
					data.wrkFlwList.forEach(function(item, index){
						html += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
					});	
					
					data.projectMemList.forEach(function(item, index){
						html2 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
					});	
					
					$(".wrk_mem_flw_box").html(html);
					$(".wrk_flw_item_list").html(html2);
				}
			});
		}
		
		function workFilePagination(page, pageSize, wrk_id) {
			$.ajax({
				url : "/workFilePagination",
				method : "post",
				data : "page=" + page + "&pageSize=" + pageSize + "&wrk_id="+ wrk_id,
				success : function(data) {
					//사용자 리스트
					var hhtml = "";
					var html = "";

					//hhtml생성
					hhtml += "<tr>";
					hhtml += "<th>파일명</th>";
					hhtml += "<th>공유한 멤버 이름</th>";
					hhtml += "<th>등록한 날짜</th>";
					hhtml += "<th>삭제</th>";
					hhtml += "</tr>";

					data.workFileList.forEach(function(file, index) {
						//html생성
						html += "<tr id='filetr'>";
						html += "<td><a href='/fileDownLoad?file_id="+file.file_id+"'>" + file.original_file_nm+ "</a></td>";
						html += "<td>" + file.user_nm + "</td>";
						html += "<td>" + file.prjStartDtStr + "</td>";
						html += "<td><a href='javascript:workDelFile("+ file.file_id + "," + file.wrk_id+ ")'>삭제</a></td>";
						html += "</tr>";
					});
					var pHtml = "";
					var pageVo = data.pageVo;
//	 				console.log(data);
//	 				console.log(pageVo);

					if (pageVo.page == 1)
						pHtml += "<li class='disabled'><span>«<span></li>";
					else
						pHtml += "<li><a href='javascript:workFilePagination("
								+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
								+ wrk_id + ");'>«</a></li>";
					for (var i = 1; i <= data.paginationSize; i++) {
						if (pageVo.page == i)
							pHtml += "<li class='active'><span>" + i
									+ "</span></li>";
						else
							pHtml += "<li><a href='javascript:workFilePagination("
									+ i + ", " + pageVo.pageSize + "," + wrk_id
									+ ");'>" + i + "</a></li>";
					}
					if (pageVo.page == data.paginationSize)
						pHtml += "<li class='disabled'><span>»<span></li>";
					else
						pHtml += "<li><a href='javascript:workFilePagination("
								+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
								+ wrk_id + ");'>»</a></li>";
					$(".pagination").html(pHtml);
					$("#publicHeader").html(hhtml);
					$("#publicList").html(html);
				}
			});
		}

		function workLinkPagination(page, pageSize, wrk_id) {
			$.ajax({
				url : "/workLinkPagination",
				method : "post",
				data : "page=" + page + "&pageSize=" + pageSize + "&wrk_id="
						+ wrk_id,
				success : function(data) {
					//사용자 리스트
					var hhtml = "";
					var html = "";

					//hhtml생성
					hhtml += "<tr>";
					hhtml += "<th>링크명</th>";
					hhtml += "<th>공유한 멤버 이름</th>";
					hhtml += "<th>등록한 날짜</th>";
					hhtml += "<th>삭제</th>";
					hhtml += "</tr>";

					console.log(data.workLinkList);
					data.workLinkList.forEach(function(link, index) {
						//html생성
						html += "<tr>";
						html += "<td><a href='https://"+link.attch_url+"'>"+ link.attch_url + "</a></td>";
						html += "<td>" + link.user_nm + "</td>";
						html += "<td>" + link.prjStartDtStr + "</td>";
						html += "<td><a href='javascript:workDelLink("+ link.link_id + "," + link.wrk_id+ ")'>삭제</a></td>";
						html += "</tr>";
					});
					var pHtml = "";
					var pageVo = data.pageVo;
					console.log(data);
					console.log(pageVo);

					if (pageVo.page == 1)
						pHtml += "<li class='disabled'><span>«<span></li>";
					else
						pHtml += "<li><a href='javascript:workLinkPagination("
								+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
								+ wrk_id + ");'>«</a></li>";

					for (var i = 1; i <= data.paginationSize; i++) {
						if (pageVo.page == i)
							pHtml += "<li class='active'><span>" + i
									+ "</span></li>";
						else
							pHtml += "<li><a href='javascript:workLinkPagination("
									+ i + ", " + pageVo.pageSize + "," + wrk_id
									+ ");'>" + i + "</a></li>";
					}
					if (pageVo.page == data.paginationSize)
						pHtml += "<li class='disabled'><span>»<span></li>";
					else
						pHtml += "<li><a href='javascript:workLinkPagination("
								+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
								+ wrk_id + ");'>»</a></li>";
					$(".pagination").html(pHtml);
					$("#publicHeader").html(hhtml);
					$("#publicList").html(html);
				}
			});
		}

		function workDelFile(fileID, wrk_id) {
			alert("삭제긔긔");

			$.ajax({
				url : "/workDelFile",
				method : "post",
				data : "file_id=" + fileID + "&wrk_id=" + wrk_id,
				success : function(data) {
					//사용자 리스트
					var hhtml = "";
					var html = "";

					//hhtml생성
					hhtml += "<tr>";
					hhtml += "<th>파일명</th>";
					hhtml += "<th>공유한 멤버 이름</th>";
					hhtml += "<th>등록한 날짜</th>";
					hhtml += "<th>삭제</th>";
					hhtml += "</tr>";

					data.workFileList.forEach(function(file, index) {
						//html생성
						html += "<tr id='filetr'>";
						html += "<td><a href='/fileDownLoad?file_id="+file.file_id+"'>" + file.original_file_nm+ "</a></td>";
						html += "<td>" + file.user_nm + "</td>";
						html += "<td>" + file.prjStartDtStr + "</td>";
						html += "<td><a href='javascript:workDelFile("
								+ file.file_id + "," + file.wrk_id
								+ ")'>삭제</a></td>";
						html += "</tr>";
					});
					var pHtml = "";
					var pageVo = data.pageVo;
					console.log(data);
					console.log(pageVo);

					if (pageVo.page == 1)
						pHtml += "<li class='disabled'><span>«<span></li>";
					else
						pHtml += "<li><a href='javascript:workFilePagination("
								+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
								+ wrk_id + ");'>«</a></li>";
					for (var i = 1; i <= data.paginationSize; i++) {
						if (pageVo.page == i)
							pHtml += "<li class='active'><span>" + i
									+ "</span></li>";
						else
							pHtml += "<li><a href='javascript:workFilePagination("
									+ i + ", " + pageVo.pageSize + "," + wrk_id
									+ ");'>" + i + "</a></li>";
					}
					if (pageVo.page == data.paginationSize)
						pHtml += "<li class='disabled'><span>»<span></li>";
					else
						pHtml += "<li><a href='javascript:workFilePagination("
								+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
								+ wrk_id + ");'>»</a></li>";
					$(".pagination").html(pHtml);
					$("#publicHeader").html(hhtml);
					$("#publicList").html(html);
				}
			});
		}

		function workDelLink(linkID, wrk_id) {
			alert("삭제긔긔");

			$.ajax({
				url : "/workDelLink",
				method : "post",
				data : "link_id=" + linkID + "&wrk_id=" + wrk_id,
				success : function(data) {
					//사용자 리스트
					var hhtml = "";
					var html = "";

					//hhtml생성
					hhtml += "<tr>";
					hhtml += "<th>링크명</th>";
					hhtml += "<th>공유한 멤버 이름</th>";
					hhtml += "<th>등록한 날짜</th>";
					hhtml += "<th>삭제</th>";
					hhtml += "</tr>";

					console.log(data.workLinkList);
					data.workLinkList.forEach(function(link, index) {
						//html생성
						html += "<tr>";
						html += "<td><a href='https://"+link.attch_url+"'>"
								+ link.attch_url + "</a></td>";
						html += "<td>" + link.user_nm + "</td>";
						html += "<td>" + link.prjStartDtStr + "</td>";
						html += "<td><a href='javascript:workDelLink("
								+ link.link_id + "," + link.wrk_id
								+ ")'>삭제</a></td>";
						html += "</tr>";
					});
					var pHtml = "";
					var pageVo = data.pageVo;
					console.log(data);
					console.log(pageVo);

					if (pageVo.page == 1)
						pHtml += "<li class='disabled'><span>«<span></li>";
					else
						pHtml += "<li><a href='javascript:workLinkPagination("
								+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
								+ wrk_id + ");'>«</a></li>";

					for (var i = 1; i <= data.paginationSize; i++) {
						if (pageVo.page == i)
							pHtml += "<li class='active'><span>" + i
									+ "</span></li>";
						else
							pHtml += "<li><a href='javascript:workLinkPagination("
									+ i + ", " + pageVo.pageSize + "," + wrk_id
									+ ");'>" + i + "</a></li>";
					}
					if (pageVo.page == data.paginationSize)
						pHtml += "<li class='disabled'><span>»<span></li>";
					else
						pHtml += "<li><a href='javascript:workLinkPagination("
								+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
								+ wrk_id + ");'>»</a></li>";
					$(".pagination").html(pHtml);
					$("#publicHeader").html(hhtml);
					$("#publicList").html(html);
				}
			});
		}

		// file, link 등록 부분 구현중
			
		
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
	//업무 상세보기 메서드
	function propertyWorkSetAjax(wrk_id){
		$.ajax({
			url:"/work/propertyWorkSetAjax",
			method:"post",
			data:"wrk_id=" + wrk_id,
			success:function(data){
				
				$("#wps_id").val(data.workVo.wrk_id);
				$("#wps_nm").val(data.workVo.wrk_nm);
				$("#wps_write_nm").text(data.workVo.user_nm);
				$("#wps_write_date").text(data.workVo.wrkDtStr);
				$("#wps_start_date").val(data.workVo.wrkStartDtStr + " ~ " + data.workVo.wrkEndDtStr);
				$("#wrk_gd").val(data.workVo.wrk_grade);
				$(".wrk_color").removeClass("colorSelect");
				
				$(data.workVo.wrk_color_cd).prev().addClass("colorSelect");
				$(data.workVo.wrk_color_cd).prop("checked", true);
				
				var html2 = "";
				if(data.getWrokPush != null){
					html2 += "<p class='resDate' id='"+data.workVo.wrk_rv_id+"'>"+data.getWrokPush.wrkDtStr+" | <span class='pushDel'>삭제</span></p>";
					$(".notifyCon").html(html2);
					$("#resNotifyBtn").css({display:"none"});
				}else{
					html2 = "<input type='button' id='resNotifyBtn' value=예약알림추가'>";
					$(".notifyCon").html(html2);
					$(".resDate").css({display:"none"});
				}
				
				//배정된 업무 멤버 불러오기
				var html = "";
				data.wrkMemList.forEach(function(item, index){
					html += "<li id='"+ item.user_email +"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='wrkMemDel' value='삭제'></li>";
				});	
				$(".wrk_add_box").html(html);
				
				//업무 팔로워 멤버 불러오기
				var html = "";
				data.wrkFlwList.forEach(function(item, index){
					html += "<li id='"+ item.user_email +"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='wrkFlwDel' value='삭제'></li>";
				});	
				$(".wrk_mem_flw_box").html(html);
				
				
				//멤버레벨이 1인데 권한이 ASC02 또는 ASC03 일때
				if(projectMemLevel == "LV1" && projectAuth == "ASC02" ){
					$(".propertySet input").prop('readonly', true); 										//설정창의 모든 input readonly
					$(".propertySet select").prop('disabled',true);										//설정창의 모든 select disabled
					$(".propertySet button[name='wps_mem_set']").prop('disabled', true);					//설정창의 모든 button disabled
					$(".propertySet button[name='wrk_flw_set']").prop('disabled', true);					//설정창의 모든 button disabled
					$(".propertySet input[type=button]").prop('disabled', true);
					$(".flatpickr-calendar").css({display:"none"});
				}else if(projectMemLevel == "LV1" && projectAuth == "ASC03"){
					$(".propertySet input").prop('readonly', true); 										//설정창의 모든 input readonly
					$(".propertySet select").prop('disabled',true);										//설정창의 모든 select disabled
					$(".propertySet button").prop('disabled', true);					//설정창의 모든 button disabled
					$(".propertySet input[type=button]").prop('disabled', true);
					$(".propertySet input[type=radio]").prop('disabled', true);
					$(".flatpickr-calendar").css({display:"none"});
				}
//					else{
//						$(".propertySet input").prop('readonly', false);
//						$(".propertySet select").prop('disabled',false);
//						$(".propertySet button").prop('disabled', false);
//						$(".prj_add_box input").css({visibility:"visible"});
//						$(".prj_mem_add_box input").css({visibility:"visible"});
//						$(".propertySet input[type=button]").prop('disabled', false);
//						$(".datepicker").css({display:"block"});
//					}
			}
		});
	}
	function commentPagination(wps_wrk_id,page, pageSize){
		$.ajax({
			url:"/comment",
			method:"post",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			data:"wps_wrk_id="+wps_wrk_id+"&page="+page+"&pageSize="+pageSize,
			success:function(data){
				var html = "";
				
				data.commentList.forEach(function(comm, index){
					
					html += "<tr class='commTr'>";
					html += "<input type='hidden' name='commContent' value='"+comm.comm_content +"'/>"
					html += "<input type='hidden' name='commContent' value='"+comm.comm_id +"'/>"
					html += "<input type='hidden' name='commContent' value='"+comm.prj_id +"'/>"
					html += "<td>"+comm.comm_content+"</td>";
					html += "<td>"+comm.user_email+"</td>";
					html += "<td>"+comm.commDateStr+"</td>";
					html += "<td>";
					html += "<input type='hidden' name='commContent' value='"+comm.comm_content +"'/>"
					html += "<input type='hidden' name='commContent' value='"+comm.comm_id +"'/>"
					html += "<button type='button' id='commUpdateBtn' class='commUpdateBtn'>수정</button>"
					html += "</td>";
					html += "<td class='commDeleteTd'>";
					html += "<input type='hidden' value='"+comm.prj_id +"'/>"
					html += "<button type='button' id='commDeleteBtn' class='commDeleteBtn' name='"+comm.comm_id+"'>삭제</button>"
					html += "</td>";
					html += "</tr>";
					
				});
				
				var pHtml = "";
				var pageVo = data.pageVo;
				
				if (pageVo.page == 1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:commentPagination(" + wps_wrk_id + "," + (pageVo.page - 1) + ", " + pageVo.pageSize + ");'>«</a></li>";
				for (var i = 1; i <= data.commPageSize; i++) {
					if (pageVo.page == i)
						pHtml += "<li class='active'><span>" + i + "</span></li>";
					else
						pHtml += "<li><a href='javascript:commentPagination(" + wps_wrk_id + ","+ i + ", " + pageVo.pageSize + ");'>" + i + "</a></li>";
				}
				if (pageVo.page == data.commPageSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:commentPagination(" + wps_wrk_id + "," + (pageVo.page + 1) + ", " + pageVo.pageSize + ");'>»</a></li>";
					
				$(".pagination").html(pHtml);
				$("#commBody").html(html);
			}
		})
	}
	function commentInsert(wps_wrk_id,wps_wrk_nm,content,page, pageSize){
		$.ajax({
			url:"/commentInsert",
			method:"post",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			data:"wps_wrk_id="+wps_wrk_id+"&wps_wrk_nm="+wps_wrk_nm+"&comm_content="+content+"&page="+page+"&pageSize="+pageSize,
			success:function(data){
				if(socket){
					var socketMsg = "";
					for(var i=0;i<data.data.length;i++){
						socketMsg = "wrk_comment," + data.data[i].user_email +"," + data.data3.wrk_nm;
						socket.send(socketMsg);
					}
					
					for(var i=0;i<data.data2.length;i++){
						socketMsg = "wrk_comment," + data.data2[i].user_email +"," + data.data3.wrk_nm;
						socket.send(socketMsg);
					}
					// websocket에 보내기!!
				}
				commentPagination(wps_wrk_id,1, 10);
			}
		})
	}
	
	
</script>
<script>
</script>