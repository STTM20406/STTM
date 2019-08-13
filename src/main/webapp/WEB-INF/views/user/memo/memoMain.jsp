<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>
	<div id="memo">
		<form id="memoFrm">
			<label>어제 한 일 :</label> <br>
				<textarea rows="5" cols="30" id="memo_yd_con" readonly style="resize:none;"></textarea><br>
			<label>오늘 할 일 :</label> <br>
				<textarea rows="5" cols="30" name="memo_con" id="memo_con" style="resize:none;"></textarea><br>
			<input type="hidden" name="memo_email" value="${USER_INFO.user_email }">
			<input type="hidden" name="prj_id" value="1">
			<button type="button" onclick="copyTask(this)">복사하기</button>
			<button type="button" onclick="memoList()">목록</button>
		</form>
	</div>
	<div id="memoList"></div>
	<div id="memoDetail"></div>
</div>

<script>
	$(function() {
		$("textarea").change(function(){
			var td_con = $("#memo_td_con").val();
			var serial = $("#memoFrm").serialize();
			mergeMemo(serial);
		});
		$("#memoList").on("click", ".memoList", function(){
			var prj_id = $(this).parents().find("table").data("prj_id");
			var user_email = $(this).parents().find("table").data("memo_email");
			var dt_str = $(this).data("memo_dt_str");
			console.log(user_email);
			console.log(prj_id);
			console.log(dt_str);
			
			var memoVo = {"memo_email": user_email, "memo_dt_str": dt_str, "prj_id": prj_id};
			getMemo(memoVo);
		});
		
		$("#memoList").on("click", ".todayMemo", function(){
			todayMemo();
		});
		
		getYdTdCon();
		$("#memoList").hide();
		$("#memoDetail").hide();
	});
	
	function copyTask(){
		console.log("copyTask")
		var btn = $(btn).parent().find("#memo_con");
		btn.select();
		document.execCommand('copy');
		console.log("Copied!");
	};
	
	function mergeMemo(serialData) {
		$.ajax({
			url: "/merge",
			data: serialData,
			type: "POST",
			success: function(data) {
				console.log(data);
			}
		});
	}
	
	function getYdTdCon() {
		$.ajax({
			url: '/yd_con',
			type: "POST",
			data: $("#memoFrm").serialize(),
			success: function(data) {
				console.log(data);
				$("#memo_con").val(data.td_con.memo_con);
				$("#memo_yd_con").val(data.yd_con.memo_con);
			}
		});
	}
	
	function memoList() {
		var serial = $("#memoFrm").serialize();
		$.ajax({
			url: "/memoList",
			type: "POST",
			data: serial,
			success: function(data){
				console.log(data);
				$("#memo").hide();
				$("#memoDetail").hide();
				$("#memoList").html(data.memoList);
				$("#memoList").show();
			}
		})
	}
	function getMemo(memoVo) {
		$.ajax({
			url: "/getMemo",
			type: "POST",
			data: memoVo,
			success: function(data){
				console.log(data);
				$("#memoList").hide();
				$("#memoDetail").html(data.memo);
				$("#memoDetail").show();
			}
		})		
	}
	
	function todayMemo() {
		$("#memoList").hide();
		$("#memo").show();
	}
</script>