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
	<form id="frm">
		<div>
			
			<input type="hidden" name="sendEmail" id="sendEmail" value="${send_email }"/>
			받는 사람 :<label>${rcv_email }</label>
			 <input type="hidden" name="rcvEmail" value="${rcv_email }"/>
			<button type="button">친구목록</button> <br>
			내용 : <br>
			<textarea name="smarteditor" id="smarteditor" rows="10" cols="100" style="width: 766px; height: 412px;"></textarea>	<br>
			<button type="button" id="sendBtn" class="btn_style_01">보내기</button>
		</div>
	</form>

</section>