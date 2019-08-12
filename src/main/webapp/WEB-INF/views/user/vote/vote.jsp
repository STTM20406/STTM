<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	#voteContainer {width: 100%; height:100%; float:left;}
	#voteList {width:600px; height:100%; float:left;}
	.votes {width:300px; height:50px; border:1px solid black;}
	.votes span {text-align: center; margin-left: 15px; margin-top:10px;}
</style>
<div id="voteContainer">
	<div id="voteList">
		
	</div>
	<button id="newVoteBtn" onclick="newVote()" class="btn_style_01">새 투표 작성</button>
</div>
<form id="voteFrm">
	<input type="hidden" name="prj_id" value="${PROJECT_INFO.prj_id }">
</form>
<script>
	$(function() {
		voteList();
		
		$("#voteList").on("click", ".votes", function() {
			var voteid = $(this).data("voteid");
			voteDetail(voteid);
		});
	});
	
	function voteDetail(voteid) {
		window.location = "/voteDetail?vote_id="+voteid;
	}
	
	function voteList() {
		$.ajax({
			url: '/vote',
			type: 'post',
			data: $("#voteFrm").serialize(),
			success: function(data){
				$("#voteList").html(data.voteList);
			}
		});
	}
	function newVote() {
		window.location = '/newVote';
	}
</script>