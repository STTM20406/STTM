<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.memoTr:hover{
	cursor:pointer;
}
</style>


<script>
$(document).ready(function(){
	
	$(".memoTr").on('click',function(){
		var memoId = $(this).find('#memoId').text();
		var memoCon = $(this).find('#memoCon').text();
		var memoDate = $(this).find('#memo_Date').text();
		
		$("#smarteditor").val(memoCon);
		$("#memoDate").text(memoDate);
		
		layer_popupup(layer1);
		
		
	})
	
})
 function layer_popupup(el){

	        var $el = $(el);      //레이어의 id를 $el 변수에 저장
	        var isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수

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

<!-- 받은 쪽지 상세내용 팝업 -->
<form class="memoFrm">
		<div id="layer1" class="pop-layer">
			<div class="pop-container">
				<div class="pop-project">
					<!--content //-->
						<input type="hidden" id="array" name="array">
						<div class="new_proejct">
							<ul>
								<li>
									<label>작성한 날짜 : </label>
									<label id="memoDate"></label>
									<input type="hidden" name="rcvEmail" id="rcvEmaildInput" value=""/>
								</li>
								<li>
									<div>
										<br><textarea name="smarteditor" readonly="readonly" id="smarteditor" rows="10" cols="100" style="width: 460px; height: 330px;"></textarea>
									</div>
								</li>
							</ul>
						</div>
					<div class="btn-r">
						<a href="#" class="btn-layerClose">Close</a>
					</div>
					<!--// content-->
				</div>
			</div>
		</div>
</form>

<section class="contents">
	<div>
		<table class="tb_style_01">
			<thead>
				<tr>
					<th>내가 한 일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ memoList}" var="memo">
					<tr class="memoTr">
						<td style="display:none;" id="user_email">${USER_INFO.user_email }</td>
						<td id="memo_Date"><fmt:formatDate value="${memo.memo_update }" pattern="yyyy년MM월dd일"/></td>
						<td style="display:none;" id="memoId">${memo.memo_id }</td>
						<td style="display:none;" id="memoCon">${memo.memo_con }</td>
					</tr>			
				</c:forEach>
			</tbody>
		</table>
	</div>
</section>