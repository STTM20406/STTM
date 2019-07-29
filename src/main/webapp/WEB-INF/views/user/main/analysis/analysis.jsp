<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		    	<input type="hidden" name="user_email" value="son@naver.com">
	    </form>
    </div>
        <div id="resultContainer" style="width:400px;padding:25px;height:100%; float:left;"></div>
        <div id="work_detail" style="width:600px;padding:25px;height:100%;float:left;"></div>
<script>
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
				console.log(filterFrm);
				$("#resultContainer").html(result);
// 				$("#frmContainer").html(filterFrm);
				$("#prjList").html(prjList);
				$("#makerList").html(makerList);
				$("#followerList").html(followerList);
			}
		})
	}
	
	function workDetail(){
		var wrk_id = $(".result").data("wrk_id");
		console.log(wrk_id);
		$.ajax({
			url: "/filter/detail",
			type: "post",
			data: {wrk_id: wrk_id},
			success: function(data){
				var workDetail = data.workDetail;
				console.log(workDetail);
				
				$("#work_detail").html(workDetail);
			}
		});
	}	
		$("#resultContainer").on("click", ".result", function(){
			workDetail(this);	
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
</script>