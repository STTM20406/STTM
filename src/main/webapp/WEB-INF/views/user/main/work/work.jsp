<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
						html +="<p>진행중 업무 <span>4</span></p>"
						html +="<a href='javascript:'>완료된업무보기 <span>2</span></a></li>"
						html +="<li class='graph'></li></ul></div>"
						html +="<div class='list n1' id='"+workList.wrk_lst_id+"'>"
						data.works.forEach(function(work, index2) {
		 						if(work.wrk_lst_id == workList.wrk_lst_id){
									html +=	"<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work.wrk_id+"'>"+work.wrk_nm+"</div>"
		 						}
		 				});
						html +="</div></div>"
						
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
							html +="<p>진행중 업무 <span>4</span></p>"
							html +="<a href='javascript:'>완료된업무보기 <span>2</span></a></li>"
							html +="<li class='graph'></li></ul></div>"
							html +="<div class='list n1' id='"+workList.wrk_lst_id+"'>"
							data.works.forEach(function(work, index2) {
			 						if(work.wrk_lst_id == workList.wrk_lst_id){
			 							html +=	"<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work.wrk_id+"'>"+work.wrk_nm+"</div>"
			 						}
			 				});
							html +="</div></div>"
							
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
				success : function(data) {
					var html = "";
					data.workList.forEach(function(workList, index) {
						html +="<div class='workList' id='"+ workList.wrk_lst_id + "'><span class='handle'>+++</span><div class='workList_hd'><dl>"
						html +="<dt><input type='text' value='"+ workList.wrk_lst_nm+"' id='"+workList.wrk_lst_id+"' class='wrkListName'></dt><dd>"
						html +="<input type='button' class='workList_add_i' value='새업무 추가'>"
						html +="<a href='javascript:;' class='workList_set_i'>업무리스트 설정</a>"
						html +="<div class='workList_set'><input type='button' id='btnWorkListDel_"+workList.wrk_lst_id+"' value='업무리스트 삭제'></div>"
						html +="</dd></dl><ul><li>"
						html +="<p>진행중 업무 <span>4</span></p>"
						html +="<a href='javascript:'>완료된업무보기 <span>2</span></a></li>"
						html +="<li class='graph'></li></ul></div>"
						html +="<div class='list n1' id='"+workList.wrk_lst_id+"'>"
						data.works.forEach(function(work, index2) {
		 						if(work.wrk_lst_id == workList.wrk_lst_id){
		 							html +=	"<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work.wrk_id+"'>"+work.wrk_nm+"</div>"
		 						}
		 				});
						html +="</div></div>"
						
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

	});
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
	<div class="sub_btn">
		<ul>
			<li><input type="button" value="4"></li>
			<li><input type="button" value="프로젝트 대화"></li>
			<li><input type="button" value="프로젝트 설정"></li>
		</ul>
	</div>
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
									<c:when test="${userInfo.prj_mem_lv == 'LV0' && PROJECT_INFO.prj_auth == 'ASC02' || userInfo.prj_mem_lv == 'LV0' && PROJECT_INFO.prj_auth == 'ASC03'}">
										<input type="button" class="workList_add_i" value="새업무 추가">
										<a href="javascript:;" class="workList_set_i">업무리스트 설정</a>
										<div class="workList_set">
											<input type="button" id="btnWorkListDel_${workList.wrk_lst_id}" value="업무리스트 삭제">
										</div>
									</c:when>
								</c:choose>
							</dd>
						</dl>
						<ul>
							<li>
								<p>
									진행중 업무 <span>4</span>
								</p> <a href="javascript:;">완료된업무보기 <span>2</span></a>
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
					<div class="list n1" id="${workList.wrk_lst_id}">
						<c:forEach items="${works}" var="work">
							<c:choose>
								<c:when test="${workList.wrk_lst_id == work.wrk_lst_id}">
									<div id="${work.wrk_lst_id}" data-wrkid="${work.wrk_id}">${work.wrk_nm}</div>
								</c:when>
							</c:choose>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="workListAdd">
			<input type="button" id="btnWorkList" value="업무리스트 추가">
		</div>
	</div>
</section>


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