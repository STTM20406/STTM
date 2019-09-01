<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Favicon -->
<link rel="shortcut icon" href="favicon.ico" />

<script src="${cp}/SE2/js/HuskyEZCreator.js"></script>
<script>
	var oEditors = []; // 개발되어 있는 소스에 맞추느라, 전역변수로 사용하였지만, 지역변수로 사용해도 전혀 무관 함.

	$(document).ready(
			function() {

				// Editor Setting
				nhn.husky.EZCreator.createInIFrame({
					oAppRef : oEditors, // 전역변수 명과 동일해야 함.
					elPlaceHolder : "smarteditor", // 에디터가 그려질 textarea ID 값과 동일 해야 함.
					sSkinURI : "${cp}/SE2/SmartEditor2Skin.html", // Editor HTML
					fCreator : "createSEditor2", // SE2BasicCreator.js 메소드명이니 변경 금지 X
					htParams : {
						// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
						bUseToolbar : true,
						// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
						bUseVerticalResizer : true,
						// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
						bUseModeChanger : true,
					}
				});

				$("#postRegBtn").on(
						"click",
						function() {

							if ($("#subject").val().length == 0) {
								alert("제목을 입력하세요");
								return false;
							}

							// 				if($("#smarteditor").val().length == 0){
							// 					alert("내용을 입력하세요");
							// 					return false;
							// 				}

							var subject = $("#subject").val();
							var subject1 = $("#subject").val().replace(/</gi,
									"&lt;");
							var subject2 = subject1.replace(/>/gi, "&gt;");

							// 				var content = $("#smarteditor").val();
							// 				var content1 = $("#smarteditor").val().replace(/</gi,"&lt;");
							// 				var content2 = content1.replace(/>/gi,"&gt;");

							$("#subject").val(subject2);
							// 				$("#smarteditor").val(content2);

							if (confirm("저장하시겠습니까?")) {
								// id가 smarteditor인 textarea에 에디터에서 대입
								oEditors.getById["smarteditor"].exec(
										"UPDATE_CONTENTS_FIELD", []);

								// 이부분에 에디터 validation 검증
								if (validation()) {

									$("#frm").submit();
								}
							}
						})

				//제목 글자 수 제한
				$('#subject').keyup(
						function() {
							if ($(this).val().length > $(this)
									.attr('maxlength')) {
								alert('입력할 수 있는 제목 범위를 넘었습니다');
								$(this).val(
										$(this).val().substr(0,
												$(this).attr('maxlength')));
							}
						});

			});

	// 필수값 Check
	function validation() {
		var contents = $.trim(oEditors[0].getContents());
		if (contents === '&nbsp;' || contents === '') { // 기본적으로 아무것도 입력하지 않아도 <p>&nbsp;</p> 값이 입력되어 있음. 
			alert("내용을 입력하세요.");
			oEditors.getById['smarteditor'].exec('FOCUS');
			return false;
		}
		return true;
	}
</script>

<section class="contents">
	<h2 class="contentTitle">글쓰기</h2>
	<form action="/postAdd" method="post" id="frm" role="form" enctype="multipart/form-data">
		<input type="hidden" name="boardnum" id="boardnum" value="${boardnum }">
		<div class="boardWriteWrap">
			<div class="title">
				<input type="text" id="subject" name="subject" placeholder="제목을 입력해 주세요." value="${param.subject }" maxlength="30">
			</div>

			<div class="boardContent">
				<input type="hidden" class="form-control" id="user_email" name="user_email" placeholder="작성자" value=${USER_INFO.user_email }>
				<textarea name="smarteditor" id="smarteditor" rows="10" cols="100" style="width:100%; height: 412px;"></textarea>
			</div>
	
			<div>
				<button id="postRegBtn" type="button" class="">등록하기</button>
			</div>
		</div>
	</form>
</section>