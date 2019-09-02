<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<script>
$(document).ready(function(){
	$("#sendBtn").on('click',function(){
		console.log("clickBtn");
		var p = $('#sendEmail').val();
		console.log(p);
		
		$("#frm").attr("action","/rcvNoteWrite");
		$("#frm").attr("method","POST");
		$("#frm").submit();
		
	})
	
})
</script>
<section class="contents">
	<h2 class="contentTitle">답장 보내기</h2>
	<div class="noteWrap">
		<form id="frm">
			<div class="noteBox">
				<div class="noteHd">
					<input type="hidden" name="sendEmail" id="sendEmail" value="${send_email }"/>
					받는 사람 <label>${rcv_email }</label>
					 <input type="hidden" name="rcvEmail" value="${rcv_email }"/>
				</div>
				<div class="noteCon">
					<textarea name="smarteditor" id="smarteditor" rows="10" style="width:100%"></textarea>
					<button type="button" id="sendBtn" class="btn_style_01">보내기</button>
				</div>
			</div>
		</form>
	</div>
</section>

