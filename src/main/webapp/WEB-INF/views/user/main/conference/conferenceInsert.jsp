<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
	$(document).ready(function(){
		
	});
	function insert(){
		var user_email = [];
		$("input[name=attender]:checked").each(function(){
			user_email.push($(this).val());
		});
		
		$('#user_email').val(user_email);
		
		var subject = $('#subject').val();
		var special = $('#special').val();
		$('#insertSubject').val(subject);
		$('#insertSpecial').val(special);
		
		$("#insertForm").submit();
		
	}
	

	//layer popup - 프로젝트 생성
	function layer_popup(el){
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


<fieldSet class="insertMinutesFs">
	<form id="insertForm" action="/insertConference" method="post">
		<input type="hidden" id="user_email" name="user_email">
		<input type="hidden" id="insertSubject" name="insertSubject">
		<input type="hidden" id="insertSpecial" name="insertSpecial">
	</form>
	
	<label>♬ 참석자   : </label>
	<c:forEach items="${userList}" var="uList">
		<input type="checkbox" id="check" value="${uList.user_email}" name="attender">${uList.user_nm}
	</c:forEach>
		
	<div>
		<label>♬♩♪ 회의내용</label><br>
		<textarea id="subject"></textarea>
	</div>
	<br>
	<div>
		<label>♬♩♪ 특이사항</label><br>
		<textarea id="special"></textarea>
	</div> 
	<input type="button" id="writer" onclick="insert()" value="작성완료">&nbsp;&nbsp;&nbsp;
	<a type="button" href="/conferenceList">뒤로</a>

    
</fieldSet>

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