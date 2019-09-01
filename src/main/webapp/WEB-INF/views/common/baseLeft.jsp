<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
	.chatBot{border:solid 2px black; width:490px; height: 490px; background : #F6CECE; position: fixed; left : 250px; bottom:0; display:none}
	.chatContent {overflow-y:scroll; height:400px}
 	.chatContent:after {content:""; clear:both; display:block; zoom:1}
	.chatContent .bot {display:block; width:100%}
	.chatContent .me {display:block; width:100%; text-align:right}
	.chatInput {position:absolute; bottom:0; left:0; width:100%; height:80px}
	
	.btnSetClose{float:right;}
	.bot{background : #F78181;}
	.me{background : #F7BE81;}
	
	#question{width:490px; resize: none; background : #F6CECE; border:solid 1px black;}
	
</style>

<script>
	$(document).ready(function(){
		
		$("#selectProject").on("change", function(){
			var projectID = $(this).children("option:selected").attr("id");
			$("#projectId").val(projectID);
			$("#projectFrm").submit();
		});
		
		$('#chatBotBtn').on('click',function(){
			$('.chatBot').show(250);
		})
		
		$(".btnSetClose").on("click", function(){
			$(".chatBot").fadeOut(250);
		});
		
		//업무리스트 이름 입력 후 엔터 또는 다른곳 클릭시 업무리스트 추가하는 ajax실행 
		$(".chatInput").on("keydown change", "#question", function(key) {
			if (key.keyCode == 13) {
				if($('#question').val().length > 20){
					$('#question').val($('#question').val().substring(0, 20));		
						$(".ctxt").text("20자 이내로 입력해주세요~");
		 				layer_popup("#layer2");
						return false;
				}else{
					var question = $('#question').val();
					var name = "${user_nm}";
					
					var me = "";
					
					me += "<dl class='me'>";
					me += "<dt>" + name + "님&nbsp;&nbsp;</dt>";
					me += "<dd>Q : " + question + "&nbsp;&nbsp;</dd>";
					me += "<dl>";
					
					$('.chatContent').append(me);
					$('#question').val('');
					
					$.ajax({
						url:"/chatBotApi",
						method:"post",
						data : "question=" + question,
						success:function(data){
							var answer = data.data;
							var bot = "";
							
							if(answer == "감사합니다"){
								$(".chatBot").fadeOut(3000);
							}
							//html 생성
							bot += "<dl class='bot'>";
							bot += "<dt>&nbsp;&nbsp;챗봇잉 </dt>";
							bot += "<dd>A:&nbsp;&nbsp;" + answer +"</dd>";
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
					$(".ctxt").text("20자 이내로 입력해주세요~");
	 				layer_popup("#layer2");
					return false;
			}else{
				var question = $('#question').val();
				var name = "${user_nm}";
				
				var me = "";
				
				me += "<dl class='me'>";
				me += "<dt>" + name + "님&nbsp;&nbsp;</dt>";
				me += "<dd>Q : " + question + "&nbsp;&nbsp;</dd>";
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
						bot += "<dt>&nbsp;&nbsp;챗봇잉 </dt>";
						bot += "<dd>A :&nbsp;&nbsp;" + answer +"</dd>";
						bot += "<dl>";
						$('.chatContent').append(bot);
					}
					
				});
			}
		});
	});
		
	
</script>

<form id="projectFrm" action="/work/list" method="post">
	<input type="hidden" name="prj_id" id="projectId" value="">
</form>

<!-- side bar -->
<div class="sidebar">
	<div class="logo">
		<h2><a href="/login">project</a></h2>
<!-- 		<div class="auth"> -->
<!-- 			<p class="auth_txt"><span>제한</span></p> -->
<!-- 			<ul class="auth_list"> -->
<!-- 				<li>전체 액세스</li> -->
<!-- 				<li>제한 액세스</li> -->
<!-- 				<li>통제 액세스</li> -->
<!-- 			</ul> -->
<!-- 		</div> -->
	</div>
	<div class="project_list_option">
		<select id="selectProject" name="selectProject">
			<option>프로젝트를 선택해 주세요</option>
			<c:forEach items="${projectList}" var="projectList">
			<option id="${projectList.prj_id}">${projectList.prj_nm}</option>
			</c:forEach>
		</select>
	</div>
	<nav id="gnb">
		<ul>
			<li><a href="/notification" id="aTagCountReset">NOTIFY<span id="spanCountReset">${USER_INFO.count_notify }</span></a></li>
			<li><a href="/overview/analysis">WORK LIST</a></li>
			<li><a href="/project/list">PROJECT LIST</a></li>
			<li><a href="/projectMember">MEMBER</a></li>
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
		<button class="btnSetClose">닫기</button>
		
		<dl class="bot">
			<dt>&nbsp;&nbsp;챗봇잉</dt>
			<dd>A :&nbsp;&nbsp;안녕하세유~ 저는 챗봇잉이예유~ 무엇을 도와 드려유?</dd>
		</dl>
	</div>
	
	<div class="chatInput">
		<textarea name="question" id="question" placeholder=" 입력해 주세요." rows="3" cols="40"></textarea>
		<input type="button" id="gigi" value="보내기 말고 모내기">
	</div>
	
</div>

<!-- 오류 알림창 -->
<!-- <div class="dim-layer"> -->
<!-- 	<div class="dimBg"></div> -->
<div id="layer2" class="pop-layer">
	<div class="pop-container">
		<div class="pop-conts">
			<!--content //-->
			<p class="ctxt mb20"></p>
			<div class="btn-r">
				<a href="#" class="btn-layerClose">Close</a>
			</div>
			<!--// content-->
		</div>
	</div>
</div>
<!-- </div> -->




