<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <!-- flatpickr.js 시작 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://npmcdn.com/flatpickr/dist/l10n/ko.js"></script>
<!-- flatpicker.js 끝 -->
<div id="newVoteContainer">
	<form id="newVoteFrm" action="/newVote" method="post">
		<input type="text" name="vote_subject" placeholder="투표 제목"><br><br>
		<textarea name="vote_con" placeholder="투표 설명" cols=30 rows="7" style="resize:none;"></textarea><br>
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
		<button onclick="newItem()" type="button">투표 항목 추가</button>
		<br>
		<b>기타 설정</b>
		<input type="hidden" name="prj_id" value="${PROJECT_INFO.prj_id }">
		<input type="hidden" name="vote_email" value="${USER_INFO.user_email }"><br>
		<input type="checkbox" name="vote_ano" value="Y"> 익명 투표<br>
		투표 마감일 : <input type="text" id="end_dt" name="vote_end_date"> <br>
		<br>
		<input type="submit" value="투표 등록">
	</form>
</div>
<script>
function newItem() {
	var table = $("#voteTbl");
	var voteItem = '<tr class="voteItem"><td><input type="text" name="vote_item" placeholder="투표 항목..."></td><td><button type="button" class="delItem">삭제</button></td></tr>';
	$(table).append(voteItem);
}

var cal = flatpickr("#end_dt", {"locale" : "ko", enableTime: true});
$(function() {
	$("#voteTbl").on("click", ".delItem", function() {
		$(this).parent().parent().remove();
	});
});
</script>