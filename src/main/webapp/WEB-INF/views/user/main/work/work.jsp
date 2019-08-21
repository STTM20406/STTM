
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- date picker resource-->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

<!-- Sortable resource-->
<script src="/js/Sortable.js"></script>

<script>
	$(document).ready(function() {
		
		var projectAuth= "${PROJECT_INFO.prj_auth}";
		var projectMemLevel ="${userInfo.prj_mem_lv}";
		
		//업무리스트, 업무 이동 function
		sortable();
		
		$(".workList_set ").hide();
		
		//업무리스트 설정 아이콘클릭시 삭제 버튼 보여주기
		$("#workListBox").on("click", ".workList_set_i", function() {
			$(this).next(".workList_set").fadeIn(300);
		});
		
		//업무리스트 삭제 버튼에서 마우스가 떠났을때 삭제버튼 사라지게 하기
		$("#workListBox").on("mouseleave", ".workList_set", function(){
			$(this).fadeOut(300);
		});
		
		//업무리스트 추가 버튼 클릭시
		$(".workListAdd").on("click", "#btnWorkList", function() {
			var html = "";
			html += "<input type='text' value='' id='workListName'>";
			

			//workListAdd input의 개수를 파악해 텍스트 창 한개만 만들기
			var len = $(".workListAdd input").length;
			if (len < 2) {
				$(".workListAdd").append(html);
			}

			//현재 버튼을 취소 버튼으로 변경하고 id도 변경
			$(this).attr("id", "btnCancelWorklist");
			$(this).attr("value", "취소");
			
		});

		//업무리스트 취소 버튼 클릭시
		$(".workListAdd").on("click", "#btnCancelWorklist", function() {
			$(this).attr("id", "btnWorkList");
			$(this).attr("value", "업무리스트 추가");
			//업무리스트 이름 입력하는 창 없애기
			$("#workListName").remove();
		});

		//업무리스트 이름 입력 후 엔터 또는 다른곳 클릭시 업무리스트 추가하는 ajax실행 
		$(".workListAdd").on("keydown change", "#workListName", function(key) {
			if (key.keyCode == 13) {
				var workListNm = $(this).val();
				workListAddAjax(workListNm);
				$(this).val("").focus();
			}
		});
		
		//업무 라벨 색상 변경 이벤트 핸들러
// 		$("input:radio[name='wrk_color_cd']").on("change", function(){
// 			console.log(this);
// 			/*if($(this).prop("checked", false)){
// 				alert("체크안됨");
// 			}
// 			else{
// 				alert("체크됨");
// 			}*/		
// 		});

		
		//업무리스트 추가
		function workListAddAjax(workListNm) {
			$.ajax({
				url : "/work/workListAddAjax",
				method : "post",
				data : "wrk_lst_nm=" + workListNm,
				success : function(data) {
					var html = "";
					data.workList.forEach(function(workList, index) {
						html +="<div class='workList' id='"+ workList.wrk_lst_id + "'><span class='handle'>+++</span><div class='workList_hd'><dl>"
						html +="<dt><input type='text' value='"+ workList.wrk_lst_nm+"' id='"+workList.wrk_lst_id+"' class='wrkListName'></dt><dd>"
						html +="<input type='button' class='workList_add_i' value='새업무 추가'>"
						html +="<a href='javascript:;' class='workList_set_i'>업무리스트 설정</a>"
						html +="<div class='workList_set'><input type='button' id='btnWorkListDel_"+workList.wrk_lst_id+"' value='업무리스트 삭제'></div>"
						html +="</dd></dl><ul><li>"
						html +="<p>진행중 업무 <span>4</span></p>";
						html +="<p><a href='javascript:' class='btnComplete' id='"+workList.wrk_lst_id+"'>완료된업무보기</a><span>2</span></p></li>";
						html +="<li class='graph'></li></ul><div class='workCreateBox'>";
						html +="<textarea name='wrk_nm' id='wrk_nm' placeholder='업무 이름을 입력해 주세요. 업무 이름은 70자 이내로 입력해 주세요'></textarea>";
						html +="<div class='workCreatebtnBox'>";
						html +="<input type='button' value='취소' id='wrkCreateCancelBtn'>";
						html +="<input type='button' value='만들기' id='wrkCreateBtn' class='wrkCreateBtn' name='"+workList.wrk_lst_id+"' disabled='disabled'>";
						html +="</div></div>";
						html +="</div>";
						html +="<div class='workWrap'><div class='working list n1' id='"+workList.wrk_lst_id+"'>"
							data.works.forEach(function(work, index2) {
		 						if(work.wrk_lst_id == workList.wrk_lst_id && work.wrk_cmp_fl == 'N'){
		 							html +=	"<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work.wrk_id+"' class='workListItem'>";
		 							html +=	"<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl'>";
									html +=	"<h2 class='wrk_title'><span>"+work.wrk_grade+"</span>"+work.wrk_nm+"</h2>";
									html +=	"<ul>";
									html +=	"<li class='wrk_data'>"+work.wrkStartDtStr+" ~ "+work.wrkEndDtStr+"</li>";
									html +=	"<li><span>코멘트 개수</span><span>업무 파일 개수</span></li>";
									html +=	"</ul>";
									html +=	"<div class='wrk_mem_flw'><dl class='wrk_mem'>";
									html +=	"<dt>업무 배정 멤버</dt>";
									html +=	"<dd>";
									html +=	"<p>또굥이</p>";
									html +=	"</dd></dl>";
									html +=	"<dl class='wrk_mem'>";
									html +=	"<dt>업무 팔로워</dt>";
									html +=	"<dd>";
									html +=	"<p>유돌이</p>";
									html +=	"</dd>";
									html +=	"</dl>";
									html +=	"</div></div>";
		 						}
							});
						html +="</div>";
						html +="<div class='complete_"+workList.wrk_lst_id+" list n1' id='"+workList.wrk_lst_id+"'>"
							data.works.forEach(function(work1, index3) {
		 						if(work1.wrk_lst_id == workList.wrk_lst_id && work1.wrk_cmp_fl == 'Y'){
									html +=	"<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work1.wrk_id+"' class='workListItem'>";
		 							html +=	"<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl' checked>";
									html +=	"<h2 class='wrk_title'><span>"+work1.wrk_grade+"</span>"+work1.wrk_nm+"</h2>";
									html +=	"<ul>";
									html +=	"<li>"+work1.wrkStartDtStr+" ~ "+work1.wrkEndDtStr+"</li>";
									html +=	"<li><span>코멘트 개수</span><span>업무 파일 개수</span></li>";
									html +=	"</ul>";
									html +=	"<div class='wrk_mem_flw'><dl class='wrk_mem'>";
									html +=	"<dt>업무 배정 멤버</dt>";
									html +=	"<dd>";
									html +=	"<p>또굥이</p>";
									html +=	"</dd></dl>";
									html +=	"<dl class='wrk_mem'>";
									html +=	"<dt>업무 팔로워</dt>";
									html +=	"<dd>";
									html +=	"<p>유돌이</p>";
									html +=	"</dd>";
									html +=	"</dl>";
									html +=	"</div></div>";
		 						}
		 					});
						html +="</div></div>";
						html +="</div>";
					
						$("#workListBox").html(html);
						sortable();
					});
				}
			});
		}

		//업무리스트 삭제 버튼 클릭시
		$("#workListBox").on("click", ".workList_set input[type=button]", function() {
			var idText = $(this).attr("id").split("_");
			var workListID = idText[1];
			
			workListDelAjax(workListID);
		});
		
		//업무리스트 삭제
		function workListDelAjax(workListID) {
			$.ajax({
				url : "/work/workListDelAjax",
				method : "post",
				data : "wrk_lst_id=" + workListID,
				success : function(data) {
					if(data.noResult == ""){
		 				$(".ctxt").text("해당 업무리스트에 업무가 존재 합니다.");
		 				layer_popup("#layer2");
						return false;
					}else{
						var html = "";
						data.workList.forEach(function(workList, index) {
							html +="<div class='workList' id='"+ workList.wrk_lst_id + "'><span class='handle'>+++</span><div class='workList_hd'><dl>"
							html +="<dt><input type='text' value='"+ workList.wrk_lst_nm+"' id='"+workList.wrk_lst_id+"' class='wrkListName'></dt><dd>"
							html +="<input type='button' class='workList_add_i' value='새업무 추가'>"
							html +="<a href='javascript:;' class='workList_set_i'>업무리스트 설정</a>"
							html +="<div class='workList_set'><input type='button' id='btnWorkListDel_"+workList.wrk_lst_id+"' value='업무리스트 삭제'></div>"
							html +="</dd></dl><ul><li>"
							html +="<p>진행중 업무 <span>4</span></p>";
							html +="<p><a href='javascript:' class='btnComplete' id='"+workList.wrk_lst_id+"'>완료된업무보기</a><span>2</span></p></li>";
							html +="<li class='graph'></li></ul><div class='workCreateBox'>";
							html +="<textarea name='wrk_nm' id='wrk_nm' placeholder='업무 이름을 입력해 주세요. 업무 이름은 70자 이내로 입력해 주세요'></textarea>";
							html +="<div class='workCreatebtnBox'>";
							html +="<input type='button' value='취소' id='wrkCreateCancelBtn'>";
							html +="<input type='button' value='만들기' id='wrkCreateBtn' class='wrkCreateBtn' name='"+workList.wrk_lst_id+"' disabled='disabled'>";
							html +="</div></div>";
							html +="</div>";
							html +="<div class='workWrap'><div class='working list n1' id='"+workList.wrk_lst_id+"'>"
								data.works.forEach(function(work, index2) {
			 						if(work.wrk_lst_id == workList.wrk_lst_id && work.wrk_cmp_fl == 'N'){
			 							html +=	"<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work.wrk_id+"' class='workListItem'>";
			 							html +=	"<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl'>";
										html +=	"<h2 class='wrk_title'><span>"+work.wrk_grade+"</span>"+work.wrk_nm+"</h2>";
										html +=	"<ul>";
										html +=	"<li class='wrk_data'>"+work.wrkStartDtStr+" ~ "+work.wrkEndDtStr+"</li>";
										html +=	"<li><span>코멘트 개수</span><span>업무 파일 개수</span></li>";
										html +=	"</ul>";
										html +=	"<div class='wrk_mem_flw'><dl class='wrk_mem'>";
										html +=	"<dt>업무 배정 멤버</dt>";
										html +=	"<dd>";
										html +=	"<p>또굥이</p>";
										html +=	"</dd></dl>";
										html +=	"<dl class='wrk_mem'>";
										html +=	"<dt>업무 팔로워</dt>";
										html +=	"<dd>";
										html +=	"<p>유돌이</p>";
										html +=	"</dd>";
										html +=	"</dl>";
										html +=	"</div></div>";
			 						}
								});
							html +="</div>";
							html +="<div class='complete_"+workList.wrk_lst_id+" list n1' id='"+workList.wrk_lst_id+"'>"
								data.works.forEach(function(work1, index3) {
			 						if(work1.wrk_lst_id == workList.wrk_lst_id && work1.wrk_cmp_fl == 'Y'){
										html +=	"<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work1.wrk_id+"' class='workListItem'>";
			 							html +=	"<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl' checked>";
										html +=	"<h2 class='wrk_title'><span>"+work1.wrk_grade+"</span>"+work1.wrk_nm+"</h2>";
										html +=	"<ul>";
										html +=	"<li class='wrk_data'>"+work1.wrkStartDtStr+" ~ "+work1.wrkEndDtStr+"</li>";
										html +=	"<li><span>코멘트 개수</span><span>업무 파일 개수</span></li>";
										html +=	"</ul>";
										html +=	"<div class='wrk_mem_flw'><dl class='wrk_mem'>";
										html +=	"<dt>업무 배정 멤버</dt>";
										html +=	"<dd>";
										html +=	"<p>또굥이</p>";
										html +=	"</dd></dl>";
										html +=	"<dl class='wrk_mem'>";
										html +=	"<dt>업무 팔로워</dt>";
										html +=	"<dd>";
										html +=	"<p>유돌이</p>";
										html +=	"</dd>";
										html +=	"</dl>";
										html +=	"</div></div>";
			 						}
			 					});
							html +="</div></div>";
							html +="</div>";
						
							$("#workListBox").html(html);
							sortable();
						});
					}
					
				}
			});
		}
		
		//업무리스트 이름 수정
		$("#workListBox").on("change", ".wrkListName", function(){
			var wrkListName = $(this).val();
			var wrkListId = $(this).attr("id");
			
			if(!wrkListName){
				$(".ctxt").text("이름을 입력해 주세요.");
 				layer_popup("#layer2");
				return false;
			}
			
			workListNameUpdateAjax(wrkListName, wrkListId);
		});
		
		//업무리스트 이름 수정하는 ajax
		function workListNameUpdateAjax(wrkListName, wrkListId){
			$.ajax({
				url : "/work/workListNameUpdateAjax",
				method : "post",
				data : "wrk_lst_nm=" + wrkListName + "&wrk_lst_id=" + wrkListId,
				success : function(data) {
					$(".ctxt").text("이름이 변경 되었습니다.");
	 				layer_popup("#layer2");
					return false;
				}
			});
		}
		
		//업무 추가 버튼 클릭시
		$("#workListBox").on("click", ".workList_add_i", function(){
			var workCreateArea = $(this).parent().parent().next().next();
			$(".workCreateBox").fadeOut(0);
			workCreateArea.fadeIn(300);
		});
		
		//업무 생성 textarea에 텍스트를 썼을 때
		$("#workListBox").on("keydown", "#wrk_nm", function(){
			var workName = $(this).val();
			if(workName.length > 1){
				$(this).next().children("#wrkCreateBtn").prop("disabled", false);
			}else{
				$(this).next().children("#wrkCreateBtn").prop("disabled", true);
			}
		});
		
		
		//업무 만들기 버튼 클릭 했을 때
		$("#workListBox").on("click", "#wrkCreateBtn", function(){
			var workName = $(this).parent().prev().val();
			var workLstID = $(this).attr("name");
			
			wrkCreateAjax(workLstID, workName);
		});
		
		function wrkCreateAjax(workLstID, workName){
			$.ajax({
				url:"/work/wrkCreateAjax",
				method:"post",
				data : "wrk_lst_id=" + workLstID + "&wrk_nm=" + workName,
				success:function(data){
					var html = "";
					data.workList.forEach(function(workList, index) {
						html +="<div class='workList' id='"+ workList.wrk_lst_id + "'><span class='handle'>+++</span><div class='workList_hd'><dl>"
						html +="<dt><input type='text' value='"+ workList.wrk_lst_nm+"' id='"+workList.wrk_lst_id+"' class='wrkListName'></dt><dd>"
						html +="<input type='button' class='workList_add_i' value='새업무 추가'>"
						html +="<a href='javascript:;' class='workList_set_i'>업무리스트 설정</a>"
						html +="<div class='workList_set'><input type='button' id='btnWorkListDel_"+workList.wrk_lst_id+"' value='업무리스트 삭제'></div>"
						html +="</dd></dl><ul><li>"
						html +="<p>진행중 업무 <span>4</span></p>";
						html +="<p><a href='javascript:' class='btnComplete' id='"+workList.wrk_lst_id+"'>완료된업무보기</a><span>2</span></p></li>";
						html +="<li class='graph'></li></ul><div class='workCreateBox'>";
						html +="<textarea name='wrk_nm' id='wrk_nm' placeholder='업무 이름을 입력해 주세요. 업무 이름은 70자 이내로 입력해 주세요'></textarea>";
						html +="<div class='workCreatebtnBox'>";
						html +="<input type='button' value='취소' id='wrkCreateCancelBtn'>";
						html +="<input type='button' value='만들기' id='wrkCreateBtn' class='wrkCreateBtn' name='"+workList.wrk_lst_id+"' disabled='disabled'>";
						html +="</div></div>";
						html +="</div>";
						html +="<div class='workWrap'><div class='working list n1' id='"+workList.wrk_lst_id+"'>"
							data.works.forEach(function(work, index2) {
		 						if(work.wrk_lst_id == workList.wrk_lst_id && work.wrk_cmp_fl == 'N'){
		 							html +=	"<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work.wrk_id+"' class='workListItem'>";
		 							html +=	"<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl'>";
									html +=	"<h2 class='wrk_title'><span>"+work.wrk_grade+"</span>"+work.wrk_nm+"</h2>";
									html +=	"<ul>";
									html +=	"<li class='wrk_data'>"+work.wrkStartDtStr+" ~ "+work.wrkEndDtStr+"</li>";
									html +=	"<li><span>코멘트 개수</span><span>업무 파일 개수</span></li>";
									html +=	"</ul>";
									html +=	"<div class='wrk_mem_flw'><dl class='wrk_mem'>";
									html +=	"<dt>업무 배정 멤버</dt>";
									html +=	"<dd>";
									html +=	"<p>또굥이</p>";
									html +=	"</dd></dl>";
									html +=	"<dl class='wrk_mem'>";
									html +=	"<dt>업무 팔로워</dt>";
									html +=	"<dd>";
									html +=	"<p>유돌이</p>";
									html +=	"</dd>";
									html +=	"</dl>";
									html +=	"</div></div>";
		 						}
							});
						html +="</div>";
						html +="<div class='complete_"+workList.wrk_lst_id+" list n1' id='"+workList.wrk_lst_id+"'>"
							data.works.forEach(function(work1, index3) {
		 						if(work1.wrk_lst_id == workList.wrk_lst_id && work1.wrk_cmp_fl == 'Y'){
									html +=	"<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work1.wrk_id+"' class='workListItem'>";
		 							html +=	"<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl' checked>";
									html +=	"<h2 class='wrk_title'><span>"+work1.wrk_grade+"</span>"+work1.wrk_nm+"</h2>";
									html +=	"<ul>";
									html +=	"<li class='wrk_data'>"+work1.wrkStartDtStr+" ~ "+work1.wrkEndDtStr+"</li>";
									html +=	"<li><span>코멘트 개수</span><span>업무 파일 개수</span></li>";
									html +=	"</ul>";
									html +=	"<div class='wrk_mem_flw'><dl class='wrk_mem'>";
									html +=	"<dt>업무 배정 멤버</dt>";
									html +=	"<dd>";
									html +=	"<p>또굥이</p>";
									html +=	"</dd></dl>";
									html +=	"<dl class='wrk_mem'>";
									html +=	"<dt>업무 팔로워</dt>";
									html +=	"<dd>";
									html +=	"<p>유돌이</p>";
									html +=	"</dd>";
									html +=	"</dl>";
									html +=	"</div></div>";
		 						}
		 					});
						html +="</div></div>";
						html +="</div>";
					
						$("#workListBox").html(html);
						sortable();
					});
				}
			});
		}
		
		//업무 만들기 취소 버튼 클릭했을 때
		$("#workListBox").on("click", "#wrkCreateCancelBtn", function(){
			$(this).parent().parent().fadeOut(300);
		});
		
		//업무를 드래그 이동 했을 때
		$("#workListBox").on("draggable", ".list div", function(event, drag){
			alert("test");
		})
		
		
		//업무 리스트, 업무 이동 script
		function sortable(){
			new Sortable(document.getElementById('workListBox'), { handle: '.handle',  animation: 150 });
			var elements = document.getElementsByClassName('list');
			for (var i = 0; i < elements.length; i++) {
				
				//프로젝트 멤버 레벨이 1이고 프로젝트 권한이 제한 이거나 프로젝트 멤버 레벨이 1이고 프로젝트 권한이 통제 일 때 업무 이동 안됨.
				if(projectMemLevel == 'LV1' &&  projectAuth == 'ASC02' || projectMemLevel == 'LV1' && projectAuth == 'ASC03'){
					new Sortable(elements[i], {
						disabled: true
					});
				}else{
					new Sortable(elements[i], {
						group: 'shared',
						invertSwap: true,
						animation: 150,
						
						//드래그가 끝났을 때
						onEnd: function (evt) {
							var itemEl = evt.item;  // dragged HTMLElement
							
							//이동하려는 업무의 아이디
							var wrkID = itemEl.getAttribute( 'data-wrkid' );
							//옮기려는 업무리스트 아이디
							var wrkListToID = evt.to.getAttribute( 'id' );
							
							wrkTransAjax(wrkID, wrkListToID);
						}
					});
				}
			}
		}
		
		//업무 리스트
		function wrkTransAjax(wrkID, wrkListToID){
			$.ajax({
				url : "/work/wrkTransAjax",
				method : "post",
				data : "wrk_id=" + wrkID + "&wrk_lst_id=" + wrkListToID,
				success : function(data) {
					//이동시 바로 이동되기 때문에 별다른 태그 추가 없음
				}
			});
		};
		
		
		//업무 클릭시 업무 설정 레이어 열기
		$("#workListBox").on("click", ".workListItem h2", function(){
			$("#propertyWorkSet").animate({right:'0'}, 500);
			
			//업무 아이디를 변수에 담음
			var wrk_id = $(this).parent().attr("data-wrkid");
			$("#wps_wrk_id").val(wrk_id);
			
			propertyWorkSetAjax(wrk_id);
		});
		
		//프로젝트 닫기 버튼을 클릭했을 때
		$(".btnSetClose").on("click", function(){
			$("#propertyWorkSet").animate({right:'-700px'}, 500);
		});
		
		function propertyWorkSetAjax(wrk_id){
			$.ajax({
				url:"/work/propertyWorkSetAjax",
				method:"post",
				data:"wrk_id=" + wrk_id,
				success:function(data){
					$("#wps_id").val(data.workVo.wrk_id);
					$("#wps_nm").val(data.workVo.wrk_nm);
					$("#wps_write_nm").text(data.workVo.user_nm);
					$("#wps_write_date").text(data.workVo.wrkDtStr);
					$("#wps_start_date").val(data.workVo.wrkStartDtStr + " ~ " + data.workVo.wrkEndDtStr);
					$("#wrk_gd").val(data.workVo.wrk_grade);
					$(".wrk_color").removeClass("colorSelect");
					$("#"+data.workVo.wrk_color_cd).prev().addClass("colorSelect");
					$("#"+data.workVo.wrk_color_cd).prop("checked", true);
				}
			});
		}
		
		//업무 클릭 했을 때 처음 보여주는 탭은 설정 탭
		$(".propertySetWrap div:nth-child(3)").show();

		//탭 버튼 클릭시 div 변경
		$(".workTab").on("click", "ul.tabs li", function () {
		    	$(".workTab ul.tabs li").removeClass("active").css("color", "#333");
		    	$(this).addClass("active").css("color", "darkred");
		    	$(".tab_content").hide()
		    	var activeTab = $(this).attr("data-tab");
		    	$("#" + activeTab).fadeIn()
		});
		
		//완료된업무보기 클릭 했을 때
		$("#workListBox").on("click", ".btnComplete", function(){
			var id = $(this).attr("id");
			$(".complete_"+id).fadeIn(300);
			
			var text = $(this).text();
			$(this).text(text.replace('완료된업무보기', '완료된 업무 숨기기'));
			$(this).attr("class", "btnCompleteHide");
		});
		
		//완료된업무숨기기 클릭시
		$("#workListBox").on("click", ".btnCompleteHide", function(){
			var text = $(this).text();
			$(this).text(text.replace('완료된 업무 숨기기', '완료된업무보기'));
			var id = $(this).attr("id");
			$(".complete_"+id).fadeOut(300);
			$(this).attr("class", "btnComplete");
		});
		
		
		//업무 완료 체크 했을 떄
		$("#workListBox").on("click", "#wrk_cmp_fl", function(){
			var wrkID = $(this).parent().attr("data-wrkid");
			var wrkCMP = "";
			if ($(this).prop('checked')){ 
				wrkCMP = "Y";
				completeCheckAjax(wrkID, wrkCMP);
			}else{ 
				wrkCMP = "N";
				completeCheckAjax(wrkID, wrkCMP);
			}
		});
		
		
		function completeCheckAjax(wrkID, wrkCMP){
			alert("test");
			$.ajax({
				url:"/work/completeCheckAjax",
				method:"post",
				data:"wrk_id=" + wrkID + "&wrk_cmp_fl=" + wrkCMP,
				success:function(data){
					console.log(data);
					var html = "";
					data.workList.forEach(function(workList, index) {
						html +="<div class='workList' id='"+ workList.wrk_lst_id + "'><span class='handle'>+++</span><div class='workList_hd'><dl>"
						html +="<dt><input type='text' value='"+ workList.wrk_lst_nm+"' id='"+workList.wrk_lst_id+"' class='wrkListName'></dt><dd>"
						html +="<input type='button' class='workList_add_i' value='새업무 추가'>"
						html +="<a href='javascript:;' class='workList_set_i'>업무리스트 설정</a>"
						html +="<div class='workList_set'><input type='button' id='btnWorkListDel_"+workList.wrk_lst_id+"' value='업무리스트 삭제'></div>"
						html +="</dd></dl><ul><li>"
						html +="<p>진행중 업무 <span>4</span></p>";
						html +="<p><a href='javascript:' class='btnComplete' id='"+workList.wrk_lst_id+"'>완료된업무보기</a><span>2</span></p></li>";
						html +="<li class='graph'></li></ul><div class='workCreateBox'>";
						html +="<textarea name='wrk_nm' id='wrk_nm' placeholder='업무 이름을 입력해 주세요. 업무 이름은 70자 이내로 입력해 주세요'></textarea>";
						html +="<div class='workCreatebtnBox'>";
						html +="<input type='button' value='취소' id='wrkCreateCancelBtn'>";
						html +="<input type='button' value='만들기' id='wrkCreateBtn' class='wrkCreateBtn' name='"+workList.wrk_lst_id+"' disabled='disabled'>";
						html +="</div></div>";
						html +="</div>";
						html +="<div class='workWrap'><div class='working list n1' id='"+workList.wrk_lst_id+"'>"
							data.works.forEach(function(work, index2) {
		 						if(work.wrk_lst_id == workList.wrk_lst_id && work.wrk_cmp_fl == 'N'){
		 							html +=	"<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work.wrk_id+"' class='workListItem'>";
		 							html +=	"<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl'>";
									html +=	"<h2 class='wrk_title'><span>"+work.wrk_grade+"</span>"+work.wrk_nm+"</h2>";
									html +=	"<ul>";
									html +=	"<li class='wrk_data'>"+work.wrkStartDtStr+" ~ "+work.wrkEndDtStr+"</li>";
									html +=	"<li><span>코멘트 개수</span><span>업무 파일 개수</span></li>";
									html +=	"</ul>";
									html +=	"<div class='wrk_mem_flw'><dl class='wrk_mem'>";
									html +=	"<dt>업무 배정 멤버</dt>";
									html +=	"<dd>";
									html +=	"<p>또굥이</p>";
									html +=	"</dd></dl>";
									html +=	"<dl class='wrk_mem'>";
									html +=	"<dt>업무 팔로워</dt>";
									html +=	"<dd>";
									html +=	"<p>유돌이</p>";
									html +=	"</dd>";
									html +=	"</dl>";
									html +=	"</div></div>";
		 						}
							});
						html +="</div>";
						html +="<div class='complete_"+workList.wrk_lst_id+" list n1' id='"+workList.wrk_lst_id+"'>"
							data.works.forEach(function(work1, index3) {
		 						if(work1.wrk_lst_id == workList.wrk_lst_id && work1.wrk_cmp_fl == 'Y'){
									html +=	"<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work1.wrk_id+"' class='workListItem'>";
		 							html +=	"<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl' checked>";
									html +=	"<h2 class='wrk_title'><span>"+work1.wrk_grade+"</span>"+work1.wrk_nm+"</h2>";
									html +=	"<ul>";
									html +=	"<li class='wrk_data'>"+work1.wrkStartDtStr+" ~ "+work1.wrkEndDtStr+"</li>";
									html +=	"<li><span>코멘트 개수</span><span>업무 파일 개수</span></li>";
									html +=	"</ul>";
									html +=	"<div class='wrk_mem_flw'><dl class='wrk_mem'>";
									html +=	"<dt>업무 배정 멤버</dt>";
									html +=	"<dd>";
									html +=	"<p>또굥이</p>";
									html +=	"</dd></dl>";
									html +=	"<dl class='wrk_mem'>";
									html +=	"<dt>업무 팔로워</dt>";
									html +=	"<dd>";
									html +=	"<p>유돌이</p>";
									html +=	"</dd>";
									html +=	"</dl>";
									html +=	"</div></div>";
		 						}
		 					});
						html +="</div></div>";
						html +="</div>";
					
						$("#workListBox").html(html);
						sortable();
					});
				}
			});
		}
		
		var wrk_color = "";
		
		
		/* 여기서부터 업무 셋팅 업데이트를 위한 이벤트 핸들러 입니다. */
		$("#propertyWorkSet input, select").on("propertychange keyup change click", function(){
			
			//프로젝트 셋팅 값 가져오기
			var id = $("#wps_id").val();
			var name = $("#wps_nm").val();
			var work_date = $("#wps_start_date").val();
			var res_date = $("#wps_res_date").val();
			var wrk_gd = $("#wrk_gd").val();
				
			var workSplit = work_date.split(" to ");
			var resSplit = res_date.split(" to ");
			
			var work_start_dt = workSplit[0];
			var work_end_dt = workSplit[1];
			
			if(!name){
				$(".ctxt").text("업무 이름을 작성해 주세요.");
 				layer_popup("#layer2");
				return false;
			}
			
			if(typeof work_start_dt == "undefined" || typeof work_end_dt == "undefined"){
				work_start_dt = "";
				work_end_dt = "";
			}
			
			var projectWorkSet = {
							  id : id
						  	, name : name
						  	, work_start_dt : work_start_dt
						 	, work_end_dt : work_end_dt
						  	, wrk_gd : wrk_gd
			}
			
			propertyWorkSetItemAjax(projectWorkSet);
		});
		
		//업무 설정 업데이트 ajax
		function propertyWorkSetItemAjax(projectWorkSet){
			$.ajax({
				url:"/work/propertyWorkSetItemAjax",
				method:"post",
				contentType:"application/x-www-form-urlencoded; charset=UTF-8",
				data:"wrk_id=" + projectWorkSet.id +
					"&wrk_nm=" + projectWorkSet.name +
					"&wrk_grade=" + projectWorkSet.wrk_gd +
					"&wrk_start_dt=" + projectWorkSet.work_start_dt +
					"&wrk_end_dt=" + projectWorkSet.work_end_dt,
				success:function(data){
					$(".workListItem").each(function() {
						var wrkID = $(this).attr("data-wrkid");
						if(wrkID == projectWorkSet.id){
							$(this).find(".wrk_title").html("<span>"+data.data.wrk_grade+"</span>" + data.data.wrk_nm);
							$(this).find(".wrk_data").text(data.data.prj_st);
						}
					});
				}
			});
		}
		//레이블 색상 선택 (라디오버튼 선택 / 선택해제)
		
		var beforeChecked = -1;
		
      		$(".lableColor").on("click", "input[type=radio]", function(e) {
      			var id = $("#wps_id").val();
         		var index = $(this).prev().index("label");
         		$(this).prev().removeClass("colorSelect");
         		
         		if(beforeChecked == index) {
	         		beforeChecked = -1;
	         		$(this).prop("checked", false);
	         		wrk_color = $("input:radio[name='wrk_color_cd']:checked").val();
         		}else{
	         		beforeChecked = index;
	         		wrk_color = $("input:radio[name='wrk_color_cd']:checked").val();
	         		$(".wrk_color").removeClass("colorSelect");
	         		$(this).prev().addClass("colorSelect");
         		}
         		
         		if(typeof wrk_color == "undefined"){
    				wrk_color = "";
    			}else{
    				wrk_color = $("input:radio[name='wrk_color_cd']:checked").val();
    			}
         		
         		propertyWorkLableColorAjax(id, wrk_color);
	      	});
      		
      		//업무 라벨 색상 변경
    		function propertyWorkLableColorAjax(id, wrk_color){
    			$.ajax({
    				url:"/work/propertyWorkLableColorAjax",
    				method:"post",
    				contentType:"application/x-www-form-urlencoded; charset=UTF-8",
    				data:"wrk_id=" + id + "&wrk_color_cd=" + wrk_color,
    				success:function(data){
    				}
    			});
    		}
		
		//날짜 선택
		$(".flatpickr").flatpickr({
		    mode: "range",
		    minDate: "today",
		    enableTime: true,
		    minTime: "09:00"
		});
		
		
		// file, link 등록 부분 구현중
		
		$('#workLink').hide(0);
		
		$(".workTab").on("click", "#FL", function(){
			var wrk_id = $('#wps_wrk_id').val();
			workFilePagination(1, 10, wrk_id);
		})
	
		$(".sub_menu").on("click", "#fileList",function(){
			var wrk_id = $('#wps_wrk_id').val();
			$('#workLink').fadeOut(0);
			$('#workFile').fadeIn(0);
			workFilePagination(1, 10, wrk_id);
		})
		
		$(".sub_menu").on("click", "#linkList",function(){
			var wrk_id = $('#wps_wrk_id').val();
			$('#workFile').fadeOut(0);
			$('#workLink').fadeIn(0);
			workLinkPagination(1, 10,wrk_id);
		})
		
		
		
		$("#workLink").on('click','#uploadLink', function(){
			alert("link가즈아!!");
			var link = $('.link').val();
			console.log(link);
			var locker = $("#locker input[type=radio]:checked").val();
			console.log(locker);
			var wrk_id = $('#wps_wrk_id').val();
			console.log(wrk_id);
			
			$('#box').val(locker);
			$('#work').val(wrk_id);
			
			$.ajax({
	 		    url: "/workLinkUpload",
	 		    type: "POST",
	 		    data: "link=" + link + "&locker=" + locker + "&wrk_id="+ wrk_id,
	 		    contentType:"application/x-www-form-urlencoded; charset=UTF-8",
	 		    success: function(data){
	 		    	workLinkPagination(1, 10, wrk_id);
	 		    },
	 		    error: function(e){
	 		        alert(e.reponseText);
	 		    }
	 		});
			
			
		})
		
		
		
		$("#workFile").on('click','#uploadFile', function(){

			var form = document.getElementById("frm");
			console.log(form);
			form.method = "POST";
			form.enctype = "multipart/form-data";
// 			formData.append("uploadfile",$("input[name=uploadfile]")[0].files[0]);
			
			var locker = $("#locker input[type=radio]:checked").val();
			var wrk_id = $('#wps_wrk_id').val();
			$('#box').val(locker);
			$('#work').val(wrk_id);
			var formData  = new FormData(form);
			console.log(formData);
			console.log($(formData).serialize());
			alert(formData);
			$.ajax({
	 		    url: "/workFileUpload",
	 		    type: "POST",
	 		    data: formData,
				cache : false,		
	 		    contentType: false,
	 		    processData: false,
	 		    success: function(data){
	 		    	workFilePagination(1, 10, wrk_id);
	 		    },
	 		    error: function(e){
	 		        alert(e.reponseText);
	 		    }
	 		});
		});
		
		
		//업무 멤버 추가하기 버튼 클릭시 해당 프로젝트 멤버 가져오기
		$(".wrk_add_mem").fadeOut(0); //멤버리스트 layer 숨기기
		$("#wps_mem_set").on("click", function(){
			$(".wrk_add_mem").fadeIn(300);
			workMemListAjax();
		});
		
		//업무 멤버 가져오는 ajax
		function workMemListAjax(){
			$.ajax({
				url:"/work/workMemListAjax",
				method:"post",
				success:function(data){
					var html = "";
					data.data.forEach(function(item, index){
						//html 생성
						html += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
					});	
					$(".wrk_mem_item").html(html);
				}
			});
		}
		
		//업무 멤버 클릭 했을 때
		$(".wrk_mem_item").on("click", "li", function(){
			var mem_add_email = $(this).attr("id");
			workMemAddAjax(mem_add_email);
		});
		
		//배정된 멤버로 선택한 멤버 추가
		function workMemAddAjax(id, mem_add_email){
			$.ajax({
				url:"/work/workMemAddAjax",
				method:"post",
				data: "user_email=" + mem_add_email,
				success:function(data){
					console.log(data);
// 					var html = "";
// 					data.data.forEach(function(item, index){
// 						html += "<li id='"+ item.user_email +"_"+item.prj_id+"_"+item.prj_mem_lv+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
// 					});	
					
// 					$(".wrk_add_box").html(html);
				}
			});
		}
		
	});
	
	function workFilePagination(page, pageSize, wrk_id) {
		$.ajax({
			url : "/workFilePagination",
			method : "post",
			data : "page=" + page + "&pageSize=" + pageSize + "&wrk_id="+ wrk_id,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";

				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>파일명</th>";
				hhtml += "<th>공유한 멤버 이름</th>";
				hhtml += "<th>등록한 날짜</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";

				data.workFileList.forEach(function(file, index) {
					//html생성
					html += "<tr id='filetr'>";
					html += "<td><a href='#'>" + file.original_file_nm
							+ "</a></td>";
					html += "<td>" + file.user_nm + "</td>";
					html += "<td>" + file.prjStartDtStr + "</td>";
					html += "<td><a href='javascript:workDelFile("
							+ file.file_id + "," + file.wrk_id
							+ ")'>삭제</a></td>";
					html += "</tr>";
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				console.log(data);
				console.log(pageVo);

				if (pageVo.page == 1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:workFilePagination("
							+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
							+ wrk_id + ");'>«</a></li>";
				for (var i = 1; i <= data.paginationSize; i++) {
					if (pageVo.page == i)
						pHtml += "<li class='active'><span>" + i
								+ "</span></li>";
					else
						pHtml += "<li><a href='javascript:workFilePagination("
								+ i + ", " + pageVo.pageSize + "," + wrk_id
								+ ");'>" + i + "</a></li>";
				}
				if (pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:workFilePagination("
							+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
							+ wrk_id + ");'>»</a></li>";
				$(".pagination").html(pHtml);
				$("#publicHeader").html(hhtml);
				$("#publicList").html(html);
			}
		});
	}

	function workLinkPagination(page, pageSize, wrk_id) {
		$.ajax({
			url : "/workLinkPagination",
			method : "post",
			data : "page=" + page + "&pageSize=" + pageSize + "&wrk_id="
					+ wrk_id,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";

				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>링크명</th>";
				hhtml += "<th>공유한 멤버 이름</th>";
				hhtml += "<th>등록한 날짜</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";

				console.log(data.workLinkList);
				data.workLinkList.forEach(function(link, index) {
					//html생성
					html += "<tr>";
					html += "<td><a href='https://"+link.attch_url+"'>"
							+ link.attch_url + "</a></td>";
					html += "<td>" + link.user_nm + "</td>";
					html += "<td>" + link.prjStartDtStr + "</td>";
					html += "<td><a href='javascript:workDelLink("
							+ link.link_id + "," + link.wrk_id
							+ ")'>삭제</a></td>";
					html += "</tr>";
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				console.log(data);
				console.log(pageVo);

				if (pageVo.page == 1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:workLinkPagination("
							+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
							+ wrk_id + ");'>«</a></li>";

				for (var i = 1; i <= data.paginationSize; i++) {
					if (pageVo.page == i)
						pHtml += "<li class='active'><span>" + i
								+ "</span></li>";
					else
						pHtml += "<li><a href='javascript:workLinkPagination("
								+ i + ", " + pageVo.pageSize + "," + wrk_id
								+ ");'>" + i + "</a></li>";
				}
				if (pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:workLinkPagination("
							+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
							+ wrk_id + ");'>»</a></li>";
				$(".pagination").html(pHtml);
				$("#publicHeader").html(hhtml);
				$("#publicList").html(html);
			}
		});
	}

	function workDelFile(fileID, wrk_id) {
		alert("삭제긔긔");

		$.ajax({
			url : "/workDelFile",
			method : "post",
			data : "file_id=" + fileID + "&wrk_id=" + wrk_id,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";

				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>파일명</th>";
				hhtml += "<th>공유한 멤버 이름</th>";
				hhtml += "<th>등록한 날짜</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";

				data.workFileList.forEach(function(file, index) {
					//html생성
					html += "<tr id='filetr'>";
					html += "<td><a href='#'>" + file.original_file_nm
							+ "</a></td>";
					html += "<td>" + file.user_nm + "</td>";
					html += "<td>" + file.prjStartDtStr + "</td>";
					html += "<td><a href='javascript:workDelFile("
							+ file.file_id + "," + file.wrk_id
							+ ")'>삭제</a></td>";
					html += "</tr>";
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				console.log(data);
				console.log(pageVo);

				if (pageVo.page == 1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:workFilePagination("
							+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
							+ wrk_id + ");'>«</a></li>";
				for (var i = 1; i <= data.paginationSize; i++) {
					if (pageVo.page == i)
						pHtml += "<li class='active'><span>" + i
								+ "</span></li>";
					else
						pHtml += "<li><a href='javascript:workFilePagination("
								+ i + ", " + pageVo.pageSize + "," + wrk_id
								+ ");'>" + i + "</a></li>";
				}
				if (pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:workFilePagination("
							+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
							+ wrk_id + ");'>»</a></li>";
				$(".pagination").html(pHtml);
				$("#publicHeader").html(hhtml);
				$("#publicList").html(html);
			}
		});
	}

	function workDelLink(linkID, wrk_id) {
		alert("삭제긔긔");

		$.ajax({
			url : "/workDelLink",
			method : "post",
			data : "link_id=" + linkID + "&wrk_id=" + wrk_id,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";

				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>링크명</th>";
				hhtml += "<th>공유한 멤버 이름</th>";
				hhtml += "<th>등록한 날짜</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";

				console.log(data.workLinkList);
				data.workLinkList.forEach(function(link, index) {
					//html생성
					html += "<tr>";
					html += "<td><a href='https://"+link.attch_url+"'>"
							+ link.attch_url + "</a></td>";
					html += "<td>" + link.user_nm + "</td>";
					html += "<td>" + link.prjStartDtStr + "</td>";
					html += "<td><a href='javascript:workDelLink("
							+ link.link_id + "," + link.wrk_id
							+ ")'>삭제</a></td>";
					html += "</tr>";
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				console.log(data);
				console.log(pageVo);

				if (pageVo.page == 1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:workLinkPagination("
							+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
							+ wrk_id + ");'>«</a></li>";

				for (var i = 1; i <= data.paginationSize; i++) {
					if (pageVo.page == i)
						pHtml += "<li class='active'><span>" + i
								+ "</span></li>";
					else
						pHtml += "<li><a href='javascript:workLinkPagination("
								+ i + ", " + pageVo.pageSize + "," + wrk_id
								+ ");'>" + i + "</a></li>";
				}
				if (pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:workLinkPagination("
							+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
							+ wrk_id + ");'>»</a></li>";
				$(".pagination").html(pHtml);
				$("#publicHeader").html(hhtml);
				$("#publicList").html(html);
			}
		});
	}

	// file, link 등록 부분 구현중
	
</script>


<div class="sub_menu">
	<ul class="sub_menu_item">
		<li><a href="/work/list">Work</a></li>
		<li><a href="/gantt/project">Gantt Chart</a></li>
		<li><a href="/analysis">Work Analysis</a></li>
		<li><a href="/publicFilePagination">File&amp;Link</a></li>
		<li><a href="">Meeting</a></li>
		<li><a href="/vote">Vote</a></li>
	</ul>
	
	<!-- 영하가 수정함 여기서부터ㅎ -->
	<div class="sub_btn">
		<ul>
			<li><a href="#">4</a></li>
			<li><a href="/publicFilePagination">회의록</a></li>
			<li><a href="#">프로젝트 대화</a></li>
			<li><a href="#">프로젝트 설정</a></li>
		</ul>
	</div>
	<!-- 영하가 수정함 여기까지ㅎ -->
	
</div>

<style>
.half {
	display: inline-block;
	width: 49%;
	padding: 0;
	margin: 0;
	vertical-align: top;
}
</style>

<section class="contents">
	<h2>${PROJECT_INFO.prj_nm}</h2>
	<div class="workListWrap">
		<div id="workListBox">
			<c:forEach items="${workList}" var="workList">
				<div class="workList">
					<span class="handle">+++</span>
					<div class="workList_hd">
						<dl>
							<dt>
								<input type="text" value="${workList.wrk_lst_nm}" id="${workList.wrk_lst_id}" class="wrkListName">
							</dt>
							<dd>
								<c:choose>
									<c:when test="${userInfo.prj_mem_lv == 'LV1' && PROJECT_INFO.prj_auth == 'ASC02' || userInfo.prj_mem_lv == 'LV1' && PROJECT_INFO.prj_auth == 'ASC03'}">
									</c:when>
									<c:otherwise>
										<input type="button" class="workList_add_i" value="새업무 추가">
										<a href="javascript:;" class="workList_set_i">업무리스트 설정</a>
										<div class="workList_set">
											<input type="button" id="btnWorkListDel_${workList.wrk_lst_id}" value="업무리스트 삭제">
										</div>
									</c:otherwise>
								</c:choose>
							</dd>
						</dl>
						<ul>
							<li>
							
								<c:set var="yCnt" value="0"/>
								<c:set var="nCnt" value="0"/>
								<c:forEach items="${works}" var="work">
									<c:choose>
										<c:when test="${workList.wrk_lst_id == work.wrk_lst_id && work.wrk_cmp_fl == 'N'}">
											<c:set var="nCnt" value="${nCnt + 1 }"/>
										</c:when>
										<c:when test="${workList.wrk_lst_id == work.wrk_lst_id && work.wrk_cmp_fl == 'Y'}">
											<c:set var="yCnt" value="${yCnt + 1 }"/>
										</c:when>
									</c:choose>
								</c:forEach>	
							
								<p>진행중 업무 <span>${nCnt }</span></p>
								<p>
									<a href="javascript:;" class="btnComplete" id="${workList.wrk_lst_id}">완료된업무보기</a>
									<span>${yCnt }</span>
								</p>
							</li>
							<li class="graph"></li>
						</ul>
						<div class="workCreateBox">
							<textarea name="wrk_nm" id="wrk_nm" placeholder="업무 이름을 입력해 주세요. 업무 이름은 70자 이내로 입력해 주세요"></textarea>
							<div class="workCreatebtnBox">
								<input type="button" value="취소" id="wrkCreateCancelBtn">
								<input type="button" value="만들기" id="wrkCreateBtn" class="wrkCreateBtn" name="${workList.wrk_lst_id}" disabled="disabled">
							</div>
						</div>
					</div>
					<div class="workWrap">
						<div class="list n1 working" id="${workList.wrk_lst_id}">
						<c:forEach items="${works}" var="work">
							<c:choose>
								<c:when test="${workList.wrk_lst_id == work.wrk_lst_id && work.wrk_cmp_fl == 'N'}">
									<div id="${work.wrk_lst_id}" data-wrkid="${work.wrk_id}" class="workListItem">
										<input type="checkbox" name="wrk_cmp_fl" id="wrk_cmp_fl">
										<h2 class="wrk_title"><span>${work.wrk_grade}</span>${work.wrk_nm}</h2>
										<ul>
											<li class="wrk_date">${work.wrkStartDtStr} ~ ${work.wrkEndDtStr}</li>
											<li>
												<span>코멘트 개수</span>
												<span>업무 파일 개수</span>
											</li>
										</ul>
										<div class="wrk_mem_flw">
											<dl class="wrk_mem">
												<dt>업무 배정 멤버</dt>
												<dd>
													<p>또굥이</p>
													<p>개굴이</p>
													<p>쏠이</p>
												</dd>
											</dl>
											<dl class="wrk_mem">
												<dt>업무 팔로워</dt>
												<dd>
													<p>유돌이</p>
													<p>하우두유두</p>
													<p>몽몽이</p>
												</dd>
											</dl>
										</div>
									</div>
								</c:when>
							</c:choose>
						</c:forEach>
						</div>
						<div class="complete_${workList.wrk_lst_id} list n1">
							<c:forEach items="${works}" var="work">
								<c:choose>
								<c:when test="${workList.wrk_lst_id == work.wrk_lst_id && work.wrk_cmp_fl == 'Y'}">
									<div id="${work.wrk_lst_id}" data-wrkid="${work.wrk_id}">
										<input type="checkbox" name="wrk_cmp_fl" id="wrk_cmp_fl" checked>
										<h2><span>${work.wrk_grade}</span>${work.wrk_nm}</h2>
										<ul>
											<li>${work.wrkStartDtStr} ~ ${work.wrkEndDtStr}</li>
											<li>
												<span>코멘트 개수</span>
												<span>업무 파일 개수</span>
											</li>
										</ul>
										<div class="wrk_mem_flw">
											<dl class="wrk_mem">
												<dt>업무 배정 멤버</dt>
												<dd>
													<p>또굥이</p>
													<p>개굴이</p>
													<p>쏠이</p>
												</dd>
											</dl>
											<dl class="wrk_mem">
												<dt>업무 팔로워</dt>
												<dd>
													<p>유돌이</p>
													<p>하우두유두</p>
													<p>몽몽이</p>
												</dd>
											</dl>
										</div>
									</div>
								</c:when>
							</c:choose>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="workListAdd">
			<input type="button" id="btnWorkList" value="업무리스트 추가">
		</div>
	</div>
</section>


<!-- work setting layer -->
<div id="propertyWorkSet" class="propertySet">
	<div class="propertySetWrap">
		<div class="setHd">
			<div class="setHdTitle">
				<input type="hidden"  id="wps_id" name="wps_id" value="">
				<input type="hidden"  id="wps_wrk_id" name="wps_wrk_id" value="">
				<h2>
					<input type="text" id="wps_nm" name="wps_nm" value="">
				</h2>
			</div>
			<p class="wrk_update"><span>작성자</span><em id="wps_write_nm"></em><span>작성일</span><em id="wps_write_date" ></em></p>
		</div>
		
		<!-- work tab -->
		<div class="workTab">
			<ul class="tabs">
				<li class="active" data-tab="tab1">설정</li>
				<li data-tab="tab2">업무 코멘트</li>
				<li id="FL" data-tab="tab3">파일&amp;링크</li>
			</ul>
		</div>
		
		<!-- 여기서부터 property setting layer contents -->
		<!-- 업무설정 : tab1 / 업무코멘트 : tab2 / 업무파일 : tab3 -->
		
		<div class="setCon tab_content" id="tab1">
			<dl class="setItem">
				<dt>날짜 설정</dt>
				<dd>
					<input class="flatpickr flatpickr-input" type="text" placeholder="Select Date.." data-id="rangeDisable" id="wps_start_date" readonly="readonly">
				</dd>
			</dl>
			<dl class="setItem">
				<dt>예약 알림</dt>
				<dd>
					<input class="flatpickr flatpickr-input" type="text" placeholder="Select Date.." data-id="rangeDisable" id="wps_res_date" readonly="readonly">
				</dd>
			</dl>
			<dl class="setItem">
				<dt>배정된 멤버</dt>
				<dd>
					<button type="button" id="wps_mem_set" name="wps_mem_set">멤버 추가</button>

					<!-- 프로젝트 관리자 리스트 box -->
					<ul class="wrk_add_box"></ul>

					<div class="wrk_add_mem">
						<label for="wrk_mem">배정된 멤버 추가</label>
						<div class="wrk_mem_list">
							<div class="wrk_mem_sch">
								<fieldset id="hd_sch">
									<legend>멤버 검색 검색</legend>
									<input type="text" name="wrk_mem" id="wrk_mem" maxlength="20" placeholder="검색어를 입력해주세요">
								</fieldset>
							</div>

							<!-- 추가된 프로젝트 관리자 리스트 box -->
							<ul class="wrk_mem_item"></ul>
						</div>
					</div>
				</dd>
			</dl>
			<dl class="setItem">
				<dt>팔로워</dt>
				<dd>
					<button type="button" id="wrk_flw_set" name="wrk_flw_set">팔로워 추가</button>

					<!-- 프로젝트 멤버 리스트 box -->
					<ul class="wrk_mem_flw_box"></ul>

					<div class="wrk_add_flw">
						<label for="wrk_flw">팔로워 추가</label>
						<div class="wrk_flw_list">
							<div class="wrk_flw_sch">
								<fieldset id="hd_sch">
									<legend>멤버 검색</legend>
									<input type="text" name="wrk_flw" id="wrk_flw" maxlength="20" placeholder="검색어를 입력해주세요">
								</fieldset>
							</div>

							<!-- 추가된 프로젝트 멤버 리스트 box -->
							<ul class="wrk_flw_item_list"></ul>
						</div>
					</div>
				</dd>
			</dl>
			<dl class="setItem">
				<dt>중요도</dt>
				<dd>
					<div class="setGrade">
						<select id="wrk_gd" name="wrk_gd">
							<option value="A">A</option>
							<option value="B">B</option>
							<option value="C">C</option>
							<option value="D">D</option>
							<option value="E">E</option>
						</select>
					</div>
				</dd>
			</dl>
			
			<dl class="setItem">
				<dt>라벨 색상</dt>
				<dd>
					<div class="lableColor">
						<ul>
							<li>
								<label for="CR01" class="wrk_color wrk_color01"></label>
								<input type="radio" value="CR01" name="wrk_color_cd" id="CR01">
							</li>
							<li>
								<label for="CR02" class="wrk_color wrk_color02"></label>
								<input type="radio" value="CR02" name="wrk_color_cd" id="CR02">
							</li>
							<li>
								<label for="CR03" class="wrk_color wrk_color03"></label>
								<input type="radio" value="CR03" name="wrk_color_cd" id="CR03">
							</li>
							<li>
								<label for="CR04" class="wrk_color wrk_color04"></label>
								<input type="radio" value="CR04" name="wrk_color_cd" id="CR04">
							</li>
							<li>
								<label for="CR05" class="wrk_color wrk_color05"></label>
								<input type="radio" value="CR05" name="wrk_color_cd" id="CR05">
							</li>
							<li>
								<label for="CR06" class="wrk_color wrk_color06"></label>
								<input type="radio" value="CR06" name="wrk_color_cd" id="CR06">
							</li>
							<li>
								<label for="CR07" class="wrk_color wrk_color07"></label>
								<input type="radio" value="CR07" name="wrk_color_cd" id="CR07">
							</li>
							<li>
								<label for="CR08" class="wrk_color wrk_color08"></label>
								<input type="radio" value="CR08" name="wrk_color_cd" id="CR08">
							</li>
							<li>
								<label for="CR09" class="wrk_color wrk_color09"></label>
								<input type="radio" value="CR09" name="wrk_color_cd" id="CR09">
							</li>
							<li>
								<label for="CR10" class="wrk_color wrk_color10"></label>
								<input type="radio" value="CR10" name="wrk_color_cd" id="CR10">
							</li>
						</ul>
					</div>
				</dd>
			</dl>
		</div>
		
		<!--  여기서부터 work comment -->
		<div id="tab2" class="tab_content">
			여기는 업무 코멘트 입니다.
		</div>
		
		<!--  여기서부터 work file&link-->
		<!-- 영하가 수정함 여기부터 ㅎ-->
		<div id="tab3" class="tab_content">
			<div class="sub_menu">
				<ul class="tabs">
					<li id="fileList">FileList</li>
					<li id="linkList">LinkList</li>
				</ul>
			</div>
			
			<div id="locker">
				<input value="public" name="box" type="radio">공유함 긔긔
				<input value="individual" name="box" type="radio">개인함 긔긔
				<input value="both" name="box" type="radio">둘다 긔긔
			</div>

			<form id="frm" action="/workFileUpload" method="post" enctype="multipart/form-data">
				<div id="workFile">
					<input type="hidden" id="work" value="" name="wrk_id">
					<input type="hidden" id="box" value="" name="locker">
					<label class="col-sm-2 control-label">공유할 첨부파일:</label>
					<input type="file" class="file" name="profile"/>
					<button type="button" id="uploadFile">등록</button>
				</div>
			</form>
			
			<div id="workLink">
				<label class="col-sm-2 control-label">공유할 링크주소:</label>
				<input type="text" class="link" name="attch_url"/>
				<button type="button" id="uploadLink">등록</button>
			</div>
			
			<div class="tab_con">
	
				<div class="tab-content current">
					<div>
						<table class="tb_style_01">
							<colgroup>
								<col width="30%">
								<col width="20%">
								<col width="30%">
								<col width="20%">
							</colgroup>
							
								<thead id="publicHeader">
								<thead>
								
								<tbody id="publicList">
							</tbody>
							
						</table>
					</div>
	
				<div class="pagination">
					</div>
					
				</div>
			</div>
			
		</div>
		<!-- 영하가 수정함 여기까지ㅎ -->
		
	</div>
	<div class="btnSetClose">닫기</div>
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