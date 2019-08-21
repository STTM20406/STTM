<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
	.chatBot{width:490px; height: 490px; background : #f5f5f5; position: fixed; left : 240px; bottom:0; }
	.chatContent {overflow-y:scroll; height:400px}
 	.chatContent:after {content:""; clear:both; display:block; zoom:1}
	.chatContent .bot {display:block; width:100%}
	.chatContent .me {display:block; width:100%; text-align:right}
	.chatInput {position:absolute; bottom:0; left:0; width:100%; height:100px}
	
	#question{width:490px; resize: none;}
</style>

// 문자열 제일 앞의 화이트스페이스(공백, 엔터, 탭) 을 제거함으로써 원하는 정리가 됨.
		text1.value = frm.text1.value.replace(/^\s/gm, '');

		// 위의 정규식으로 없앨수 없는 제일 마지막줄 빈엔터를 제거함.
		frm.text1.value = frm.text1.value.replace(/\r\n$/g, '');


<script>
	$(document).ready(function(){
		$('.chatBot').hide();
		$('#chatBotBtn').on('click',function(){
			$('.chatBot').show(250);
		})
		
		//업무리스트 이름 입력 후 엔터 또는 다른곳 클릭시 업무리스트 추가하는 ajax실행 
		$(".chatInput").on("keydown change", "#question", function(key) {
			if (key.keyCode == 13) {
				if($('#question').val().length > 20){
					$('#question').val($('#question').val().substring(0, 20));		
					alert("20자 이내로 입력해주세요~");
				}else if($('#question').val().trim().length == 0){
					alert("질문 하실 사항을 입력해주세요~");
					$('#question').selectRange(0,0);
					return;
				}else{
					var question = $('#question').val();
					var name = "${user_nm}";
					
					var me = "";
					
					me += "<dl class='me'>";
					me += "<dt>" + name + "님</dt>";
					me += "<dd>Q : " + question + "</dd>";
					me += "<dl>";
					
					$('.chatContent').append(me);
					$('#question').val('');
					
					$.ajax({
						url:"/chatBotApi",
						method:"post",
						data : "question=" + question,
						success:function(data){
							console.log(data);
							var answer = data.data;
							alert(answer);
							var bot = "";
							//html 생성
							bot += "<dl class='bot'>";
							bot += "<dt> 챗봇잉 </dt>";
							bot += "<dd>A :" + answer +"</dd>";
							bot += "<dl>";
							$('.chatContent').append(bot);
						}
						
					});
				}
			}
		});
		
		$('#gigi').on('click',function(){
			if($('#question').val().length > 20){
				$('#question').val($('#question').val().substring(0, 20));		
				alert("20자 이내로 입력해주세요~");
			}else if($('#question').val().length == 0){
				alert("질문 하실 사항을 입력해주세요~");
			}else{
				var question = $('#question').val();
				var name = "${user_nm}";
				
				var me = "";
				
				me += "<dl class='me'>";
				me += "<dt>" + name + "님</dt>";
				me += "<dd>Q : " + question + "</dd>";
				me += "<dl>";
				
				$('.chatContent').append(me);
				$('#question').val('');
				
				$.ajax({
					url:"/chatBotApi",
					method:"post",
					data : "question=" + question,
					success:function(data){
						console.log(data);
						var answer = data.data;
						alert(answer);
						var bot = "";
						//html 생성
						bot += "<dl class='bot'>";
						bot += "<dt> 챗봇잉 </dt>";
						bot += "<dd>A :" + answer +"</dd>";
						bot += "<dl>";
						$('.chatContent').append(bot);
					}
					
				});
			}
		})
	});
		
</script>

<!-- side bar -->
<div class="sidebar">
	<div class="logo">
		<h2><a href="#">project</a></h2>
		<div class="auth">
			<p class="auth_txt"><span>제한</span></p>
			<ul class="auth_list">
				<li>전체 액세스</li>
				<li>제한 액세스</li>
				<li>통제 액세스</li>
			</ul>
		</div>
	</div>
	<div class="project_list_option">
		<select>
			<option>병원 사이트 리뉴얼</option>
			<option>창업지원플랫폼 구축</option>
		</select>
	</div>
	<nav id="gnb">
		<ul>
			<li><a href="#">NOTIFY</a></li>
			<li><a href="/overview/analysis">WORK LIST</a></li>
			<li><a href="/project/list">PROJECT LIST</a></li>
			<li><a href="/projectMemberList">MEMBER</a></li>
			<li><a href="/friendChatList">CHATTING</a></li>
			<li><a href="/userInquiry">INQUERY</a></li>
			<c:forEach items="${userBoardListY }" var = "board">
				<li class="board_id"><a href="/community?board_id=${board.board_id }" id="${board.board_id }">${board.name }</a></li>
			</c:forEach>
			<li><a href="#" id="chatBotBtn">ChatBot</a></li>
		</ul>
	</nav>
</div>

<div class="chatBot">
	<div class="chatContent">
		<dl class="bot">
			<dt>챗봇잉</dt>
			<dd>A : 안녕하세유~ 저는 챗봇잉이예유~ 무엇을 도와 드려유?</dd>
		</dl>
		
		
	</div>
	
	<div class="chatInput">
		<textarea name="question" id="question" placeholder=" 입력해 주세요." rows="3" cols="40"></textarea>
		<input type="button" id="gigi" value="모내기">
	</div>
</div>






