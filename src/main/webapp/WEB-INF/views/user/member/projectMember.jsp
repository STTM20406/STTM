<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

여기로 올까요?

<style>
	.userTr:hover{
		cursor: pointer;
	}
	
	/* 모달 설정 스타일 */
	.layer {display:none; position:fixed; _position:absolute; top:0; left:0; width:100%; height:100%; z-index:100;}
		.layer .bg {position:absolute; top:0; left:0; width:100%; height:100%; background:#000; opacity:.5; filter:alpha(opacity=50);}
		.layer .pop-layer {display:block;}
	
	.pop-layer {display:none; position: absolute; top: 50%; left: 50%; width: 410px; height:auto;  background-color:#fff; border: 5px solid #3571B5; z-index: 10;}	
	.pop-layer .pop-container {padding: 20px 25px;}
	.pop-layer p.ctxt {color: #666; line-height: 25px;}
	.pop-layer .btn-r {width: 100%; margin:10px 0 20px; padding-top: 10px; border-top: 1px solid #DDD; text-align:right;}
	
	a.cbtn {display:inline-block; height:25px; padding:0 14px 0; border:1px solid #304a8a; background-color:#3f5a9d; font-size:13px; color:#fff; line-height:25px;}	
	a.cbtn:hover {border: 1px solid #091940; background-color:#1f326a; color:#fff;}
</style>

<script>
$(document).ready(function(){
	
	// ------------------------------------------------------
	//사용자 tr 태그 이벤트 등록
	$(".userTr").on("click", function(){
		console.log("userTr click");
		//userId를 획득하는 방법
		//$(this).find(".userId").text();
		//$(this).data("userid");
		
		//사용자 아이디를 #userId 값으로 설정해주고
		var user_email = $(this).find(".user_email").text();
		$("#prjMemList").val(user_email);
		
		//#frm 을 이용하여 submit();
		$("#prjMemForm").submit();
	});
	// ------------------------------------------------------
});	

//------- 모달 설정 스크립트 -------
function layer_open(el){

	var temp = $('#' + el);
	var bg = temp.prev().hasClass('bg');	//dimmed 레이어를 감지하기 위한 boolean 변수

	if(bg){
		$('.layer').fadeIn();	//'bg' 클래스가 존재하면 레이어가 나타나고 배경은 dimmed 된다. 
	}else{
		temp.fadeIn();
	}

	//  -------화면의 중앙에 레이어를 띄운다. -------
	if (temp.outerHeight() < $(document).height() ) temp.css('margin-top', '-'+temp.outerHeight()/2+'px');
	else temp.css('top', '0px');
	if (temp.outerWidth() < $(document).width() ) temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
	else temp.css('left', '0px');

	temp.find('a.cbtn').click(function(e){
		if(bg){
			$('.layer').fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다. 
		}else{
			temp.fadeOut();
		}
		e.preventDefault();
	});

	$('.layer .bg').click(function(e){	//배경을 클릭하면 레이어를 사라지게 하는 이벤트 핸들러
		$('.layer').fadeOut();
		e.preventDefault();
	});

}			
</script>

<section class="contents">

	<div id="tab-1" class="tab-content current">
		
		<!--  -->
		<form id="prjMemForm" action="/projectMemberList" method="get">
			<input type="hidden" id="prjMemList" name="prjMemList" >
		</form>
		
		<div>
			<table class="tb_style_01">
				<colgroup>
					<col width="10%">
					<col width="40%">
					<col width="30%">
					<col width="10%">
					<col width="10%">
				</colgroup>
				<tbody>
					<tr>
					
						<th>사용자 이메일</th>
						<th>사용자 이름</th>
	
						<c:forEach items="${projectMemList}" var="prjVo">
						
							<tr class="userTr" data-user_email="${prjVo.user_email }">
								<td class="user_email">${prjVo.user_email}</td>
								<td>${prjVo.user_nm}</td>
							</tr>
							
						</c:forEach>
						
					</tr>
				</tbody>
			</table>
		</div>
		
		<a href="/admInsertUser" class="btn btn-default pull-right">사용자 등록</a>
	
		<div class="pagination">
				<c:choose>
					<c:when test="${pageVo.page == 1 }">
						<a href class="btn_first"></a>
					</c:when>
					<c:otherwise>
						<a href="${cp}/projectMemberList?page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
					
					</c:otherwise>
				</c:choose>
	
				<c:forEach begin="1" end="${paginationSize}" var="i">
					<c:choose>
						<c:when test="${pageVo.page == i}">
							<span>${i}</span>
						</c:when>
						<c:otherwise>
						<a href="${cp}/projectMemberList?page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
						</c:otherwise>
					</c:choose>
	
				</c:forEach>
	
				<c:choose>
					<c:when test="${pageVo.page == paginationSize}">
						<a href class="btn_last"></a>
					</c:when>
					
					<c:otherwise>
						<a href="${cp}/projectMemberList?page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
					</c:otherwise>
				</c:choose>
		
		</div>
	</div>

</section>