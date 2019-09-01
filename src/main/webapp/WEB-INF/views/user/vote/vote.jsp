<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- flatpickr.js 시작 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://npmcdn.com/flatpickr/dist/l10n/ko.js"></script>
<!-- flatpicker.js 끝 -->
<style>
	#voteContainer {width: 100%; height:100%; float:left;}
	#voteList {width:95%; height:100%; float:left;}
	#voteListTbl {width:95%; height:100%; text-align:center; border-collapse: collapse;}
	#voteListTbl tr { height: 80px; }
	.votes {width:95%; height:35px; margin: 5px; border:1px solid black; }
	.vote_subject_head { width: 90%; display:inline-block; font-size: large; }
	.vote_subject { width: 90%; display:inline-block; font-size: large; cursor:pointer;}
	.vote_part { width: 90%; font-size: medium; display:inline-block;}
	.vote_ano { width: 90%; font-size: medium; display:inline-block; color:#111111;}
	.vote_end_dt { width: 90%; display:inline-block; font-size: medium; }
	.vote_no_deadline { width: 90%; display:inline-block; font-size: medium; }
	.vote_part_y, .vote_ended { font-size:large; font-style: italic; font-weight: 900; color: green; display:inline-block;}
	.vote_part_n { font-size:large; font-style: italic; font-weight: 900; color: red;  display:inline-block;}
	.vote_part_yn { width: 90%; font-size:large; padding: 2px; display:inline-block;}
	.vote_user_nm { width: 90%; font-size: large; padding: 2px; display:inline-block; }
	.vote_config {font-size:large; font-weight: 900; display:inline-block; text-align:right; cursor: pointer; }
	.vote_config_menu { width: 62px; height: 52px; }
	.vote_end { font-size: large;}
	.modal { display: none; /* Hidden by default */ position: fixed; /* Stay in place */ z-index: 1; /* Sit on top */ padding-top: 100px; /* Location of the box */ left: 0; top: 0; width: 100%; /* Full width */ height: 100%; /* Full height */ overflow: auto; /* Enable scroll if needed */ background-color: rgb(0,0,0); /* Fallback color */ background-color: rgba(0,0,0,0.4); /* Black w/ opacity */ }
	.th1 { width: 25%;}
	.th2 { width: 15%;}
	.th3 { width: 24%; }
	.th4 { width: 10%; }
	.th5 { width: 7%; }
	.th6 { width: 7%; }
	/* Modal Content */
	.modal-content { background-color: #fefefe; margin: auto; padding: 20px; border: 1px solid #888; width: 20%;  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19); animation-name: animatetop; animation-duration: 0.4s }
	.modal-content-mdf { background-color: #fefefe; margin: auto; padding: 20px; border: 1px solid #888; width: 24%;  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19); animation-name: animatetop; animation-duration: 0.4s }
	.modal-content-detail { background-color: #fefefe; margin: auto; padding: 20px; border: 1px solid #888; width: 24%;  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19); animation-name: animatetop; animation-duration: 0.4s }
	.modal-body { font-size: medium; }
	/* The Close Button */
	.close { color: #aaaaaa; float: right; font-size: 28px; font-weight: bold;}
	.close:hover, .close:focus { color: #000; text-decoration: none; cursor: pointer;}
	/* Add Animation */
	@keyframes animatetop { from {top: -300px; opacity: 0} to {top: 0; opacity: 1} }
	.modal-header h2 { font-size: x-large;}
	#newVoteBtn {margin-left : 83%; margin-bottom : 10px; }
	#newVoteContainer {padding : 10px; }
	input[name='vote_subject'] { width: 80%; }
	input[name='vote_item'] { width: 100%; }
	input[name='vote_con'] { width: 80%; }
	.delItem,.delItemMdf ,#addItemBtn { background-color:#ffffff; color: #2b65ff; border: none;}
	#voteTbl { padding: 5px; }
	.voteItems { font-size: medium;}
	.vote_set_list dd { text-align: center;}
	.vote_mdf { cursor:pointer; font-size:medium; }
	.vote_del { cursor:pointer; font-size:medium; }
	.vote_set_list {/*position:relative; left:101%; top:-20px; */width:60px; z-index:2; display:none; padding: 1px; border: 1px solid #e1e1e1;}
	.item { width:100%; border:1px solid black; margin:5px; cursor: pointer;}
	#voteDetailFrm input[type=button], #btnCmp {width: 50%; height:30px; margin-left:25%; margin-top : 2px;}
	.voted {background-color: #eeeeee;}
	.selected {background-color: #33aaff; color: #ffffff;}
	#btnCmp {margin-top: 3px; display: none;}
	.vote_item {width : 88%; height: 28px; vertical-align: middle; }
	.vote_item span {margin-left: 5%;}
	#tblItem, #tblItemVoted { width: 100%; border-collapse: collapse;}
	.input-button {cursor: pointer;}
</style>
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
<h2>${PROJECT_INFO.prj_nm} > Votes</h2>
	<button id="newVoteBtn" type="button" class="btn_style_01">새 투표 작성</button>
<div id="voteContainer">
	<div id="voteList">
	</div>

<form id="voteFrm">
	<input type="hidden" name="prj_id" value="${PROJECT_INFO.prj_id }">
	<input type="hidden" name="user_email" value="${USER_INFO.user_email }">
	<input type="hidden" name='votelist_page'>
</form>

<div id="voteMdfModal" class='modal'>
	<div class="modal-content-mdf">
  	<div class="modal-header">
	    <span id="modalCloseMdf" class="close">&times;</span>
	    <h2>투표 내용 수정</h2>
	    <br>
  	</div>
  	<div class="modal-body">
  		<div id="VoteMdfContainer">
  		
  		</div>
  	</div>
  	<div class="modal-footer">
  	</div>
  </div>
</div>

<div id="newVoteModal" class="modal">
  <!-- Modal content -->
  <div class="modal-content">
  	<div class="modal-header">
	    <span id="modalClose" class="close">&times;</span>
	    <h2>새 투표 생성</h2>
	    <br>
  	</div>
  	<div class="modal-body">
  		<div id="newVoteContainer">
		    <form id="newVoteFrm">
			<input type="text" name="vote_subject" placeholder="투표 제목"><br><br>
			<input type="text" name="vote_con" placeholder="투표 설명..."><br><br>
			<h2 class='voteItems'>투표 항목</h2>
			<table id="voteTbl">
				<tr class="voteItem">
					<td><input type="text" name="vote_item" placeholder="투표 항목..."></td>
					<td><button type="button" class="delItem">삭제</button></td>
				</tr>
				<tr class="voteItem">
					<td><input type="text" name="vote_item" placeholder="투표 항목..."></td>
					<td><button type="button" class="delItem">삭제</button></td>
				</tr>
			</table>
			<button onclick="newItem()" id="addItemBtn" type="button">투표 항목 추가</button>
			<br><br>
			<h2 class='voteItems'>투표 마감일</h2>
			<input type="hidden" name="prj_id" value="${PROJECT_INFO.prj_id }">
			<input type="hidden" name="vote_email" value="${USER_INFO.user_email }">
			<input type="hidden" name="vote_ano" value="Y">
			<div id="end_dt">
				<input type="text" name="vote_end_date" data-input>
				<a class="input-button" title="clear" data-clear> X</a>
				<br>
			</div>
			<br>
			<input type="button" id="newVoteSubmit" onclick="newVote()" class="btn_style_02" value="투표 등록">
			</form>
  		</div>
  	</div>
  	<div class="modal-footer">
  	</div>
  </div>

</div>

<div id="voteDetailModal" class="modal">
  <!-- Modal content -->
  <div class="modal-content-detail">
  	<div class="modal-header">
	    <span id="modalCloseDetail" class="close">&times;</span>
	    <h2 id='detail_vote_subject'></h2>
	    <br>
  	</div>
  	<div class="modal-body">
  		<div id="voteDetailContainer">
		<form id="voteDetailFrm">
			<div id="voteDetail">
			</div>
			<input type="hidden" id='detail_vote_id' name='vote_id'>
			<input type="hidden" name='prj_id' value='${PROJECT_INFO.prj_id }'>
			<input type="hidden" name='user_email' value='${USER_INFO.user_email }'>
			<input type="button" id="btnVote" onclick="vote()" value="투표">
			<input type='button' id='btnCmp' onclick='cmpVote()' value="투표 완료">
	</form>
  		</div>
  	</div>
  	<div class="modal-footer">
  	</div>
  </div>

</div>
</div>
</section>

<script>
var cal = flatpickr("#end_dt", {"locale" : "ko", enableTime: true, wrap:true});
	$(function() {
		var modal = document.getElementById("newVoteModal");
		var modalMdf = document.getElementById("voteMdfModal");
		var modalDetail = document.getElementById('voteDetailModal');
		var btn = document.getElementById("newVoteBtn");
		var closeBtn = document.getElementById("modalClose");
		var closeBtnMdf = document.getElementById("modalCloseMdf");
		var closeBtnDetail = document.getElementById('modalCloseDetail');
		
		btn.onclick = function() { // 새 투표 생성 버튼 클릭했을 때 이벤트
			modal.style.display = "block";
		}
		closeBtn.onclick = function() { // 새 투표 모달 닫기 버튼 클릭했을 때 이벤트
			modal.style.display = "none";
			clearModal();
		}
		
		closeBtnMdf.onclick = function() { // 투표 수정 모달 닫기버튼 클릭했을 때 이벤트
			modalMdf.style.display = "none";
			clearModal();
		}
		
		closeBtnDetail.onclick = function() { // 상세보기 모달 닫기 버튼 클릭했을 때 이벤트
			modalDetail.style.display = "none";
		}
		
		window.onclick = function(event) {
			if(event.target == modal) {
				modal.style.display = "none";
			} else if(event.target == modalMdf) {
				modalMdf.style.display = "none";
			} else if(event.target == modalDetail) {
				modalDetail.style.display = "none";
			}
		}
		
		voteList(1);
		
		 $(".vote_set_list").each(function() { // 로드 완료시 .vote_set_list hide처리
			 $(this).hide();
		 });
		 
		 $("#voteContainer").on("click", ".vote_config", function(){ // .vote_config(설정버튼) 클릭했을 때 이벤트
			 console.log(this);
			 $(this).parent().next().children(".vote_set_list").fadeIn();
		   });
		   $("#voteContainer").on("mouseleave", ".vote_set_list", function(){ // .vote_set_list(수정/삭제)에서 마우스 나갔을 때 이벤트
			   $(this).fadeOut();
		   });
		   
		$("#voteList").on("click", ".vote_subject", function() { // 투표 제목 클릭했을 때 이벤트 (상세보기 전환)
			var voteid = $(this).parent().parent().data("voteid");
			$("#detail_vote_id").val(voteid);
			voteDetail();
			
		});
		
		$("section").on("click", ".delItem", function() { // 새 투표 모달, 투표 수정 모달에서 투표항목 삭제 클릭했을 때 이벤트
			$(this).parent().parent().remove();
		});
		
		$("section").on("click", ".delItemMdf", function() { // 새 투표 모달, 투표 수정 모달에서 투표항목 삭제 클릭했을 때 이벤트
			var itemid = $(this).parent().siblings().children("input[name='vote_item']").data("itemid");
			var input = "<input type='hidden' name='del_item_id' value='"+ itemid +"' >";
			$("#delItems").append(input);
			$(this).parent().parent().remove();
		});
		
		$("#voteContainer").on("click", '.vote_del', function() { // 투표 삭제 클릭했을 때 이벤트
			var conf = confirm("정말 삭제하시겠습니까?");
			if(conf) {
				var vote_id = $(this).parent().siblings("dt").children("input").val();
				deleteVote(vote_id);
			}
		});
		$("#voteContainer").on("click", '.vote_mdf', function() { // 투표 수정 클릭했을 때 이벤트
			var vote_id = $(this).parent().siblings("dt").children("input").val();
			modalMdf.style.display = "block";
			voteDetailMdf(vote_id);
		});
		
		$("#voteContainer").on("click",".item",function() { // 상세보기 모달에서 투표항목 선택했을 때 이벤트
			var checked = $(this).find("input[type=radio]").prop("checked");
			switch(checked) {
				case false:
					$(this).find("input[type=radio]").prop("checked", true);
					break;
				case true:
					$(this).find("input[type=radio]").prop("checked", false);
					break;
			}
		});
		$("#voteContainer").on("mouseenter", ".vote_subject", function() {
			$(this).parent().parent().css("background", "#b3b3ff").css("color", "#ffffff");
		});
		$("#voteContainer").on("mouseout", ".vote_subject", function() {
			$(this).parent().parent().css("background", "#ffffff").css("color", "#000000");
		});
	});
	
	function newItem() { // 새 투표 모달에서 투표항목 추가 클릭했을 때 실행하는 함수
		var table = $("#voteTbl");
		var voteItem = '<tr class="voteItem"><td><input type="text" name="vote_item" placeholder="투표 항목..."></td><td><button type="button" class="delItem">삭제</button></td></tr>';
		$(table).append(voteItem);
	}
	
	function newItemMdf() { // 투표 수정 모달에서 투표항목 추가 클릭했을 때 실행하는 함수
		var table = $("#voteTblMdf");
		var voteItem = '<tr class="voteItemMdf"><td><input type="text" name="vote_item" placeholder="투표 항목..."></td><td><button type="button" class="delItem">삭제</button></td></tr>';
		$(table).append(voteItem);
	}
	function voteList(page) { // 투표 목록 출력하는 함수
		$("#voteFrm input[name='votelist_page']").val(page);
		$.ajax({
			url: '/vote',
			type: 'post',
			data: $("#voteFrm").serialize(),
			success: function(data){
				console.log(data);
				var voteList = data.voteList;
				voteList.replace('&gt;', '<');
				voteList.replace('&lt;', '>');
				$("#voteList").html(voteList);
			}
		});
	}
	function newVote() { // 새 투표 생성하는 함수
		var vals = $("#newVoteFrm").find("input");
		$(vals).each(function() {
			var val = $(this).val().replace(/</gi,"&lt;");
			$(this).val(val);
		});
		var serial = $("#newVoteFrm").serialize();
		
		console.log(serial);
		var valid = voteDtValidate(serial);
		if(valid) {
			$.ajax({
				url: '/newVote',
				type: 'post',
				data: serial,
				success: function(data) {
					console.log(data)
					switch(data) {
						case "OK":
							alert("투표가 생성되었습니다.");
							var modal = document.getElementById("newVoteModal");
							modal.style.display = "none";
							clearModal();
							voteList(1);
							break;
						case "ERROR":
							alert("투표 생성중 오류가 발생했습니다.")
							break;
					}
				}
			});
		}
	}
	function clearModal() { // 모달창 닫았을 때 내용 초기화하는 함수
		document.getElementById("newVoteFrm").reset();
		var tbl = document.getElementsByClassName("voteItem");
		console.log(tbl);
		var leng = tbl.length;
		
		while(leng>2){
			$(tbl[2]).remove();
			leng = tbl.length;
		}
	}
	function deleteVote(vote_id){ // 투표 삭제처리하는 함수
		$.ajax({
			url: '/vote/del',
			type: 'post',
			data : {'vote_id': vote_id},
			success: function(data) {
				switch(data) {
				case "OK":
					alert("삭제가 완료되었습니다.");
					break;
				case "ERROR":
					alert("삭제도중 오류가 발생했습니다.");
					break;
				}
				voteList(1);
			}
		});
	}
	
	function voteDetail() { // 상세보기 모달 내용 출력하는 함수
		$.ajax({
			url: '/voteDetail',
			type: 'post',
			data: $("#voteDetailFrm").serialize(),
			success: function(data) {
				var isVoted = data.isVoted;
				var htmlVoted = data.htmlVoted;
				var html = data.html;
				var voteVo = data.voteVo;
				
				$("#detail_vote_subject").text(voteVo.vote_subject);
				console.log(data);
				
				var vote_email = voteVo.vote_email;
				var vote_st = voteVo.vote_st;
				var user_email = "${USER_INFO.user_email}";
				
				var isEnd = vote_st == "Y" ? true : false;
				console.log(isEnd);
				
				console.log(vote_email);
				console.log(vote_st);
				$("#btnCmp").css("display", "none");
				
				if(user_email==vote_email && "P"==vote_st && isVoted) {
					$("#btnCmp").css("display", "inline-block");
				} else if(isEnd){
					$("#btnCmp").css("display", "none");
				}	
				
				if(isVoted && !isEnd) {
					$("#voteDetail").html(htmlVoted);
					$("#btnVote").attr("value", "다시 투표");
					$("#btnVote").attr("onclick", "revote()");
					$("#btnVote").attr("disabled", false);
				} else if(!isVoted && !isEnd){
					$("#voteDetail").html(html);
					$("#btnVote").attr("value", "투표");
					$("#btnVote").attr("onclick", "vote()");
					$("#btnVote").attr("disabled", false);
				} else if(isEnd) {
					$("#voteDetail").html(htmlVoted);
					$("#btnVote").attr("value", "투표가 종료되었습니다.");
					$("#btnVote").attr("disabled", "true");
				} 
				var modalDetail = document.getElementById('voteDetailModal');
				modalDetail.style.display = 'block';
				
			}
		});
	}
	
	function vote() { // 상세보기 모달에서 투표버튼 눌렀을때 실행하는 함수
		var serial = $("#voteDetailFrm").serialize();
		console.log(serial);
		var valid = validate();
		if(valid) {
			$.ajax({
				url: '/vote/check',
				data: serial,
				type: 'post',
				success: function(data) {
					var vote_page = $("#voteFrm input[name='votelist_page']").val();
					voteList(vote_page);
					voteDetail();
				}
			});
		} else {
			return;
		}
	};
	
	function revote() { //상세보기 모달에서 다시 투표 버튼 눌렀을 때 실행하는 함수
		$.ajax({
			url: '/voteDetail',
			type: 'post',
			data: $("#voteDetailFrm").serialize(),
			success: function(data) {
				var html = data.html;
				$("#voteDetail").html(data.html);
				$("#btnVote").attr("value", "투표");
				$("#btnVote").attr("onclick", "vote()");
				$("#btnCmp").css("display", "none");
			}
		});		
	}
	
	function validate() { // 상세보기 모달에서 투표버튼 눌렀을 때 항목 선택했는지 확인하는 함수
		var leng = $(".item :radio[checked]").length;
		if(leng==0) {
			alert("투표할 항목을 선택해주세요.");
			return false;
		}
		else
			return true;
	}
	
	function voteDtValidate(serial) {
		console.log(serial);
		var endDt = serial.split("vote_end_date=");
		console.log(endDt);
		if(endDt[1] == "") {
			return true;
		}
		endDt = endDt[1];
		var dtArr = endDt.split("+");
		var dtArr2 = dtArr[1].split("%3A");
		endDt = dtArr[0]+"T"+dtArr2[0]+":"+dtArr2[1]+"+09:00";
		var today = new Date();
		console.log(today);
		var end_date = new Date(endDt);
		console.log(end_date);
		var end_date_long = end_date.getTime() - (1000*60*60*24);
		if(today.getTime() > end_date_long) {
			alert("마감일은 24시간 이후로만 설정할 수 있습니다.");
			return false;
		} else {
			return true;
		}
	}
	
	function voteDetailMdf(vote_id) {
		$.ajax({
			url: "/voteDetailMdf",
			type: 'post',
			data: {'vote_id': vote_id},
			success: function(data) {
				console.log(data);
				$("#VoteMdfContainer").html(data.html);
				var calMdf = flatpickr("#end_dt_mdf",  {"locale" : "ko", enableTime: true, defaultDate: data.voteVo.vote_end_date == null ? null : new Date(data.voteVo.vote_end_date), wrap:true});
			}
		});
	}
	
	function voteDtValidateMdf(vote_id, vote_end_date) {
		var bool = null;
		$.ajax({
			url: '/checkDtMdf',
			type: 'post',
			data: {'vote_id': vote_id, 'vote_end_date': vote_end_date },
			async: false,
			success: function(data) {
				bool = data;
			}
		});
		return bool;
	}
	
	function voteMdf() {
		var vals = $("#voteMdfFrm").find("input");
		$(vals).each(function() {
			var val = $(this).val().replace(/</gi,"&lt;");
			$(this).val(val);
		});
		
		var item_del = $("#delItems").serialize();
		
		var mdfFrm = $("#voteMdfFrm");
		var vote_subject = $(mdfFrm).find("input[name='vote_subject']").val();
		var vote_con = $(mdfFrm).find("input[name='vote_con']").val();
		var prj_id = $(mdfFrm).find("input[name='prj_id']").val();
		var vote_email = $(mdfFrm).find("input[name='vote_email']").val();
		var vote_ano = $(mdfFrm).find("input[name='vote_ano']").val();
		var vote_end_date = $(mdfFrm).find("input[name='vote_end_date']").val();
		var vote_id = $(mdfFrm).find("input[name='vote_id']").val();
		
				
		var dt_valid = voteDtValidateMdf(vote_id, vote_end_date);
		
		if(!dt_valid) {
			alert("마감일시는 작성일시 기준 24시간 이후로만 설정할 수 있습니다.");
			return;
		}
		
		if(!(vote_end_date == "")) {
			var end_dt = new Date(vote_end_date);
			console.log(end_dt);
			if(end_dt.getTime() < new Date().getTime()) {
				alert("마감일시는 현재 시간 이후로만 설정할 수 있습니다.");
				return;
			}
		}
		
		if(item_del) {
			$.ajax({
				url: '/voteMdfItems_del',
				type: 'post',
				data: item_del,
				success: function(data){}
			});
		} 
		
		var item = $("#voteMdfFrm input[name='vote_item']");
		var item_arr = new Array();
		$(item).each(function() {
			var itemid = $(this).data("itemid");
			var value = $(this).val();
			var item_obj = {'vote_item_con': value, 'vote_id': vote_id, 'vote_item_id': itemid};
			item_arr.push(item_obj);
		});
		
		
		$.ajax({
			url: '/voteMdfItems',
			type: 'post',
			contentType: 'application/json;charset=utf-8',
			data: JSON.stringify(item_arr),
			success: function(data) {}
		});
		
		var serial = {
				'vote_id': vote_id,
				'vote_subject': vote_subject, 
				'vote_con': vote_con,
				'prj_id': prj_id,
				'vote_email': vote_email,
				'vote_ano': vote_ano,
				'vote_end_date': vote_end_date
		};
		$.ajax({
			url:'/voteMdf',
			type: 'post',
			data: serial,
			success: function(data) {
			}
		});
		alert("수정이 완료되었습니다.");
		var vote_page = $("#voteFrm input[name='votelist_page']").val();
		voteList(vote_page);
		var modalMdf = document.getElementById("voteMdfModal");
		modalMdf.style.display = "none";
	}
	
	
	function cmpVote() {
		var serial = $("#voteDetailFrm").serialize();
		console.log(serial);
		var bool = confirm("투표를 완료하시겠습니까?");
		
		if(bool) {
			$.ajax({
				url: '/cmpVote',
				type: 'post',
				data : serial,
				success: function() {
					alert("투표가 완료처리 되었습니다.");
					voteDetail();
					var page = $("#voteFrm input[name='votelist_page']").val();
					voteList(page);
				}
			});
		}
	}
	
</script>