<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	#voteContainer {width:1000px;}
	#voteDetail {width:90%;}
	.item {width:400px; font-size: large; border:1px solid black; margin:5px; cursor: pointer;}
	.item_con{ width:75%; float:left; margin-left:30px; }
	.item input[type=radio] {width:15px; height:15px;}
	#voteFrm input[type=button] {width: 150px; height:30px; margin-left:125px;}
	.voted {background-color: #e1e1e1;}
	.selected {background-color: yellow;}
</style>
<div id="voteContainer">
	<form id="voteFrm">
		<div id="voteDetail">
		</div>
		<input type="hidden" name='vote_id' value='${vote_id }'>
		<input type="hidden" name='prj_id' value='${PROJECT_INFO.prj_id }'>
		<input type="hidden" name='user_email' value='${USER_INFO.user_email }'>
		<input type="button" id="btnVote" onclick="vote()" value="투표">
	</form>
</div>
<script>
	$(function() {
		voteDetail();
		
		$("#voteContainer").on("click",".item",function() {
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
		
		
	});
	
	function voteDetail() {
		$.ajax({
			url: '/voteDetail',
			type: 'post',
			data: $("#voteFrm").serialize(),
			success: function(data) {
				var isVoted = data.isVoted;
				var htmlVoted = data.htmlVoted;
				var html = data.html;
				console.log(data);
				if(isVoted==true) {
					$("#voteDetail").html(htmlVoted);
					$("#btnVote").attr("value", "다시 투표");
					$("#btnVote").attr("onclick", "revote()");
				} else {
					$("#voteDetail").html(data.html);
				}
			}
		});
	}
	
	function vote() {
		var serial = $("#voteFrm").serialize();
		console.log(serial);
		var valid = validate();
		
		if(valid) {
			$.ajax({
				url: '/vote/check',
				data: serial,
				type: 'post',
				success: function(data) {
					voteDetail();
				}
			});
		} else {
			return;
		}
	};
	
	function revote() {
		$.ajax({
			url: '/voteDetail',
			type: 'post',
			data: $("#voteFrm").serialize(),
			success: function(data) {
				var html = data.html;
				$("#voteDetail").html(data.html);
				$("#btnVote").attr("value", "투표");
				$("#btnVote").attr("onclick", "vote()");
			}
		});		
	}
	function validate() {
		var leng = $(".item :radio[checked]").length;
		if(leng==0) {
			alert("투표할 항목을 선택해주세요.");
			return false;
		}
		else
			return true;
	}
</script>
