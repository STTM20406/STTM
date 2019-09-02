<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>


$(document).ready(function(){
	$("#sendBtn").on('click',function(){
		console.log("clickBtn");
		var p = $('#sendEmail').val();
		console.log(p);
		
		$("#frm").attr("action","/noteWrite");
		$("#frm").attr("method","POST");
		$("#frm").submit();
		
	})
	
	$("#friendList").on('click',function(){
		console.log("clickFriendList Click");
		 layer_popup(layer1);
	})
	
	$("#checkBtn").on('click',function(){
		console.log("checkBtn Click");
		var chkbox = $(".checkSelect");
		
		var email = ""; 
		for(i=0;i<chkbox.length;i++){
			if(chkbox[i].checked == true){
				email += chkbox[i].value + ",";
			}
		}
		$("#rcvEmail").val(email);
		
		if(email == ""){
			return false;
		}else{
			$('.pop-layer').fadeOut();
			return false;
		}
		
	})
	
})


	function frined_popup(el){
		console.log(el);

        var $el = $(el);		//레이어의 id를 $el 변수에 저장
        var isDim = $el.prev().hasClass('dimBg');	//dimmed 레이어를 감지하기 위한 boolean 변수

        isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

        var $elWidth = ~~($el.outerWidth()),
            $elHeight = ~~($el.outerHeight()),
            docWidth = $(document).width(),
            docHeight = $(document).height();

        // 화면의 중앙에 레이어를 띄운다.
        if ($elHeight < docHeight || $elWidth < docWidth) {
            $el.css({
                marginTop: -$elHeight /2,
                marginLeft: -$elWidth/2
            })
        } else {
            $el.css({top: 0, left: 0});
        }

        $el.find('a.btn-layerClose').click(function(){
            isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
            return false;
        });

        $('.layer .dimBg').click(function(){
//             $('.dim-layer').fadeOut();
//             return false;
        });

    }
</script>
<form class="rcvFrm">
	<div id="layer1" class="pop-layer">
		<div class="pop-container">
			<div class="pop-addFriend">
				<!--content //-->
					<input type="hidden" id="array" name="array">
					<div class="new_proejct">
						<h2>친구목록</h2>
						<ul class="friendListNote">
							<c:forEach items="${friendList }" var="fri">
								<input type="checkbox" name="rcv_emailList" class="checkSelect" value="${fri.frd_email}">${fri.user_nm }
							</c:forEach>
						</ul>
						<div class="btn-r">
							<a href="#" class="btn-layerClose">Close</a>
						</div>
						<div class="prj_btn">
							<button type="button" id="checkBtn" class="btn_style_01" style="width:70%; margin:0 auto">선택하기</button>
						</div>
					</div>
				<!--// content-->
			</div>
		</div>
	</div>
</form>
<section class="contents">
	<h2 class="contentTitle">쪽지 쓰기</h2>
	<div class="noteWrap">
		<form id="frm">
			<div class="noteBox">
				<div class="noteHd">
					<input type="hidden" name="sendEmail" id="sendEmail" value="${send_email }"/>
					<input type="text" name="rcvEmail" id="rcvEmail" value="" placeholder="받는 사람">
					<button type="button" id="friendList">친구목록</button> <br>
				</div>
				<div class="noteCon">
					<textarea name="smarteditor" id="smarteditor" rows="10" cols="100" style="width: 100%;"></textarea>
					<button type="button" id="sendBtn" class="btn_style_01">보내기</button>
				</div>
			</div>
		</form>
	</div>
</section>