<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	#gantt_here { width:85%; height:630px; overflow:hidden; padding:0px; margin:0px; float:left;}
</style>
<!-- 프로젝트 업무 간트차트 -->
<script src="/js/dhtmlxgantt.js"></script>
<link rel="stylesheet" href="/css/dhtmlxgantt.css" type="text/css">
<!-- Include 할 부분 -->
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
<h2 class="contentTitle">${PROJECT_INFO.prj_nm }</h2>
<div id="allContainer">
<div id="frmContainer">
	    <form id="filterFrm">
	    	<label>업무 구분</label>
	    	<select name="wrk_is_mine" class="filter">
	    		<option value="all" selected>전체 업무</option>
	    		<option value="mine">내 업무만</option>
	    	</select>
	    	<label>작성일 기준</label>
	    	<select name="wrk_dt" class="filter">
	    		<option value="0" selected>전체</option>
	    		<option value="30">30일 이내</option>
	    		<option value="60">60일 이내</option>
	    		<option value="90">90일 이내</option>
	    	</select>
	    	<label>업무 주체</label>
		    	<p><input type="checkbox" class="filter" name="wrk_i_assigned" value="y"> 내게 할당된 업무</p>
		    	<p><input type="checkbox" class="filter" name="wrk_i_made" value="y">	내가 작성한 업무</p>
		    	<p><input type="checkbox" class="filter" name="wrk_i_following" value="y"> 내가 팔로우한 업무</p>
	    	<input type="hidden" name="prj_id" value="${PROJECT_INFO.prj_id }"> <!-- 나중에 세션에 저장된 프로젝트 아이디 값이 들어올 예정 -->
	    	<label>마감일 기준</label>
		    	<p><input type="checkbox" class="filter" name="overdue" value="y"> 마감일 지남</p>
		    	<p><input type="checkbox" class="filter" name="till_this_week" value="y"> 이번 주까지</p>
		    	<p><input type="checkbox" class="filter" name="till_this_month" value="y"> 이번 달까지</p>
		    	<p><input type="checkbox" class="filter" name="no_deadline" value="y"> 마감일 없음</p>
	    	<label>업무 상태 구분</label>
		    	<p><input type="checkbox" class="filter" name="is_cmp" value="y"> 완료된 업무</p>
	    		<div id="makerList">
	    		</div>
	    		<div id="followerList">
	    		</div>
		    	<button type="button" class="btn_style_02" onclick="reset()">필터 초기화</button>
		    	<input type="hidden" name="user_email" value="${USER_INFO.user_email }">
	    </form>
</div>
<div id="gantt_here"></div>
</div>
<!-- 업무 상세 보기 시작 -->
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
			<div class="tab_sub_menu">
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
<!-- 업무 상세보기 끝 -->
</section>
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
			var filterFrm = data.filterFrm;
			var result = data.result;
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
		url: "/filter/prjgantt",
		type: "post",
		data: serial,
		success: function(data) {
			console.log(data);
			var filterFrm = data.filterFrm;
			var result = data.result;
			var makerList = data.makerList;
			var followerList = data.followerList;
			console.log(data);
// 			var task = JSON.parse(result);
			
			gantt.parse(result);
			
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
		      {view: "scrollbar", scroll: "x", id:"scrollHor", height:20}
		    ]
		};
		gantt.config.date_format = "%Y-%n-%j %H:%i";
		gantt.init("gantt_here");
	}
	
	$(function(){
		searchInit();
		loadGantt();
// 		$("#filterFrm p").hide();
		  // $("ul > li:first-child a").next().show();
		  $("#filterFrm").on("click", "ul li label", function(){
		    $(this).siblings().slideToggle(300);
		    // $(this).next().slideDown(300);
// 		    $("ul li span").not(this).next().slideUp(300);
		    return false;
		  });
// 		  $("#ul li span").eq(0).trigger("click");
	})
</script>