<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
            $('.dim-layer').fadeOut();
            return false;
        });

    }
</script>
<form class="rcvFrm">
		<div id="layer1" class="pop-layer">
			<div class="pop-container">
				<div class="pop-project">
					<!--content //-->
						<input type="hidden" id="array" name="array">
						<div class="new_proejct">
							<label>친구목록</label>
							<div>
							<c:forEach items="${friendList }" var="fri">
								<input type="checkbox" name="friend" class="checkSelect" value="${fri.frd_email}">${fri.user_nm }
							</c:forEach>
							</div>
						</div>
					<div class="btn-r">
						<button type="button" id="rcvBtn" class="rcvbtn-style"> 확인</button>
						<a href="#" class="btn-layerClose">Close</a>
					</div>
					<!--// content-->
				</div>
			</div>
		</div>
</form>
<section class="contents">
	<form id="frm">
		<div>
			
			<input type="hidden" name="sendEmail" id="sendEmail" value="${send_email }"/>
			받는 사람 : <input type="text" name="rcvEmail" value=""/>
			<button type="button" id="friendList">친구목록</button> <br>
			내용 : <br>
			<textarea name="smarteditor" id="smarteditor" rows="10" cols="100" style="width: 766px; height: 412px;"></textarea>	<br>
			<button type="button" id="sendBtn" class="btn_style_01">보내기</button>
		</div>
	</form>

</section>