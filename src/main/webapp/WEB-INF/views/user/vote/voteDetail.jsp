<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="voteContainer">
</div>
<script>
	$(function() {
		voteDetail(${vote_id});
	});
	
	function voteDetail(vote_id) {
		$.ajax({
			url: '/voteDetail',
			type: 'post',
			data: {"vote_id": vote_id},
			success: function(data) {
				console.log(data);
			}
		});
	}
</script>
